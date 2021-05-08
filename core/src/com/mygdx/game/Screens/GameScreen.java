package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import com.mygdx.game.Windows.DebugConsole;
import com.mygdx.game.Windows.PauseWindow;

import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen {

    private final Stage stage;
    private final Batch batch;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final PauseWindow pauseWindow;
    private final BoardUI boardUI;
    private final DebugConsole console;
    private Foititopoli game;

    public class BoardUI extends Group {

        private Board board;

        private float tileHeightRatio = 1.5f;

        public BoardUI(Board board, float size) {
            this.board = board;
            setSize(size, size);
            float basicTileWidth = size/(board.tilesPerSide-2 + (2 * tileHeightRatio) );
            drawBoard(basicTileWidth);

            for(Player player: game.getGameInstance().getPlayers()) {
                this.addActor(player.getPawn());
                player.getPawn().setCurrentSquare(board.squares[0][0]);
                player.getPawn().moveTo(board.squares[0][0].getCenter().x, board.squares[0][0].getCenter().y);
            }
        }

        public void movePawn(Pawn pawn, Square destination) {

            SequenceAction sequence = new SequenceAction();

            int currentI = pawn.getOldSquare().i;
            int currentJ = pawn.getOldSquare().j;

            for (int i = 0; i < 10; i++) { //So it doesnt continue to infinity

                if ( currentI == destination.i && currentJ < destination.j ) { // If on same side and target forward
                    sequence.addAction(pawn.getMoveLeftToSquare(destination));
                    break;
                } else if ( (currentI+1)%4==destination.i && destination.j ==0 ) { // If target is next corner (next corner is considered other side)
                    sequence.addAction(pawn.getMoveLeftToSquare(destination));
                    RotateByAction rotate = new RotateByAction();
                    rotate.setAmount(-90);
                    rotate.setDuration(0.5f);
                    sequence.addAction(rotate);
                    break;
                } else {                                                       // else go to the next side and repeat
                    Square endSquare = board.squares[(currentI+1)%4][0];
                    sequence.addAction(pawn.getMoveLeftToSquare(endSquare));
                    RotateByAction rotate = new RotateByAction();
                    rotate.setAmount(-90);
                    rotate.setDuration(0.5f);
                    sequence.addAction(rotate);
                    currentI = endSquare.i;
                    currentJ = endSquare.j;
                }
            }
            pawn.addAction(sequence);
        }

        private void drawBoard(float basicTileWidth) {
            float basicTileHeight = basicTileWidth * tileHeightRatio;

            drawSide(0, Arrays.asList(board.squares[0]), basicTileWidth, getWidth()-basicTileHeight,0);
            drawSide(-90, Arrays.asList(board.squares[1]), basicTileWidth, 0, basicTileHeight);
            //noinspection SuspiciousNameCombination
            drawSide(-180, Arrays.asList(board.squares[2]), basicTileWidth, basicTileHeight, getHeight());
            drawSide(-270, Arrays.asList(board.squares[3]), basicTileWidth, getWidth(), getHeight()-basicTileHeight);
        }

        private void drawSide(int rotation, List<Square> squares, float basicTileWidth, float startX, float startY) {
            float basicTileHeight = basicTileWidth * tileHeightRatio;

            //noinspection SuspiciousNameCombination
            squares.get(0).setSize(basicTileHeight, basicTileHeight);
            squares.get(0).setRotation(rotation);
            squares.get(0).setPosition(startX,startY);
            addActor(squares.get(0));
            for (int i = 1; i < squares.size(); i++) {
                squares.get(i).setSize(basicTileWidth, basicTileHeight);
                squares.get(i).setRotation(rotation);
                squares.get(i).setPosition(startX,startY);
                addActor(squares.get(i));
                squares.get(i).moveLeft(i*basicTileWidth);
            }
        }

    }

    public GameScreen(Foititopoli game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        Viewport viewport = new StretchViewport(1280,720, camera);
        this.stage = new Stage(viewport, batch);

        Label title = new Label("Game Screen", Foititopoli.gameSkin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*4/5f);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton pauseButton = new TextButton("Pause Menu", Foititopoli.gameSkin);
        pauseButton.setWidth(Gdx.graphics.getWidth()/2f);
        pauseButton.setPosition(camera.viewportWidth/2f-pauseButton.getWidth()/2,20);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseWindow.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
                stage.addActor(pauseWindow);
            }
        });
        stage.addActor(pauseButton);

        boardUI = new BoardUI(game.getGameInstance().getBoard(), 600);
        boardUI.setPosition((camera.viewportWidth- boardUI.getWidth())/2, 100);
        stage.addActor(boardUI);
        //stage.setDebugAll(true);

        font = new BitmapFont();

        pauseWindow = new PauseWindow("Game paused", Foititopoli.gameSkin, game);

        console = new DebugConsole(game.getGameInstance(), stage);

        game.getGameInstance().setListener(new GameInstance.GameInstanceListener() {
            @Override
            public void pawnPositionUpdated(Pawn pawn) {
                boardUI.movePawn(pawn, pawn.getCurrentSquare());
            }
        });
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (pauseWindow.hasParent()) {
                pauseWindow.remove();
            } else {
                pauseWindow.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
                stage.addActor(pauseWindow);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            console.activate();
        }

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen","show");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
