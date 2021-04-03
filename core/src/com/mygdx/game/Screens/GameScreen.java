package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Pawn;


public class GameScreen implements Screen {

    private Stage stage;
    private Foititopoli game;

    Texture background;
    Batch batch;

    OrthographicCamera camera;

    private Pawn pawn;

    public GameScreen(final Foititopoli game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();

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

        pawn = game.getGameInstance().getPlayers().get(0).getPawn();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background,240,100,800,600);
        batch.draw(pawn,pawn.x, pawn.y,80,60);
        batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            pawn.setTarget(touchPos.x,touchPos.y);
        }
        pawn.update();

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

    }
}
