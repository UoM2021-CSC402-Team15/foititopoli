package com.mygdx.game.Logic;

import java.util.Random;

public class Dice {

    public static int roll() {

        Random rand = new Random();
        return rand.nextInt(5)+1;
    }
}
