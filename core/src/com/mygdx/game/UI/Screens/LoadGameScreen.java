package com.mygdx.game.UI.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LoadGameScreen implements Screen {

    private Stage stage;
    private final Foititopoli game;

    private class SavedGameItem extends Table {

        public SavedGameItem(String name, FileHandle fileHandle) {

            Viewport viewport = new StretchViewport(720,400);;
            stage = new Stage(viewport);

            TextButton loadButton = new TextButton("Load", Foititopoli.gameSkin);
            loadButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameInstance loadedGame = DataProvider.loadGame(fileHandle.read());
                    game.setGameInstance(loadedGame);
                    System.out.println(loadedGame.getPlayers());
                    game.setScreen(new GameScreen(game));
                }
            });
            TextButton deleteButton = new TextButton("Delete", Foititopoli.gameSkin);
            deleteButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    fileHandle.delete();
                    remove();
                }
            });

            Label nameLabel = new Label("", Foititopoli.gameSkin);
            nameLabel.setText(name);
            Label dateLabel = new Label("Created on --/--/---", Foititopoli.gameSkin);

            @SuppressWarnings("SimpleDateFormat")
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH_mm'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            String iso = fileHandle.name().replace(".ser","");
            try {
                System.out.println(iso);
                Date result = df.parse(iso);
                dateLabel.setText(SimpleDateFormat.getDateTimeInstance().format(result));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.pad(20);
            this.add(nameLabel).expand().fill().width(500);
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

        TextButton backButton = new TextButton("Back", Foititopoli.gameSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table mainTable = new Table();

        VerticalGroup verticalGroup = new VerticalGroup();
        ScrollPane scrollPane = new ScrollPane(verticalGroup);


        ArrayList<FileHandle> gameSaves = new ArrayList<>();
        if (Gdx.files.local("./saves").exists() && Gdx.files.local("./saves").isDirectory()) {
            FileHandle[] files = Gdx.files.local("./saves").list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches("^\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d_\\d\\dZ\\.ser$");
                }
            });
            gameSaves.addAll(Arrays.asList(files));
        }
        System.out.println(gameSaves);

        for (int i = gameSaves.size(); i > 0; i--) {
            SavedGameItem item = new SavedGameItem("Foititopoli Save " + i, gameSaves.get(i-1));
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
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 0.5f);
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

