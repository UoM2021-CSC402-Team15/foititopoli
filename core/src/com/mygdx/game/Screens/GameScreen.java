package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Board;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Pawn;
import com.mygdx.game.Square;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Stage stage;
    private Foititopoli game;

    Texture background;
    Batch batch;

    OrthographicCamera camera;

    private BitmapFont font;

    private ArrayList<Pawn> pawns = new ArrayList<>();

    public GameScreen(final Foititopoli game) {
        this.game = game;
        batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport(), batch);

        background = new Texture(Gdx.files.internal("board.png"));

        Label title = new Label("Game Screen", Foititopoli.gameSkin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*4/5);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth()/2);
        backButton.setPosition(Gdx.graphics.getWidth()/2-backButton.getWidth()/2,20);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backButton);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        Board board = new Board(600,11);
        board.setPosition(200,100);
        stage.addActor(board);
        //stage.setDebugAll(true);

        for (int i = 0; i < game.getGameInstance().getPlayers().size(); i++) {
            Pawn pawn = game.getGameInstance().getPlayers().get(i).getPawn();
            pawns.add(pawn);
            pawn.moveTo((i*100)+50, pawn.getY());
            stage.addActor(pawn);
        }

        font = new BitmapFont();
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
            //System.out.println("X: " +Gdx.input.getX() + " | Y: " +Gdx.input.getY());
            pawns.get(1).moveTo(Gdx.input.getX(),Gdx.input.getY(),1);
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
