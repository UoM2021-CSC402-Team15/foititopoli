package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.game.Windows.DebugConsole;
import com.mygdx.game.Windows.PauseWindow;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final Stage stage;

    private final Batch batch;

    private final OrthographicCamera camera;

    private final BitmapFont font;

    private final PauseWindow pauseWindow;

    private final Board board;

    private final ArrayList<Pawn> pawns = new ArrayList<>();

    private final DebugConsole console;

    public GameScreen(final Foititopoli game) {
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
        pauseButton.setPosition(Gdx.graphics.getWidth()/2f-pauseButton.getWidth()/2,20);
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


        for(Pawn pawn: pawns) {
            pawn.setCurrentSquare(board.squares[0][0]);
            pawn.moveTo(board.squares[0][0].getCenter().x, board.squares[0][0].getCenter().y);
        }

        console = new DebugConsole(stage);
        console.setBoard(board);
        console.setPawns(pawns);
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
