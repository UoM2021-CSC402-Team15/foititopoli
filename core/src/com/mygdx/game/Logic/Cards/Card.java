package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public abstract class Card {
  protected String image;

    public Card(String image) {
        this.image = image;
    }
    public abstract float runAction(Player aPlayer);


}
