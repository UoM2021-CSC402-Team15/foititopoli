package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.Square;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInstance implements Serializable {

    private final int numberOfPlayers;
    private final float currency;
    private final ArrayList<Player> players = new ArrayList<>();

    private GameInstanceListener listener;
    private Board board;

    private Player currentPlayer;

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

    public void setupPlayer(String name, Pawn pawn,double studyHours) {
        Player player = new Player(name, pawn,studyHours);
        players.add(player);
    }

    public void movePawn(Pawn pawn, Square square) {
        pawn.setCurrentSquare(square);
        listener.pawnPositionUpdated(pawn);
    }

    public void initialize() {
        if (currentPlayer==null) {
            currentPlayer = players.get(0);
        }
    }
    public void endTurn(){
        currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
    }

    public void gameLoop() {
        int dice = Dice.roll() + Dice.roll();
        System.out.println("Roll=" + dice);
        Square square = board.getDestination(currentPlayer.getPawn(), dice);
        currentPlayer.getPawn().setCurrentSquare(square);
        listener.pawnPositionUpdated(currentPlayer.getPawn());

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
