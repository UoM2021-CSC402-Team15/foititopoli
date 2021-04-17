package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Square extends Table {

    private Sprite sprite;

    private String name;

    public Square(String name) {
        this(name, "square.png");
    }
    public Square(String name, String texture) {
        sprite = new Sprite( new Texture(Gdx.files.internal(texture)));
        this.name = name;
        sprite.setOrigin(getWidth()/2,getHeight()/2);
        setPosition(50,50);
        setSize(80,120);

        this.debug();
        Label label = new Label("asfgsdgggggggggggggggggg", Foititopoli.gameSkin);
        label.setWrap(true);
        label.setBounds(getX(),getY(),getWidth(),getHeight());
        //this.add(label).width(getWidth()).row();
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
        super.draw(batch, parentAlpha);
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

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        sprite.setRotation(getRotation());
    }
}

