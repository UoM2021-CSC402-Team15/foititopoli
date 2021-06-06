package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Cards.Card;



public class CardWindow extends Window {


    public CardWindow(Card aCard) {
        super("Random Game Card", Foititopoli.gameSkin);
        this.setModal(true);
        Label description = new Label(aCard.getDescription(),Foititopoli.gameSkin);
        description.setPosition(getWidth()/2,getHeight()/2);




        TextButton okButton = new TextButton("OK",Foititopoli.gameSkin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
              CardWindow.this.remove();
            }
        });

        add(okButton);
        okButton.setPosition(getWidth()/2,0);
    }

}
