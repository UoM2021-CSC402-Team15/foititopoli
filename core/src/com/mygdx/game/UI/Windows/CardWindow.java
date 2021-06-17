package com.mygdx.game.UI.Windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.UI.Screens.GameScreen;

public class CardWindow extends Window {

    public CardWindow(Player player, Card aCard, GameScreen.UI ui) {
        super("Random Game Card", Foititopoli.gameSkin);
        this.setModal(true);
        Label description = new Label(aCard.getDescription(), Foititopoli.gameSkin);
        description.setWrap(true);
        description.setAlignment(Align.center);
        description.setBounds(getX(), getY(), getWidth(), getHeight());
        description.invalidate();
        description.pack();

        setSize(400,400);
        setScale(1.5f);
        setPosition((Gdx.graphics.getWidth()-getWidth()*getScaleX())/2,(Gdx.graphics.getHeight()-getHeight()*getScaleY())/2 );

        TextButton okButton = new TextButton("OK",Foititopoli.gameSkin);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aCard.runAction(player);
                ui.updatePlayer(player);
                CardWindow.this.remove();
            }
        });

        add(description).pad(20).expand().fill().row();
        add(okButton).width(100).height(30).pad(20);
    }
}
