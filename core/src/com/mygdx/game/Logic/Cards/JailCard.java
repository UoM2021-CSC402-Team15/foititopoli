package com.mygdx.game.Logic.Cards;

import com.mygdx.game.Logic.Board;
import com.mygdx.game.Logic.Player;
import com.mygdx.game.Logic.Squares.Square;

/**
 * Card Type that implements the Go To Jail card function
 */
public class JailCard extends Card{

    /** {@link com.mygdx.game.Logic.Squares.Square} reference for the player to move to. Has to be set by {@link #setSquare(Board)} at runtime */
    private Square square;

    public JailCard(String description) {
        super(description);
    }

    /**
     * {@inheritDoc}
     * Removes 2 turns from the player and moves them to the prison
     * @param aPlayer The current {@link Player}
     */
    @Override
    public void runAction(Player aPlayer) {
        aPlayer.setTurnsToPlay(-2);
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
        this.square = board.getSquare( 3, 0);
    }
}
