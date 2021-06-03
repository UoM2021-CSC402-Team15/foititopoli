package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public class JailCard extends Card{
    private final float jailLocation = 3;

    public JailCard(String image) {
        super(image);
    }

    @Override
    public float runAction(Player aPlayer) {
        return jailLocation;
    }
}
