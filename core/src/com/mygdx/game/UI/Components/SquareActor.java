package com.mygdx.game.UI.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Foititopoli;
import com.mygdx.game.Logic.Squares.Square;

public class SquareActor extends Group {

    private Square square;

    private Sprite sprite;

    public SquareActor(final Square square) {
        this.square = square;
        try {
            sprite = new Sprite( new Texture(Gdx.files.internal(square.getName()+".png")));
        } catch (Exception e) {
            sprite = new Sprite( new Texture(Gdx.files.internal("square.png")));
        }
        setOrigin(0,0);
        sprite.setOrigin(0, 0);
        Table table = new Table(Foititopoli.gameSkin);
        table.setFillParent(true);

        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println(square.getName());
                System.out.println(getX() + "|" +getY());
            }
        });

        Label label = new Label("Hello", Foititopoli.gameSkin);
        label.setFontScale(0.5f);
        label.setAlignment(Align.center);
        //table.add(label).fillX().row();

        Label label2 = new Label("Hello", Foititopoli.gameSkin);
        label.setColor(Color.RED);
        label2.setFontScale(0.5f);
        label2.setAlignment(Align.center);
        //table.add(label2).expand().fill().row();

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

    public Vector2 getCenter() {
        Vector2 centerInParent = localToParentCoordinates(new Vector2(getWidth() / 2, getHeight() / 2));
        return centerInParent;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //sprite.draw(batch);
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
