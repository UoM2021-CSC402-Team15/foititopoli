package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.DataProvider;
import com.mygdx.game.Logic.GameInstance;

/**
 * Square type that implements the Pick A Card function
 */
public class CardSquare extends Square {

    public CardSquare(String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     * Draws a random card and notifies the {@link GameInstance}
     * @param game The current Game Instance
     */
    @Override
    public void runAction(GameInstance game) {
        Card randomCard = DataProvider.drawCard();
        game.drawCard(randomCard);
    }
}
