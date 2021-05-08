package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.DataProvider;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.GameInstance;

public class GameSetupScreen implements Screen {

    private final Stage stage;

    public GameSetupScreen(final Foititopoli game) {

        Viewport viewport = new StretchViewport(1280,720);
        stage = new Stage(viewport);

        Label title = new Label("Create Game", Foititopoli.gameSkin);

        final Label playerLabel = new Label("", Foititopoli.gameSkin);
        final Slider playersSlider = new Slider(2,8,1,false,Foititopoli.gameSkin);
        playersSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerLabel.setText( (int)playersSlider.getValue() + " Players" );
            }
        });
        playersSlider.setValue(4);

        final Label currencyLabel = new Label("", Foititopoli.gameSkin);
        final Slider currencySlider = new Slider(100,1000,100,false,Foititopoli.gameSkin);
        currencySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currencyLabel.setText( "Initial Player Money: " + (int)currencySlider.getValue() );
            }
        });
        currencySlider.setValue(300);

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        VerticalGroup verticalGroup = new VerticalGroup();
        //verticalGroup.setWidth(Gdx.graphics.getWidth());
        ScrollPane scrollPane = new ScrollPane(verticalGroup);

        for (int i = 0; i < 15; i++) {
            CheckBox checkBox = new CheckBox("option " + i, Foititopoli.gameSkin);
            verticalGroup.addActor(checkBox);
        }

        TextButton createGameButton = new TextButton("Create Game", Foititopoli.gameSkin);
        createGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameInstance gameInstance = new GameInstance( (int)playersSlider.getValue(), currencySlider.getValue());
                game.setGameInstance(gameInstance);
                game.setScreen(new PlayerCreateScreen(game, DataProvider.getPawns()));
            }
        });

        Table mainTable = new Table();

        mainTable.setFillParent(true);
        mainTable.add(title).padTop(10).padBottom(20).row();
        mainTable.add(playerLabel).row();
        mainTable.add(playersSlider).row();
        mainTable.add(currencyLabel).row();
        mainTable.add(currencySlider).row();
        mainTable.add(scrollPane).row();
        mainTable.add(createGameButton).row();
        mainTable.add(backButton).padTop(20).padBottom(10);

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.app.log("GameSetupScreen", "show");
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
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
}