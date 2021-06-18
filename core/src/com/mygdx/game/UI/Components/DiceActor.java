package com.mygdx.game.UI.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class DiceActor extends Actor {

    Texture[] diceTextures = new Texture[6];
    Texture currentTexture;

    public DiceActor() {
        super();

        for (int i = 0; i < 5; i++) {
            diceTextures[i] = new Texture(Gdx.files.internal("./images/dice/"+ (i + 1) +".png"));
        }

        currentTexture = diceTextures[0];


    }

    public void rollAnimation(int finalRoll) {
        for (int i = 0; i < 12; i++) {
            int finalI = i;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Random random = new Random();
                    currentTexture = diceTextures[random.nextInt(5)];
                    if (finalI == 11) currentTexture = diceTextures[finalRoll-1];
                }
            }, (float) Math.pow(i / 10f, 4));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentTexture, getX(), getY(), getWidth(), getHeight());
    }

}
