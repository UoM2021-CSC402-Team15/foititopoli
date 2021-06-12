package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;


public class MoneyCard extends Card {
    private float amount;

public MoneyCard(String image, float amount) {
        super(image);
        this.amount = amount;
    }

    @Override
    public void runAction(Player aPlayer) {
        aPlayer.setStudyHours(aPlayer.getStudyHours()+amount);
    }
}
