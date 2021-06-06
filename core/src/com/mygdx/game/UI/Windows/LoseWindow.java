package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Player;

public class LoseWindow extends Window {
    public LoseWindow(Player aPlayer) {
        super("sory", Foititopoli.gameSkin);
        setModal(true);

        Label loseMessage = new Label(aPlayer.getName()+"is out of the game",Foititopoli.gameSkin);
        loseMessage.setPosition(getWidth()/2,getHeight()/2);

    }


}
