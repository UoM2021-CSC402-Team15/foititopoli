package com.mygdx.game;

public class Player {

    Pawn pawn;

    public Player(String name, Pawn selectedPawn) {
        this.pawn = selectedPawn;
    }

    public Pawn getPawn() {
        return pawn;
    }
}
