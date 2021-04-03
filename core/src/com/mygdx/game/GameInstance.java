package com.mygdx.game;

import java.util.ArrayList;

public class GameInstance {

    private int numberOfPlayers;
    private float currency;
    private ArrayList<Player> players = new ArrayList<>();

    public GameInstance(int numberOfPlayers, float currency) {
        this.numberOfPlayers = numberOfPlayers;
        this.currency = currency;
    }

    public void setupPlayer(String name, Pawn pawn) {
        Player player = new Player(name, pawn);
        players.add(player);
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
}
