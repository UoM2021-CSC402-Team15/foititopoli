package com.mygdx.game;

import java.util.ArrayList;

public class GameInstance {

    private int numberOfPlayers;
    private float currency;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Pawn> availablePawns = new ArrayList<>();

    public GameInstance(int numberOfPlayers, float currency) {
        this.numberOfPlayers = numberOfPlayers;
        this.currency = currency;

        availablePawns.add( new Pawn("dog") );
        availablePawns.add( new Pawn("hat") );
        availablePawns.add( new Pawn("thimble") );
        availablePawns.add( new Pawn("boot") );
        availablePawns.add( new Pawn("wheelbarrow") );
        availablePawns.add( new Pawn("cat") );
        availablePawns.add( new Pawn("racing car") );
        availablePawns.add( new Pawn("battleship") );


    }

    public void setupPlayer(String name, Pawn pawn) {
        Player player = new Player(name, pawn);
        players.add(player);
    }

    public boolean nextPlayerToSetupExists() {
        return players.size()<(numberOfPlayers-1);
    }

    public ArrayList<Pawn> getAvailablePawns() {
        return availablePawns;
    }
    public String[] getAvailablePawnStrings() {
        String[] strings = new String[availablePawns.size()];
        for (int i = 0; i < availablePawns.size(); i++) {
            strings[i] = availablePawns.get(i).toString();
        }
        return strings;
    }

    public Pawn getPawnFromString(String pawnName) {
        for (Pawn pawn: availablePawns) {
            if (pawn.toString().equals(pawnName)) {
                return pawn;
            }
        }
        return null;
    }

    public void removeFromAvailablePawns(Pawn pawn) {
        availablePawns.remove(pawn);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
