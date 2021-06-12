package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.DataProvider;
import com.mygdx.game.Logic.GameInstance;

public class CardSquare extends Square {
    public CardSquare(String name) {
        super(name);
    }

    @Override
    public void runAction(GameInstance game) {
        Card randomCard = DataProvider.drawCard();
        game.getListener().playerDrewCard(randomCard);
    }
}
