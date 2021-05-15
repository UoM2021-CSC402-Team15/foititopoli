package com.mygdx.game.Logic.Squares;

public abstract class Square {

    protected String name;

    public int i;
    public int j;

    public Square(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
