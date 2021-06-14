package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Cards.JailCard;
import com.mygdx.game.Logic.Cards.MoneyCard;
import com.mygdx.game.Logic.Cards.MoveCard;
import com.mygdx.game.Logic.Squares.CourseSquare;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DataProvider {

   private static ArrayList<Pawn> pawns = new ArrayList<>();
   private static ArrayList<CourseSquare> courseSquares = new ArrayList<>();
   private static ArrayList<Card> cardList = new ArrayList<>();

    private static void readPawns() {
        if (pawns.size() == 0) {
            pawns.add( new Pawn("dog") );
            pawns.add( new Pawn("hat") );
            pawns.add( new Pawn("thimble") );
            pawns.add( new Pawn("boot") );
            pawns.add( new Pawn("wheelbarrow") );
            pawns.add( new Pawn("racing car") );
            pawns.add( new Pawn("battleship") );
        }
    }
    public static  Card drawCard(){
        Random random = new Random();
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

    public static GameInstance loadGame (InputStream stream){
        try {
            ObjectInputStream oist = new ObjectInputStream(stream);
            GameInstance aGame =  (GameInstance) oist.readObject();
            oist.close();
            stream.close();

            return aGame;
        } catch (IOException e) {

            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void readCourses(InputStream stream) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {
            reader.readLine();
            String str;
            while ((str = reader.readLine()) != null) {
                String[] data = str.split(",");
                if (data.length == 6) {
                    CourseSquare courseSquare = new CourseSquare(data[0], Integer.parseInt(data[1]), data[2], data[3], data[4], Integer.parseInt(data[5]));
                    courseSquares.add(courseSquare);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CourseSquare> getCourses() {
        return courseSquares;
    }

    private static ArrayList<MoveCard> readMoveCards(InputStream stream) throws IOException {
        ArrayList<MoveCard> moveCards = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {
            reader.readLine();
            String str;
            while ((str = reader.readLine()) != null) {
                String[] data = str.split(",");
                if (data.length == 4) {
                    MoveCard moveCard = new MoveCard(data[1], (Float.parseFloat(data[2]) + (Float.parseFloat(data[3])/ (float) Math.pow(10, data[3].length())  )));
                    moveCards.add(moveCard);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return moveCards;
    }

    private static ArrayList<MoneyCard> readMoneyCards(InputStream stream) throws IOException {
        ArrayList<MoneyCard> moneyCards = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {
            reader.readLine();
            String str;
            while ((str = reader.readLine()) != null) {
                String[] data = str.split(",");
                if (data.length == 3) {
                    MoneyCard moneyCard = new MoneyCard(data[1], Float.parseFloat(data[2]));
                    moneyCards.add(moneyCard);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return moneyCards;
    }

    private static ArrayList<JailCard> readJailCards(InputStream stream) throws IOException {
        ArrayList<JailCard> jailCards = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {
            reader.readLine();
            String str;
            while ((str = reader.readLine()) != null) {
                String[] data = str.split(",");
                if (data.length == 2) {
                    JailCard jailCard = new JailCard(data[1]);
                    jailCards.add(jailCard);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jailCards;
    }

    public static void readCards(InputStream moveStream, InputStream moneyStream, InputStream jailStream) throws IOException {
        ArrayList<MoveCard> moveCards = readMoveCards(moveStream);
        ArrayList<MoneyCard> moneyCards = readMoneyCards(moneyStream);
        ArrayList<JailCard> jailCards = readJailCards(jailStream);
        cardList.clear();
        cardList.addAll(moveCards);
        cardList.addAll(moneyCards);
        cardList.addAll(jailCards);
    }
}
