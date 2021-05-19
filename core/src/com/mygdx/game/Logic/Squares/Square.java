package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

import java.io.Serializable;

public abstract class Square implements Serializable {

    protected String name;
    public int i;
    public int j;

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
}
