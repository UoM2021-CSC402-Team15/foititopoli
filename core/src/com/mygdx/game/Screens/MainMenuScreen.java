package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Foititopoli;

public class MainMenuScreen implements Screen {

    final Foititopoli game;

    OrthographicCamera camera;

    Texture background;
    Music introAmbience;

    Stage stage;

    Table table;

    Batch batch;

    public MainMenuScreen(final Foititopoli game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        table = new Table();
        table.setFillParent(true);

        background = new Texture(Gdx.files.internal("background2.jpg"));

        TextButton playButton = new TextButton("Play", Foititopoli.gameSkin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameSetupScreen(game));
            }
        });

        TextButton loadGamesButton = new TextButton("Load Game", Foititopoli.gameSkin);
        loadGamesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadGameScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Exit to Desktop", Foititopoli.gameSkin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.exit(0);
            }
        });


        //table.setDebug(true);
        table.columnDefaults(0).width(500).height(64).fill();
        table.add(playButton).row();
        table.add(loadGamesButton).row();
        table.add(exitButton);


        stage.addActor(table);

        introAmbience = Gdx.audio.newMusic(Gdx.files.internal("elevatorMusic.mp3"));
        introAmbience.setVolume(0.3f);
        introAmbience.setLooping(true);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background,0,0,1280,720);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        introAmbience.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        if (!game.backgroundMusicPlaying) {
            introAmbience.play();
            game.backgroundMusicPlaying = true;
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //introAmbience.stop();
    }
}
