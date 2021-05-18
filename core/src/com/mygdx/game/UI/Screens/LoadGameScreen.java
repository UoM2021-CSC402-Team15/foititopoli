package com.mygdx.game.UI.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.DataProvider;
import com.mygdx.game.Logic.GameInstance;

public class LoadGameScreen implements Screen {

    private Stage stage;
    private final Foititopoli game;

    private class SavedGameItem extends Table {

        public SavedGameItem(String name) {

            Viewport viewport = new StretchViewport(1280,720);
            stage = new Stage(viewport);

            TextButton loadButton = new TextButton("Load", Foititopoli.gameSkin);
            loadButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            });
            TextButton deleteButton = new TextButton("Delete", Foititopoli.gameSkin);
            loadButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameInstance loadedGame = DataProvider.loadGame("save.ser");
                    game.setGameInstance(loadedGame);
                    System.out.println(loadedGame.getPlayers());
                    game.setScreen(new GameScreen(game));
                }
            });

            Label nameLabel = new Label("", Foititopoli.gameSkin);
            nameLabel.setText(name);
            Label dateLabel = new Label("Created on 01/01/1970", Foititopoli.gameSkin);

            this.pad(20);
            //this.debug();
            this.add(nameLabel).expand().fill().width(viewport.getScreenWidth() /3f);
            this.add(loadButton).expandX().fill().height(50).padBottom(10).row();
            this.add(dateLabel).fillX();
            this.add(deleteButton).fill();

        }

    }


    public LoadGameScreen(final Foititopoli game) {
        this.game = game;

        Viewport viewport = new StretchViewport(1280,720);
        stage = new Stage(viewport);

        Label title = new Label("Load Game Screen", Foititopoli.gameSkin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3f);
        title.setWidth(Gdx.graphics.getWidth());

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth() / 2f);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 4f - backButton.getHeight() / 2);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table mainTable = new Table();

        VerticalGroup verticalGroup = new VerticalGroup();
        ScrollPane scrollPane = new ScrollPane(verticalGroup);

        for (int i = 0; i < 25; i++) {
            SavedGameItem item = new SavedGameItem("test " + i);
            verticalGroup.addActor(item);
        }

        mainTable.setFillParent(true);
        mainTable.add(title).padTop(10).padBottom(20).row();
        mainTable.add(scrollPane).expand().fill().row();
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
        Gdx.app.log("GameScreen", "show");
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

