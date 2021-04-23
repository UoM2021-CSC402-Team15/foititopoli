package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class Square extends Group {

    private Sprite sprite;

    private String name;
    private Table table;

    public int i;
    public int j;

    public Square(String name) {
        this(name, "square.png");
    }
    public Square(final String name, String texture) {
        sprite = new Sprite( new Texture(Gdx.files.internal(texture)));
        this.name = name;
        setOrigin(0,0);
        sprite.setOrigin(0, 0);
        table = new Table(Foititopoli.gameSkin);
        table.setFillParent(true);
        table.debug();

        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println(name);
                System.out.println(getX() + "|" +getY());
            }
        });

        //debug();

        Label label = new Label("Hello", Foititopoli.gameSkin);
        label.setFontScale(0.5f);
        label.setAlignment(Align.center);
        table.add(label).fillX().row();

        Label label2 = new Label("Hello", Foititopoli.gameSkin);
        label.setColor(Color.RED);
        label2.setFontScale(0.5f);
        label2.setAlignment(Align.center);
        table.add(label2).expand().fill().row();

        addActor(table);
    }

    public void moveLeft(float x) {
        float moveX = x * MathUtils.cosDeg(getRotation());
        float moveY = x * MathUtils.sinDeg(getRotation());
        float finalX = getX()-moveX;
        float finalY = getY()-moveY;

        float speed = 300;  //pixels per second

        float distance = (float) Math.sqrt(Math.pow(finalX - getX(), 2) + Math.pow(finalY - getY(), 2));
        addAction(Actions.moveTo(finalX, finalY, distance/speed));
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

