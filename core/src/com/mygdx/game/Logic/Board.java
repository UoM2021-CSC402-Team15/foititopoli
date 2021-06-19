package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Board implements Serializable {

    public Square[][] squares;
    public final int tilesPerSide;

    public Board(int tilesPerSide) {
        this.tilesPerSide = tilesPerSide;
        initialize();
    }

    public void initialize() {
        squares = new Square[4][tilesPerSide - 1];

        // Corner Square setup
        squares[0][0] = new MoneySquare("Αφετηρία",0);
        squares[1][0] = new MoneySquare("Λέσχη", 0);
        squares[2][0] = new MoneySquare("Βιβλιοθήκη", 20);
        squares[3][0] = new TurnSquare("Πρύτανης",-3);

        // Middle Square setup
        squares[0][5] = new TurnSquare("Τραπεζάκια Κομμάτων",-1);
        squares[1][5] = new TurnSquare("Γυμναστήριο",1);
        squares[2][5] = new MoneySquare("Αίθουσα Υπολογιστών", 15);
        squares[3][5] = new MoneySquare("Κυλικείο", -15);

        // Card Square setup
        squares[0][2] = new CardSquare("Τετράγωνο Κάρτας");
        squares[0][7] = new CardSquare("Τετράγωνο Κάρτας");
        squares[1][2] = new CardSquare("Τετράγωνο Κάρτας");
        squares[1][7] = new CardSquare("Τετράγωνο Κάρτας");
        squares[2][2] = new CardSquare("Τετράγωνο Κάρτας");
        squares[2][8] = new CardSquare("Τετράγωνο Κάρτας");
        squares[3][3] = new CardSquare("Τετράγωνο Κάρτας");
        squares[3][8] = new CardSquare("Τετράγωνο Κάρτας");

        ArrayList<CourseSquare> courses = DataProvider.getCourses();
        Iterator<CourseSquare> iterator = courses.iterator();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tilesPerSide - 1; j++) {
                if (squares[i][j] == null && iterator.hasNext()) {
                    squares[i][j] = iterator.next();
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tilesPerSide - 1; j++) {
                if (squares[i][j] != null) {
                    squares[i][j].setIJ(i, j);
                }
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
