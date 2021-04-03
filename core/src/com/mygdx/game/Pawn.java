package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Pawn extends Texture {

    private String name;
    public float x = 20;
    public float y = 20;
    private float targetX;
    private float targetY;
    private float moveX;
    private float moveY;


    public Pawn(String name) {
        super(Gdx.files.internal("pawn.png"));
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setTarget(float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.moveX = (targetX - this.x)/20;
        this.moveY = (targetY - this.y)/20;
    }

    public void update() {
//        if ( isCloseToTarget(3*moveX) ) {
//            setTarget(targetX,targetY);
//        }
        if ( isCloseToTarget(moveX) ) {
            moveX = 0;
            moveY = 0;
        }
        this.x+=moveX;
        this.y+=moveY;
    }

    private Boolean isCloseToTarget(float distance) {
        return Math.abs(this.targetX - this.x) < Math.abs(distance);
    }

}
