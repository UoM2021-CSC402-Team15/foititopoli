package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Cards.JailCard;
import com.mygdx.game.Logic.Cards.MoveCard;
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

    public void drawCard(Card card) {
        if ( card instanceof MoveCard ) {
            ((MoveCard) card).setSquare(board);
        } else if ( card instanceof JailCard) {
            ((JailCard) card).setSquare(board);
        }
        listener.playerDrewCard(card);
    }

    public void initialize() {
        if (currentPlayer==null) {
            currentPlayer = players.get(0);
        }
    }
    public void endTurn(){

        // Lose check
        if (currentPlayer.getStudyHours()<0) {
            listener.playerLost(currentPlayer);
            currentPlayer.setTurnsToPlay(Integer.MIN_VALUE);
        }

        // Win check
        if (currentPlayer.getStudyHours()>1000 || otherPlayersLost()) {
            listener.playerWon(currentPlayer);
            //end game!
        }

        //Your turn is done for this Round
        if (currentPlayer.getTurnsToPlay() <= 0){
            //Find and set the next player
             currentPlayer = getNextValidPlayer();
        }

    }

    private Boolean otherPlayersLost() {
        for (Player player: players) {
            if (player != currentPlayer && player.getTurnsToPlay()>-10 && player.getStudyHours()>=0) {
                return false;
            }
        }
        return true;
    }

    private Player getNextValidPlayer() {
        //Set the first nominated player
        Player possibleNextPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());

        while (possibleNextPlayer.getTurnsToPlay()<=0){
            possibleNextPlayer.setTurnsToPlay(possibleNextPlayer.getTurnsToPlay()+1);
            possibleNextPlayer = players.get((players.indexOf(possibleNextPlayer)+1)%players.size());
        }
        return  possibleNextPlayer;

    }


    public void gameLoop(int dice) {
        currentPlayer.setTurnsToPlay(currentPlayer.getTurnsToPlay()-1);

        Square square = board.getDestination(currentPlayer.getPawn(), dice);
        currentPlayer.getPawn().setCurrentSquare(square);
        listener.pawnPositionUpdated(currentPlayer.getPawn());

        if (Board.playerPassedStart(currentPlayer.getPawn().getOldSquare(), currentPlayer.getPawn().getCurrentSquare())) {
            currentPlayer.setStudyHours(currentPlayer.getStartSalary()+currentPlayer.getStudyHours());
        }

        square.runAction(this);

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
