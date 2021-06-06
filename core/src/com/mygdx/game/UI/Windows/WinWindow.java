package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Player;

public class WinWindow extends Window {
    public WinWindow(Player aPlayer) {
        super("congratulations!!!", Foititopoli.gameSkin);
        setModal(true);

        Label winMessage = new Label(aPlayer.getName()+"is the winner",Foititopoli.gameSkin);
        winMessage.setPosition(getWidth()/2,getHeight()/2);

    }


}
