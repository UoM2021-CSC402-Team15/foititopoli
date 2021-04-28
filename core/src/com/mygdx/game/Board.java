package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.Arrays;
import java.util.List;

public class Board extends Group {

    private final float tileHeightRatio = 1.5f;
    private final int tilesPerSide = 11;

    public Square[][] squares;

    public Board(float size) {
        setSize(size, size);
        float basicTileWidth = size/(tilesPerSide-2 + (2 * tileHeightRatio) );
        drawBoard(basicTileWidth);

    }

    public SequenceAction movePawn(Pawn pawn, Square destination, SequenceAction sequence) {

        for (int i = 0; i < 10; i++) { //So it doesnt continue to infinity

            int currentI = pawn.getCurrentSquare().i;
            int currentJ = pawn.getCurrentSquare().j;

            if ( currentI == destination.i && currentJ < destination.j ) { // If on same side and target forward
                sequence.addAction(pawn.getMoveLeftToSquare(destination));
                pawn.setCurrentSquare(destination);
                break;
            } else if ( (currentI+1)%4==destination.i && destination.j ==0 ) { // If target is next corner (next corner is considered other side)
                sequence.addAction(pawn.getMoveLeftToSquare(destination));
                RotateByAction rotate = new RotateByAction();
                rotate.setAmount(-90);
                rotate.setDuration(0.5f);
                sequence.addAction(rotate);
                pawn.setCurrentSquare(destination);
                break;
            } else {                                                       // else go to the next side and repeat
                Square endSquare = squares[(currentI+1)%4][0];
                sequence.addAction(pawn.getMoveLeftToSquare(endSquare));
                RotateByAction rotate = new RotateByAction();
                rotate.setAmount(-90);
                rotate.setDuration(0.5f);
                sequence.addAction(rotate);
                pawn.setCurrentSquare(endSquare);
            }
        }

        return sequence;
    }

    private void drawBoard(float basicTileWidth) {
        float basicTileHeight = basicTileWidth * tileHeightRatio;

        squares = new Square[4][tilesPerSide - 1];

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    squares[i][0] = new Square("Start", "start.png");
                    break;
                case 1:
                    squares[i][0] = new Square("cafeteria", "cafeteria.png");
                    break;
                case 2:
                    squares[i][0] = new Square("library", "library.png");
                    break;
                case 3:
                    squares[i][0] = new Square("prison", "prison.png");
                    break;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < tilesPerSide - 1; j++) {
                squares[i][j] = new Square("square: " + i + "| " + j);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tilesPerSide - 1; j++) {
                squares[i][j].i =i;
                squares[i][j].j =j;
            }
        }


        drawSide(0, Arrays.asList(squares[0]), basicTileWidth, getWidth()-basicTileHeight,0);
        drawSide(-90, Arrays.asList(squares[1]), basicTileWidth, 0, basicTileHeight);
        //noinspection SuspiciousNameCombination
        drawSide(-180, Arrays.asList(squares[2]), basicTileWidth, basicTileHeight, getHeight());
        drawSide(-270, Arrays.asList(squares[3]), basicTileWidth, getWidth(), getHeight()-basicTileHeight);
    }

    private void drawSide(int rotation, List<Square> squares, float basicTileWidth, float startX, float startY) {
        float basicTileHeight = basicTileWidth * tileHeightRatio;

        //noinspection SuspiciousNameCombination
        squares.get(0).setSize(basicTileHeight, basicTileHeight);
        squares.get(0).setRotation(rotation);
        squares.get(0).setPosition(startX,startY);
        addActor(squares.get(0));
        for (int i = 1; i < squares.size(); i++) {
            squares.get(i).setSize(basicTileWidth, basicTileHeight);
            squares.get(i).setRotation(rotation);
            squares.get(i).setPosition(startX,startY);
            addActor(squares.get(i));
            squares.get(i).moveLeft(i*basicTileWidth);
        }
    }

}
