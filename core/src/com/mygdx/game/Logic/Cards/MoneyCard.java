package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

/**
 * Card Type that implements the win/lose [AMMOUNT] hours function
 */
public class MoneyCard extends Card {

    /** The ammount of hours the player is going to gain/lose (possitive or negative value respectively) */
    private final float amount;

    public MoneyCard(String description, float amount) {
            super(description);
            this.amount = amount;
        }

    /**
     * {@inheritDoc}
     * Adds the ammount specified to the current Study Hours of the player
     * @param aPlayer The current {@link Player}
     */
    @Override
    public void runAction(Player aPlayer) {
        aPlayer.setStudyHours(aPlayer.getStudyHours()+amount);
    }
}
