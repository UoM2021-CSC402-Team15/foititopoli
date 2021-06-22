package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.Square;

import java.io.Serializable;

/**
 * Represents the pawn of the {@link Player} <br>
 * Stores a current and an old {@link Square} to define its location
 */
public class Pawn implements Serializable {

    private final String name;

    private Square currentSquare;
    private Square oldSquare;

    /**
     * @param name Creates a Pawn Object
     */
    public Pawn(String name) {
        this.name = name;
    }

    /**
     * Sets the input parameter as the currentSquare and its previous value to the oldSquare
     * @param currentSquare A {@link Square} from the board
     */
    public void setCurrentSquare(Square currentSquare) {
        this.oldSquare = this.currentSquare;
        this.currentSquare = currentSquare;

    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public Square getOldSquare() {
        return oldSquare;
    }

    @Override
    public String toString() {
        return name;
    }
}
