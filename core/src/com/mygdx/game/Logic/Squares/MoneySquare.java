package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

/**
 * Square that implements the win/lose [AMOUNT] function
 */
public class MoneySquare extends Square {
    private final float money;

    public MoneySquare(String name, float money) {
        super(name);
        this.money = money;
    }

    /**
     * {@inheritDoc}
     * Adds the ammoun the study hours of the current {@link com.mygdx.game.Logic.Player Player}
     * @param game The current Game Instance
     */
    @Override
    public void runAction(GameInstance game) {
          float finalMoney = game.getCurrentPlayer().getStudyHours() + money;
          game.getCurrentPlayer().setStudyHours(finalMoney);
    }

}
