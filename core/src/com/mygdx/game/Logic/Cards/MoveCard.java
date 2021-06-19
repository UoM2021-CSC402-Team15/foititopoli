package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Board;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.Square;

public class MoveCard extends Card {
    private final float location;
    private Square square;

    public MoveCard(String description, float location) {
        super(description);
        this.location = location;

    }

    @Override
    public void runAction(Player aPlayer) {
        if (square != null) {
            aPlayer.getPawn().setCurrentSquare(square);
        } else {
            throw new IllegalArgumentException("Set square before running runAction");
        }

    }

    public void setSquare(Board board) {
        String[] split = String.valueOf(location).split("\\.");
        System.out.println(location);
        this.square = board.getDestination( Integer.parseInt( split[0] ), Integer.parseInt( split[1] ) );
    }
}
