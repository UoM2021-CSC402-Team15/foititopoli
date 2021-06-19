package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

import java.io.Serializable;

public abstract class Square implements Serializable {

    protected final String name;
    private int i;
    private int j;

    public abstract void runAction(GameInstance game);

    public Square(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", i=" + i +
                ", j=" + j +
                '}';
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setIJ(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
