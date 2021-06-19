package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

public class TurnSquare extends Square{
    private final int turns;

    public TurnSquare(String name, int turns) {
        super(name);
        this.turns = turns;
    }

    @Override
    public void runAction(GameInstance game) {
        int finalTurns = game.getCurrentPlayer().getTurnsToPlay() + turns;
        game.getCurrentPlayer().setTurnsToPlay(finalTurns);
    }
}
