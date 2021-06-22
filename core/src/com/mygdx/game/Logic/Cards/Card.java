package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

/**
 * Abstract Card Class
 */
public abstract class Card {

    /**
     * Body text of the card to be shown when drawn.
     */
    protected final String description;

    public Card(String description) {
        this.description = description;
    }

    /**
     * To be called after the user has been shown the card. <br>
     * @param aPlayer The current {@link Player}
     */
    public abstract void runAction(Player aPlayer);

    public String getDescription() {
        return description;
    }

}
