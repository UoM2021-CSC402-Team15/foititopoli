package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Cards.JailCard;
import com.mygdx.game.Logic.Cards.MoveCard;
import com.mygdx.game.Logic.Squares.Square;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The "main" class of the Game. Contains the rules and connects the Logic segment of the project together.
 * This is the class that gets serialized when saving the game.
 */
public class GameInstance implements Serializable {

    private final int numberOfPlayers;
    private final ArrayList<Player> players = new ArrayList<>();

    private GameInstanceListener listener;
    private final Board board;

    private Player currentPlayer;

    /**
     * This interface is to be implemented by the UI in order to be notified about events that happen by the game logic
     */
    public interface GameInstanceListener {
        /** This pawn has moved in the board */
        void pawnPositionUpdated(Pawn pawn);
        /** This Player has a variable with a changed value */
        void playerUpdated(Player aPlayer);
        /** A card has been activated by the player */
        void playerDrewCard(Card aCard);
        /** This Player has won the game */
        void playerWon(Player aPlayer);
        /** This Player has lost the game */
        void playerLost(Player aPlayer);
    }

    /**
     * @param numberOfPlayers Number of players that play the game. Works with any possitive number but normal values are in [2,8]
     */
    public GameInstance(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.board = new Board(11);
    }

    public void initialize() {
        if (currentPlayer==null) {
            currentPlayer = players.get(0);
        }
    }

    /**
     * Called by {@link com.mygdx.game.Logic.Squares.CardSquare CardSquare} to notify the UI so that it can display a message to the user
     * {@link MoveCard} and {@link JailCard}.<br>
     * Objects also need to set their resulting square to the current board. It need to be at runtime because cards are constructed using csv files with no reference to the current Board
     * @param card The {@link Card} that has been drawn
     */
    public void drawCard(Card card) {
        if ( card instanceof MoveCard ) {
            ((MoveCard) card).setSquare(board);
        } else if ( card instanceof JailCard) {
            ((JailCard) card).setSquare(board);
        }
        listener.playerDrewCard(card);
    }

    /**
     * To be called by the UI at the start of the turn. <br>
     * Moves the player, runs the action of the resulting square and checks if player passed start
     * @param dice By how many squares should the {@link Pawn} move.
     */
    public void gameLoop(int dice) {
        currentPlayer.setTurnsToPlay(currentPlayer.getTurnsToPlay()-1);

        Square square = board.getSquare(currentPlayer.getPawn(), dice);
        currentPlayer.getPawn().setCurrentSquare(square);
        listener.pawnPositionUpdated(currentPlayer.getPawn());

        if (Board.playerPassedStart(currentPlayer.getPawn().getOldSquare(), currentPlayer.getPawn().getCurrentSquare())) {
            currentPlayer.setStudyHours(currentPlayer.getStartSalary()+currentPlayer.getStudyHours());
        }

        square.runAction(this);

    }

    /**
     * To be called by the UI when the player finished his turn <br>
     * Checks if the player lost or won and proceeds to the next player
     */
    public void endTurn() {

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

    /**
     * @return true if all other players except the current {@link Player} have lost the game
     */
    private Boolean otherPlayersLost() {
        for (Player player: players) {
            if (player != currentPlayer && player.getTurnsToPlay()>-10 && player.getStudyHours()>=0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return The next {@link Player} of the current {@link Player} that has a turn available to play (>0)
     */
    private Player getNextValidPlayer() {
        //Set the first nominated player
        Player possibleNextPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());

        while (possibleNextPlayer.getTurnsToPlay()<=0){
            possibleNextPlayer.setTurnsToPlay(possibleNextPlayer.getTurnsToPlay()+1);
            possibleNextPlayer = players.get((players.indexOf(possibleNextPlayer)+1)%players.size());
        }
        return  possibleNextPlayer;

    }

    // GETTERS AND SETTERS

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
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

    public GameInstanceListener getListener() {
        return listener;
    }

    public void setListener(GameInstanceListener listener) {
        this.listener = listener;
    }
}
