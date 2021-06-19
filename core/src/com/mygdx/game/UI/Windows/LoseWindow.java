package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Player;

public class LoseWindow extends Window {
    public LoseWindow(Player aPlayer) {
        super("Sorry", Foititopoli.gameSkin);
        setModal(true);

        setSize(400,400);
        setScale(1.5f);
        setPosition((1280-getWidth()*getScaleX())/2,(720-getHeight()*getScaleY())/2 );

        Label description = new Label(aPlayer.getName()+" is out of the game", Foititopoli.gameSkin);
        description.setWrap(true);
        description.setAlignment(Align.center);
        description.setBounds(getX(), getY(), getWidth(), getHeight());
        description.invalidate();
        description.pack();

        TextButton okButton = new TextButton("OK",Foititopoli.gameSkin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LoseWindow.this.remove();
            }
        });

        add(description).pad(20).expand().fill().row();
        add(okButton).width(100).height(30).pad(20);
    }
}