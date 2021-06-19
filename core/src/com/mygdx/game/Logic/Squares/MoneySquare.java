package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

public class MoneySquare extends Square {
    private final float money;

    public MoneySquare(String name, float money) {
        super(name);
        this.money = money;
    }

    @Override
    public void runAction(GameInstance game) {
          float finalMoney = game.getCurrentPlayer().getStudyHours() + money;
          game.getCurrentPlayer().setStudyHours(finalMoney);
    }

}
