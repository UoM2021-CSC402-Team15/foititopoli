package com.mygdx.game.Logic;

public class Player {

    private final String name;
    private final Pawn pawn;

    public Player(String name, Pawn selectedPawn) {
        this.name = name;
        this.pawn = selectedPawn;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public String getName() {
        return name;
    }
}
