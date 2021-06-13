package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public class MoveCard extends Card {
    private String description;
    private float location;

    public MoveCard(String description, float location) {
        super(description);
        this.description = description;
        this.location = location;

    }

    @Override
    public void runAction(Player aPlayer) {

    }
}
