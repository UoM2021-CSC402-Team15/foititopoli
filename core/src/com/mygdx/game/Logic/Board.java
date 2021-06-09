package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;
import com.mygdx.game.Logic.Squares.MoneySquare;
import com.mygdx.game.Logic.Squares.Square;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    public Square[][] squares;
    public int tilesPerSide;

    public Board(int tilesPerSide) {
        this.tilesPerSide = tilesPerSide;
        initialize();
    }

    public void initialize() {
        squares = new Square[4][tilesPerSide - 1];

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    squares[i][0] = new MoneySquare("start");
                    break;
                case 1:
                    squares[i][0] = new MoneySquare("cafeteria");
                    break;
                case 2:
                    squares[i][0] = new MoneySquare("library");
                    break;
                case 3:
                    squares[i][0] = new MoneySquare("prison");
                    break;
            }
        }

        ArrayList<CourseSquare> courses = DataProvider.getCourses();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < tilesPerSide - 1; j++) {
                squares[i][j] = courses.get(i*10+j);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tilesPerSide - 1; j++) {
                squares[i][j].setIJ(i,j);
            }
        }
    }

    public Square getDestination(Pawn pawn, int displacement) {
        int i = (pawn.getCurrentSquare().getI() + (pawn.getCurrentSquare().getJ() + displacement)/squares[0].length)%4;
        int j = (pawn.getCurrentSquare().getJ() + displacement)%squares[0].length;
        return squares[i][j];
    }

    public Square getDestination(int i, int j) {
        return squares[i][j];
    }

    public static Boolean playerPassedStart(Square start, Square end) {
        return Integer.parseInt( end.getI()+""+ end.getJ() ) < Integer.parseInt( start.getI()+""+ start.getJ() );
    }
}
