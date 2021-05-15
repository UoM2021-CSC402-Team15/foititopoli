package com.mygdx.game.UI.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.Logic.Board;
import com.mygdx.game.Logic.Pawn;
import com.mygdx.game.Logic.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BoardGroup extends Group {

    private Board board;

    private HashMap<Pawn, PawnActor> pawnActors = new HashMap<>();
    private SquareActor[][] squareActors;

    private Texture background;

    private float tileHeightRatio = 1.6f;

    public BoardGroup(Board board, float size, ArrayList<Player> players) {
        this.board = board;
        setSize(size, size);
        float basicTileWidth = size/(board.tilesPerSide-2 + (2 * tileHeightRatio) );

        squareActors = new SquareActor[board.squares.length][board.squares[0].length];
        for (int i = 0; i < board.squares.length; i++) {
            for (int j = 0; j < board.squares[i].length; j++) {
                System.out.println("test");
                squareActors[i][j] = new SquareActor(board.squares[i][j]);
            }
        }
        background = new Texture(Gdx.files.internal("1_3.png"));
        drawBoard(basicTileWidth);

        for(Player player: players) {
            pawnActors.put(player.getPawn(),new PawnActor(player.getPawn()));
        }
        for(PawnActor pawnActor: pawnActors.values()) { ;
            this.addActor(pawnActor);
            pawnActor.getPawn().setCurrentSquare(board.squares[0][0]);
            pawnActor.moveTo(squareActors[0][0].getCenter().x, squareActors[0][0].getCenter().y);
        }
    }



    public void movePawn(Pawn pawn) {

        SequenceAction sequence = new SequenceAction();

        PawnActor pawnActor = pawnActors.get(pawn);

        int currentI = pawn.getOldSquare().i;
        int currentJ = pawn.getOldSquare().j;

        int destinationI = pawn.getCurrentSquare().i;
        int destinationJ = pawn.getCurrentSquare().j;

        for (int i = 0; i < 10; i++) { //So it doesnt continue to infinity

            if ( currentI == destinationI && currentJ < destinationJ ) { // If on same side and target forward
                SquareActor destination = squareActors[destinationI][destinationJ];
                sequence.addAction(pawnActor.getMoveLeftToSquare(destination));
                break;
            } else if ( (currentI+1)%4==destinationI && destinationJ ==0 ) { // If target is next corner (next corner is considered other side)
                SquareActor destination = squareActors[destinationI][destinationJ];
                sequence.addAction(pawnActor.getMoveLeftToSquare(destination));
                RotateByAction rotate = new RotateByAction();
                rotate.setAmount(-90);
                rotate.setDuration(0.5f);
                sequence.addAction(rotate);
                break;
            } else {                                    // else go to the next side and repeat
                currentI = (currentI+1)%4;
                currentJ = 0;
                SquareActor endSquare = squareActors[currentI][currentJ];
                sequence.addAction(pawnActor.getMoveLeftToSquare(endSquare));
                RotateByAction rotate = new RotateByAction();
                rotate.setAmount(-90);
                rotate.setDuration(0.5f);
                sequence.addAction(rotate);
            }
        }
        pawnActor.addAction(sequence);
    }

    private void drawBoard(float basicTileWidth) {
        float basicTileHeight = basicTileWidth * tileHeightRatio;

        drawSide(0, Arrays.asList(squareActors[0]), basicTileWidth, getWidth()-basicTileHeight,0);
        drawSide(-90, Arrays.asList(squareActors[1]), basicTileWidth, 0, basicTileHeight);
        //noinspection SuspiciousNameCombination
        drawSide(-180, Arrays.asList(squareActors[2]), basicTileWidth, basicTileHeight, getHeight());
        drawSide(-270, Arrays.asList(squareActors[3]), basicTileWidth, getWidth(), getHeight()-basicTileHeight);
    }

    private void drawSide(int rotation, List<SquareActor> squares, float basicTileWidth, float startX, float startY) {
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(background,getX(),getY(),getWidth(),getHeight());
    }
}
