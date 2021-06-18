package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.UI.Screens.MainMenuScreen;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PauseWindow extends Window{

    public PauseWindow(String title, Skin skin, final Foititopoli game) {
        super(title, skin);

        this.setModal(true);

        setSize(300,200);
        setScale(1.5f);
        setPosition((1280-getWidth()*getScaleX())/2,(720-getHeight()*getScaleY())/2 );

        TextButton resumeButton = new TextButton("Resume", Foititopoli.gameSkin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PauseWindow.this.remove();
            }
        });
        final TextButton saveButton = new TextButton("Save", Foititopoli.gameSkin);
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (Gdx.files.local("./saves").exists() && Gdx.files.local("./saves").isDirectory()) {

                        // Get Current TimeDate as ISO-8601
                        TimeZone tz = TimeZone.getTimeZone("UTC");
                        @SuppressWarnings("SimpleDateFormat")
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH_mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                        df.setTimeZone(tz);
                        String nowAsISO = df.format(new Date());

                        // Write file
                        OutputStream fouts = Gdx.files.local("./saves/" + nowAsISO + ".ser").write(false);
                        ObjectOutputStream douts = new ObjectOutputStream(fouts);
                        game.getGameInstance().setListener(null);
                        douts.writeObject(game.getGameInstance());
                        douts.close();
                        fouts.close();

                    } else {
                        Gdx.files.local("./saves").mkdirs();
                        this.changed(event, actor);
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }

                saveButton.setText("Game Saved");

            }
        });
        TextButton exitButton = new TextButton("Exit to Main Menu", Foititopoli.gameSkin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        add(resumeButton).expand().fill().row();
        add(saveButton).expand().fill().row();
        add(exitButton).expand().fill();
    }

}
