package com.mygdx.game;

public class Board {

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
    }

    public Square getDestination(Pawn pawn, int displacement) {

        return null;
    }

    public Square getDestination(int i, int j) {

        return null;
    }

}
