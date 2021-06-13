package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Player;

public class JailCard extends Card{
    private final float jailLocation = 3;

    public JailCard(String description) {
        super(description);
    }

    @Override
    public void runAction(Player aPlayer) {
           aPlayer.setTurnsToPlay(-2);
    }
}
