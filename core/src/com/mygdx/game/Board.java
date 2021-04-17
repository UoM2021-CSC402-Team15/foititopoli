package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;

public class Board extends Group {

    private final float tileHeightRatio = 1.5f;
    private float width;

    public Board(float width, int tilesPerSide) {
        this.width = width;
        setSize(width, width);
        float basicTileWidth = width/(tilesPerSide-2 + (2 * tileHeightRatio) );
        drawBoard(basicTileWidth, tilesPerSide);

    }

    private void drawBoard(float basicTileWidth, int tilePerSide) {
        float basicTileHeight = basicTileWidth * tileHeightRatio;

        // Side 0
        Square corner0 = new Square("Start","start.png");
        corner0.setSize(basicTileHeight,basicTileHeight);
        corner0.setPosition(width-corner0.getWidth(), getY());
        addActor(corner0);
        for (int i = 0; i < tilePerSide-2 ; i++) {
            Square square = new Square("square");
            square.setSize(basicTileWidth, basicTileHeight);
            square.setPosition(width-(i+1+tileHeightRatio)*square.getWidth(), getY());
            addActor(square);
        }

        // Side 1
        Square corner1 = new Square("cafeteria", "cafeteria.png");
        corner1.setSize(basicTileHeight,basicTileHeight);
        corner1.setPosition(getX(), getY()+basicTileHeight);
        corner1.setRotation(270);
        addActor(corner1);
        for (int i = 0; i < tilePerSide-2 ; i++) {
            Square square = new Square("square");
            square.setSize(basicTileWidth, basicTileHeight);
            square.setRotation(270);
            square.setPosition(getX(), (i+1+tileHeightRatio)*square.getWidth());
            addActor(square);
        }

        // Side 2
        Square corner2 = new Square("library", "library.png");
        corner2.setSize(basicTileHeight,basicTileHeight);
        corner2.setPosition(getX()+basicTileHeight, width);
        corner2.setRotation(180);
        addActor(corner2);
        for (int i = 0; i < tilePerSide-2 ; i++) {
            Square square = new Square("square");
            square.setSize(basicTileWidth, basicTileHeight);
            square.setRotation(180);
            square.setPosition((i+1+tileHeightRatio)*square.getWidth(), corner2.getY());
            addActor(square);
        }

        // Side 3
        Square corner3 = new Square("prison", "prison.png");
        corner3.setSize(basicTileHeight,basicTileHeight);
        corner3.setRotation(90);
        corner3.setPosition(width, width-basicTileHeight);
        addActor(corner3);
        for (int i = 0; i < tilePerSide-2 ; i++) {
            Square square = new Square("square");
            square.setSize(basicTileWidth, basicTileHeight);
            square.setRotation(90);
            square.setPosition(width, width - (i+1+tileHeightRatio)*square.getWidth());
            addActor(square);
        }
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
    }
}
