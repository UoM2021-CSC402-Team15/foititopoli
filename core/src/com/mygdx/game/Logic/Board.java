package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Board is the class that generates and contains the squares that are active in the game.
 * It provides methods that make it easy to get the reference of a square in the board
 */
public class Board implements Serializable {

    private Square[][] squares;
    private final int tilesPerSide;

    /**
     * @param tilesPerSide The number of squares per side when we count the corners as being in two sides. So 11 for the normal Monopoly board
     */
    public Board(int tilesPerSide) {
        this.tilesPerSide = tilesPerSide;
        initialize();
    }

    /**
     * Necessary method to be run. Initializes the default square layout
     */
    private void initialize() {
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

        // Course Square setup
        //finds blanks in the board and fills them with CourseSquares
        ArrayList<CourseSquare> courses = DataProvider.getCourses();
        Iterator<CourseSquare> iterator = courses.iterator();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tilesPerSide - 1; j++) {
                if (squares[i][j] == null && iterator.hasNext()) {
                    squares[i][j] = iterator.next();
                }
            }
        }

        // Loops through the entire board and sets the i,j pointers in the squares
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tilesPerSide - 1; j++) {
                if (squares[i][j] != null) {
                    squares[i][j].setIJ(i, j);
                }
            }
        }
    }

    /**
     * @param pawn The pawn is to be moved
     * @param displacement How many squares the pawn should be moved
     * @return the square that the pawn should move to
     */
    public Square getSquare(Pawn pawn, int displacement) {
        int i = (pawn.getCurrentSquare().getI() + (pawn.getCurrentSquare().getJ() + displacement)/squares[0].length)%4;
        int j = (pawn.getCurrentSquare().getJ() + displacement)%squares[0].length;
        return squares[i][j];
    }

    /**
     * @param i Side of the board. Values beetween 0 and 4.
     * @param j Position of square in selected Side. Values beetween 0 and tilesPerSide-2 (0 and 9 normally) (0 counting from the rightmost corner of the side)
     * @return The square in that position of the board
     * @see <a href="https://docs.google.com/spreadsheets/d/17S6XhOd0g3WOGAadJXZW2S_RjGm83rnoW0tYwpeP1HM/edit#gid=714353109">here</a> for a diagram of the board with our coordinate system
     */
    public Square getSquare(int i, int j) {
        return squares[i][j];
    }

    /**
     * @param start The square before the move
     * @param end The square after the move
     * @return True if player has passed the start during that move
     */
    public static Boolean playerPassedStart(Square start, Square end) {
        return Integer.parseInt( end.getI()+""+ end.getJ() ) < Integer.parseInt( start.getI()+""+ start.getJ() );
    }

    /**
     * @return The "fake" number of squares per side. (11 in the default board)
     * The real number is smaller by one (10), so as not to count the corners twice (because the visually belong to two sides)
     */
    public int getTilesPerSide() {
        return tilesPerSide;
    }
}
