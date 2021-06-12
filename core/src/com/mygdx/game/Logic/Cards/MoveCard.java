package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public class MoveCard extends Card {
    private float location;

    public MoveCard(String image, float location) {
        super(image);
        this.location = location;

    }

    @Override
    public void runAction(Player aPlayer) {

    }
}
