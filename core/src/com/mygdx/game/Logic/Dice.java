package com.mygdx.game.Logic;

import java.util.Random;

public class Dice {

    public static int roll() {

        Random rand = new Random();
        return (int) (Math.random() * (6) + 1);
    }

}
