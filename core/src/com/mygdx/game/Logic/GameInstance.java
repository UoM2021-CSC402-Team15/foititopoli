package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.Square;

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
