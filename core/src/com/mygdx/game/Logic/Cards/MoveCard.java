package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Board;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.Square;

/**
 * Card Type that implements the go to [LOCATION] function
 */
public class MoveCard extends Card {

    /** Two integer values (i,j) that are used to point to a square in the board, stored as a float */
    // TODO: on second thought, this is a very bad design
    private final float location;
    /** {@link com.mygdx.game.Logic.Squares.Square} reference for the player to move to. Has to be set by {@link #setSquare(Board)} at runtime */
    private Square square;

    public MoveCard(String description, float location) {
        super(description);
        this.location = location;
    }

    /**
     * {@inheritDoc}
     * Moves the player to the designated square
     * @param aPlayer The current {@link Player}
     */
    @Override
    public void runAction(Player aPlayer) {
        if (square != null) {
            aPlayer.getPawn().setCurrentSquare(square);
        } else {
            throw new IllegalArgumentException("Set square before running runAction");
        }

    }

    /**
     * @param board To be called before passing card to the UI. Traslates numeric pointer of square to a {@link Square} object
     */
    public void setSquare(Board board) {
        String[] split = String.valueOf(location).split("\\.");
        System.out.println(location);
        this.square = board.getSquare( Integer.parseInt( split[0] ), Integer.parseInt( split[1] ) );
    }
}
