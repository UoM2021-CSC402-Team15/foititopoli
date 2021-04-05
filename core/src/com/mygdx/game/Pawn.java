package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Pawn extends Actor {

    private Sprite sprite;

    private String name;

    public Pawn(String name) {
        sprite = new Sprite( new Texture(Gdx.files.internal("pawn.png")));
        this.name = name;
        setPosition(50,50);
        setSize(60,40);
    }

    public void moveTo(float X, float Y) {
        float speed = 300;  //pixels per second
        float distance = (float) Math.sqrt( Math.pow(X-getX(),2) + Math.pow(Y-getY(),2) );
        System.out.println(distance);
        moveTo(X,Y, distance/speed);
    }

    public void moveTo(float X, float Y, float duration) {
        addAction(Actions.moveTo(X - getWidth()/2, Gdx.graphics.getHeight()-Y-getHeight()/2,duration ));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(),getY());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        sprite.setSize(getWidth(),getHeight());
    }
}
