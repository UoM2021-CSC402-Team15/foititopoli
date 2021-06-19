package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.UI.Screens.MainMenuScreen;

public class WinWindow extends Window {
    public WinWindow(Player aPlayer, final Foititopoli game) {
        super("Congratulations!!!", Foititopoli.gameSkin);
        setModal(true);

        setSize(400,200);
        setScale(1.5f);
        setPosition((1280-getWidth()*getScaleX())/2,(720-getHeight()*getScaleY())/2 );

        Label winMessage = new Label(aPlayer.getName()+" is the winner",Foititopoli.gameSkin);
        winMessage.setPosition(getWidth()/2,getHeight()/2);
        TextButton returnToMainMenuButton = new TextButton("Return to main menu",Foititopoli.gameSkin);
        returnToMainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        add(winMessage).row();
        add(returnToMainMenuButton);
    }
}
