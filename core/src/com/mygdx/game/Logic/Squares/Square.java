package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;

import java.io.Serializable;

/**
 * Main component of the {@link com.mygdx.game.Logic.Board Board} class
 */
public abstract class Square implements Serializable {

    protected final String name;
    private int i;
    private int j;

    public Square(String name) {
        this.name = name;
    }

    /**
     * To be run when the {@link com.mygdx.game.Logic.Player Player} lands on this square <br>
     * @param game The current Game Instance
     */
    public abstract void runAction(GameInstance game);

    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", i=" + i +
                ", j=" + j +
                '}';
    }

    // Getters and Setters

    /**
     * @return The name of the square
     */
    public String getName() {
        return name;
    }

    /**
     * @return The number of the side it belongs [0-4]
     */
    public int getI() {
        return i;
    }

    /**
     * @return The number of the square counting from the right corner of the side [0-...]
     */
    public int getJ() {
        return j;
    }

    /**
     * To be set during initialization of the {@link com.mygdx.game.Logic.Board Board}
     * @param i The number of the side it belongs [0-4]
     * @param j The number of the square counting from the right corner of the side [0-...]
     */
    public void setIJ(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
