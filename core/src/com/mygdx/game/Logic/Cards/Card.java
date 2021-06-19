package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public abstract class Card {
    protected final String description;

    public Card(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract void runAction(Player aPlayer);

}
