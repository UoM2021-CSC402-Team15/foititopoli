package com.mygdx.game.Logic;

import com.badlogic.gdx.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DataProvider {

    static ArrayList<Pawn> pawns = new ArrayList<>();

    private static void readPawns() {
        if (pawns.size() == 0) {
            pawns.add( new Pawn("dog") );
            pawns.add( new Pawn("hat") );
            pawns.add( new Pawn("thimble") );
            pawns.add( new Pawn("boot") );
            pawns.add( new Pawn("wheelbarrow") );
            pawns.add( new Pawn("cat") );
            pawns.add( new Pawn("racing car") );
            pawns.add( new Pawn("battleship") );
        }
    }

    public static ArrayList<Pawn> getPawns() {
        readPawns();
        return pawns;
    }
     public GameInstance loadGame (String source){
        try {
            FileInputStream fis = new FileInputStream(source);
            ObjectInputStream oist = new ObjectInputStream(fis);
            GameInstance aGame =  (GameInstance) oist.readObject();
            oist.close();
            fis.close();

            return aGame;
        } catch (IOException e) {

            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
