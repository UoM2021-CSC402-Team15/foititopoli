package com.mygdx.game.Windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Screens.MainMenuScreen;


public class PauseWindow extends Window{

    public PauseWindow(String title, Skin skin, final Foititopoli game) {
        super(title, skin);

        this.setModal(true);

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

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x-getWidth()/2, y-getHeight()/2);
    }
}
