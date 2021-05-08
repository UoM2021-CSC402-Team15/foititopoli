package com.mygdx.game;

import java.util.ArrayList;

public class GameInstance {

    private final int numberOfPlayers;
    private final float currency;
    private final ArrayList<Player> players = new ArrayList<>();

    private GameInstanceListener listener;
    private Board board;

    public interface GameInstanceListener {
        void pawnPositionUpdated(Pawn pawn);
    }

    public void setListener(GameInstanceListener listener) {
        this.listener = listener;
    }

    public GameInstance(int numberOfPlayers, float currency) {
        this.numberOfPlayers = numberOfPlayers;
        this.currency = currency;
        this.board = new Board(11);
    }

    public void setupPlayer(String name, Pawn pawn) {
        Player player = new Player(name, pawn);
        players.add(player);
    }

    public void movePawn(Pawn pawn, Square square) {
        pawn.setCurrentSquare(square);
        listener.pawnPositionUpdated(pawn);
    }

    public static ArrayList<Pawn> getAvailablePawns() {
        ArrayList<Pawn> availablePawns = new ArrayList<>();
        availablePawns.add( new Pawn("dog") );
        availablePawns.add( new Pawn("hat") );
        availablePawns.add( new Pawn("thimble") );
        availablePawns.add( new Pawn("boot") );
        availablePawns.add( new Pawn("wheelbarrow") );
        availablePawns.add( new Pawn("cat") );
        availablePawns.add( new Pawn("racing car") );
        availablePawns.add( new Pawn("battleship") );
        return availablePawns;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public float getCurrency() {
        return currency;
    }

    public Board getBoard() {
        return board;
    }
}
