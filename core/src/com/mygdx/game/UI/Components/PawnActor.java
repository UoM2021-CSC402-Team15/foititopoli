package com.mygdx.game.UI.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.game.Logic.Pawn;

public class PawnActor extends Actor {

    private final Sprite sprite;

    private final Pawn pawn;

    public PawnActor(Pawn pawn) {

        sprite = new Sprite( new Texture(Gdx.files.internal("images/pawns/"+pawn+".png")));
        this.pawn = pawn;
        setPosition(50,50);
        setSize(60,40);
        this.setOrigin(getWidth()/2,getHeight()/2);
        sprite.setOrigin(getWidth()/2,getHeight()/2);
    }

    public void moveTo(float X, float Y) {
        float speed = 300;  //pixels per second
        float distance = (float) Math.sqrt( Math.pow(X-getX(),2) + Math.pow(Y-getY(),2) );
        System.out.println(distance);
        moveTo(X,Y, distance/speed);
    }

    public void moveTo(float X, float Y, float duration) {
        addAction(Actions.moveTo(X - getWidth()/2, Y-getHeight()/2,duration ));
    }

    public MoveToAction getMoveLeftToSquare(SquareActor square) {
        float finalX = square.getCenter().x -getWidth()/2;
        float finalY = square.getCenter().y -getHeight()/2;

        MoveToAction action = new MoveToAction();
        action.setPosition(finalX, finalY);
        action.setDuration(1);

        return action;
    }

    public Pawn getPawn() {
        return pawn;
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
        sprite.setPosition(getX(), getY());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        sprite.setSize(getWidth(),getHeight());
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        sprite.setRotation(getRotation());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x-getWidth()/2, y-getHeight()/2);
    }
}
