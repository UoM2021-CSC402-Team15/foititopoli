package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Board;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Pawn;
import com.mygdx.game.Square;
import com.mygdx.game.Windows.PauseWindow;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Stage stage;
    private Foititopoli game;

    Texture background;
    Batch batch;

    OrthographicCamera camera;

    private BitmapFont font;

    PauseWindow pauseWindow;

    Board board;

    private ArrayList<Pawn> pawns = new ArrayList<>();

    public GameScreen(final Foititopoli game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        Viewport viewport = new StretchViewport(1280,720, camera);
        this.stage = new Stage(viewport, batch);

        background = new Texture(Gdx.files.internal("board.png"));

        Label title = new Label("Game Screen", Foititopoli.gameSkin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*4/5);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton pauseButton = new TextButton("Pause Menu", Foititopoli.gameSkin);
        pauseButton.setWidth(Gdx.graphics.getWidth()/2);
        pauseButton.setPosition(Gdx.graphics.getWidth()/2-pauseButton.getWidth()/2,20);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseWindow.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
                stage.addActor(pauseWindow);
            }
        });
        stage.addActor(pauseButton);

        board = new Board(600);
        board.setPosition((camera.viewportWidth-board.getWidth())/2, 100);
        stage.addActor(board);
        //stage.setDebugAll(true);

        for (int i = 0; i < game.getGameInstance().getPlayers().size(); i++) {
            Pawn pawn = game.getGameInstance().getPlayers().get(i).getPawn();
            pawns.add(pawn);
            pawn.moveTo((i*100)+50, pawn.getY());
            board.addActor(pawn);
        }

        font = new BitmapFont();

        pauseWindow = new PauseWindow("Game paused", Foititopoli.gameSkin, game);

        pawns.get(0).setCurrentSquare(board.squares[0][0]);
        pawns.get(0).moveTo(board.squares[0][0].getX(),board.squares[0][0].getY());
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //batch.draw(background,240,100,800,600);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            pawns.get(1).moveTo(touchPos.x, touchPos.y,1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (pauseWindow.hasParent()) {
                pauseWindow.remove();
            } else {
                pauseWindow.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
                stage.addActor(pauseWindow);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            SequenceAction sequence = board.movePawn(pawns.get(0), board.squares[1][8], new SequenceAction());
            pawns.get(0).addAction(sequence);
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
