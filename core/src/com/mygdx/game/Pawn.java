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
    private final int moves = 20;
    private int currentMove = 0;


    public Pawn(String name) {
        super(Gdx.files.internal("pawn.png"));
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setTarget(float targetX, float targetY) {
        this.targetX = targetX-40;
        this.targetY = targetY-30;
        this.moveX = (targetX - this.x)/moves;
        this.moveY = (targetY - this.y)/moves;
        currentMove = 0;
    }

    public void update() {
        if ( currentMove == moves ) {
            moveX = 0;
            moveY = 0;
        }

        if ( currentMove >= moves -2 ) {
            moveX = moveX/1.5f;
            moveY = moveY/1.5f;
        }

        this.x+=moveX;
        this.y+=moveY;
        currentMove++;
    }

}
