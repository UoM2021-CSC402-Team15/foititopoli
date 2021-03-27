package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Foititopoli;

public class LoadGameScreen implements Screen {

    private Stage stage;
    private Foititopoli game;

    private class SavedGameItem extends Table {

        Table table;

        public SavedGameItem(String name) {
            this.table = new Table();

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
                    game.setScreen(new GameScreen(game));
                }
            });

            Label nameLabel = new Label("", Foititopoli.gameSkin);
            nameLabel.setText(name);
            Label dateLabel = new Label("Created on 01/01/1970", Foititopoli.gameSkin);

            this.pad(20);
            //this.debug();
            this.add(nameLabel).width(500).fill();
            this.add(loadButton).expandX().fill().height(50).padBottom(10).row();
            this.add(dateLabel).fillX();
            this.add(deleteButton).fill();

        }

    }


    public LoadGameScreen(final Foititopoli game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Label title = new Label("Load Game Screen", Foititopoli.gameSkin);
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth() / 2);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 4 - backButton.getHeight() / 2);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table mainTable = new Table();

        VerticalGroup verticalGroup = new VerticalGroup();
        //verticalGroup.setWidth(Gdx.graphics.getWidth());
        ScrollPane scrollPane = new ScrollPane(verticalGroup);

        for (int i = 0; i < 25; i++) {
            SavedGameItem item = new SavedGameItem("test " + i);
            item.setWidth(Gdx.graphics.getWidth());
            verticalGroup.addActor(item);
        }

        mainTable.setFillParent(true);
        mainTable.add(title).padTop(10).padBottom(20).row();
        mainTable.add(scrollPane).row();
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

    }
}

