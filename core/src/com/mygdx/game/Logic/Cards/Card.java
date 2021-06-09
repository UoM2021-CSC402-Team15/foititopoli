package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public abstract class Card {
  protected String description;

    public Card(String image) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public abstract float runAction(Player aPlayer);


}
