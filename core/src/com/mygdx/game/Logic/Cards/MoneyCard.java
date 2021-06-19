package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;


public class MoneyCard extends Card {
    private final float amount;

public MoneyCard(String description, float amount) {
        super(description);
        this.amount = amount;
    }

    @Override
    public void runAction(Player aPlayer) {
        aPlayer.setStudyHours(aPlayer.getStudyHours()+amount);
    }
}
