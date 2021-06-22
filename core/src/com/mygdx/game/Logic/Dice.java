package com.mygdx.game.Logic;

import java.util.Random;

/**
 * The Dice class was supposed to represent the dice in game (both UI and Logic).
 * It was later decided to separate the UI from the Logic so this class only has a single method <br>
 * See {@link com.mygdx.game.UI.Components.DiceActor DiceActor} for the UI segment
 */
public class Dice {


    /**
     * @return A random integer in [1,6]
     */
    public static int roll() {
        Random rand = new Random();
        return rand.nextInt(5)+1;
    }
}
