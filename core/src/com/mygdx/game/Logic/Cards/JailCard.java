package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Board;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.Square;

public class JailCard extends Card{
    private Square square;

    public JailCard(String description) {
        super(description);
    }

    @Override
    public void runAction(Player aPlayer) {
        aPlayer.setTurnsToPlay(-2);
        if (square != null) {
            aPlayer.getPawn().setCurrentSquare(square);
        } else {
            throw new IllegalArgumentException("Set square before running runAction");
        }
    }

    public void setSquare(Board board) {
        this.square = board.getDestination( 3, 0);
    }
}
