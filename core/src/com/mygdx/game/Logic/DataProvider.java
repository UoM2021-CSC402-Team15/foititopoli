package com.mygdx.game.Logic;

import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Cards.JailCard;
import com.mygdx.game.Logic.Cards.MoneyCard;
import com.mygdx.game.Logic.Cards.MoveCard;
import com.mygdx.game.Logic.Squares.CourseSquare;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DataProvider {

   private static final ArrayList<Pawn> pawns = new ArrayList<>();
   private static final ArrayList<CourseSquare> courseSquares = new ArrayList<>();
   private static final ArrayList<Card> cardList = new ArrayList<>();

    private static void readPawns() {
        pawns.add( new Pawn("battleship") );
        pawns.add( new Pawn("boot") );
        pawns.add( new Pawn("dog") );
        pawns.add( new Pawn("hat") );
        pawns.add( new Pawn("iron") );
        pawns.add( new Pawn("racing car") );
        pawns.add( new Pawn("thimble") );
        pawns.add( new Pawn("wheelbarrow") );
    }

    public static  Card drawCard(){
        Collections.shuffle(cardList);
        if (cardList.size() !=0) {
            return cardList.get(0);
        } else {
            return new Card("card") {
                @Override
                public void runAction(Player aPlayer) {
                    aPlayer.setStudyHours(20);
                }
            };
        }

    }

    public static ArrayList<Pawn> getPawns() {
        readPawns();
        return pawns;
    }

    public static GameInstance loadGame (FileHandle fileHandle) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileHandle.read());
            GameInstance aGame =  (GameInstance) objectInputStream.readObject();
            objectInputStream.close();

            return aGame;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void readCourses(FileHandle fileHandle) {
        String[] lines = fileHandle.readString().split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] data = lines[i].split(",");
            if (data.length == 6) {
                CourseSquare courseSquare = new CourseSquare(data[0], Integer.parseInt(data[1]), data[2], data[3], data[4], Integer.parseInt(data[5]));
                courseSquares.add(courseSquare);
            }
        }
    }

    public static ArrayList<CourseSquare> getCourses() {
        return courseSquares;
    }

    private static ArrayList<MoveCard> readMoveCards(FileHandle fileHandle){
        ArrayList<MoveCard> moveCards = new ArrayList<>();
        String[] lines = fileHandle.readString().split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] data = lines[i].split(",");
            if (data.length == 4) {
                MoveCard moveCard = new MoveCard(data[1], (Float.parseFloat(data[2]) + (Float.parseFloat(data[3])/ (float) Math.pow(10, data[3].length())  )));
                moveCards.add(moveCard);
            }
        }
        return moveCards;
    }

    private static ArrayList<MoneyCard> readMoneyCards(FileHandle fileHandle){
        ArrayList<MoneyCard> moneyCards = new ArrayList<>();
        String[] lines = fileHandle.readString().split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] data = lines[i].split(",");
            if (data.length == 3) {
                MoneyCard moneyCard = new MoneyCard(data[1], Float.parseFloat(data[2]));
                moneyCards.add(moneyCard);
            }
        }
        return moneyCards;
    }

    private static ArrayList<JailCard> readJailCards(FileHandle fileHandle) {
        ArrayList<JailCard> jailCards = new ArrayList<>();
        String[] lines = fileHandle.readString().split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] data = lines[i].split(",");
            if (data.length == 2) {
                JailCard jailCard = new JailCard(data[1]);
                jailCards.add(jailCard);
            }
        }
        return jailCards;
    }

    public static void readCards(FileHandle moveFile, FileHandle moneyFile, FileHandle jailFile) throws IOException {
        ArrayList<MoveCard> moveCards = readMoveCards(moveFile);
        ArrayList<MoneyCard> moneyCards = readMoneyCards(moneyFile);
        ArrayList<JailCard> jailCards = readJailCards(jailFile);
        cardList.clear();
        cardList.addAll(moveCards);
        cardList.addAll(moneyCards);
        cardList.addAll(jailCards);
    }
}
