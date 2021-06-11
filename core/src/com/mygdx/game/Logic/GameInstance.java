package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Cards.Card;
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
        void playerUpdated(Player aPlayer);
        void playerDrewCard(Card aCard);
        void playerWon(Player aPlayer);
        void playerLost(Player aPlayer);
    }

    public GameInstanceListener getListener() {
        return listener;
    }

    public void setListener(GameInstanceListener listener) {
        this.listener = listener;
    }

    public GameInstance(int numberOfPlayers, float currency) {
        this.numberOfPlayers = numberOfPlayers;
        this.currency = currency;
        this.board = new Board(11);
    }

    public void setupPlayer(String name, Pawn pawn,float studyHours) {
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
        //Your turn is done for this Round
        if (currentPlayer.getTurnsToPlay() <= 1){
            listener.playerUpdated(currentPlayer);
            //Find and set the next player
            SetTheNextPlayer();
        }

        //In other case th player must play again in this round and his turn must be updated
        else {
            currentPlayer.setTurnsToPlay(currentPlayer.getTurnsToPlay()-1);
        }
    }

    private void SetTheNextPlayer() {
        //Set the first nominated player
        currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());

       //If this player cant play change players until you find the one who can
        while (currentPlayer.getTurnsToPlay() <1) {
            currentPlayer.setTurnsToPlay(currentPlayer.getTurnsToPlay()+1);
            currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());

        }
    }


    public void gameLoop(int dice) {

        Square square = board.getDestination(currentPlayer.getPawn(), dice);
        currentPlayer.getPawn().setCurrentSquare(square);
        listener.pawnPositionUpdated(currentPlayer.getPawn());

        if (Board.playerPassedStart(currentPlayer.getPawn().getOldSquare(), currentPlayer.getPawn().getCurrentSquare())) {
            currentPlayer.setStudyHours(currentPlayer.getStartSalary()+currentPlayer.getStudyHours());
        }

        listener.playerUpdated(currentPlayer);

        if (currentPlayer.getStudyHours()<0)
        {
           listener.playerLost(currentPlayer);
            players.remove(currentPlayer);
        }

        if (currentPlayer.getStudyHours()>1000)
        {
            listener.playerWon(currentPlayer);
            //end game!
        }
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

    public void setCurrentPlayer(int i) {
        this.currentPlayer = players.get(i);
    }
}
