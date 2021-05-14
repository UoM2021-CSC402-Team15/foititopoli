package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.Square;

public class Pawn {

    private final String name;

    private Square currentSquare;
    private Square oldSquare;

    public Pawn(String name) {
        this.name = name;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(Square currentSquare) {
        this.oldSquare = this.currentSquare;
        this.currentSquare = currentSquare;
    }

    public Square getOldSquare() {
        return oldSquare;
    }

    @Override
    public String toString() {
        return name;
    }
}
