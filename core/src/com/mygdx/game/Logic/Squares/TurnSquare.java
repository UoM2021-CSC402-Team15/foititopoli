package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

public class TurnSquare extends Square{
    private final int turns;

    public TurnSquare(String name, int turns) {
        super(name);
        this.turns = turns;
    }

    /**
     * {@inheritDoc}
     * Removes or adds the specified turns number to the player
     * @param game The current Game Instance
     */
    @Override
    public void runAction(GameInstance game) {
        int finalTurns = game.getCurrentPlayer().getTurnsToPlay() + turns;
        game.getCurrentPlayer().setTurnsToPlay(finalTurns);
    }
}
