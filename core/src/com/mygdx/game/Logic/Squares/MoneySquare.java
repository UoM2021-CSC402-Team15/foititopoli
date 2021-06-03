package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

public class MoneySquare extends Square {
    private float money;

    @Override
    public void runAction(GameInstance game) {
          float finalMoney = game.getCurrentPlayer().getStudyHours() + money;
          game.getCurrentPlayer().setStudyHours(finalMoney);
          game.getListener().playerUpdated(game.getCurrentPlayer());

    }

    public MoneySquare(String name) {
        super(name);

    }

}
