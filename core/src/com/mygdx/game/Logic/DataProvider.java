package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Cards.Card;
import com.mygdx.game.Logic.Squares.CourseSquare;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
            pawns.add( new Pawn("cat") );
            pawns.add( new Pawn("racing car") );
            pawns.add( new Pawn("battleship") );
        }
    }
    public static  Card drawCard(){
        Random random = new Random();
        int randomNumber = random.nextInt(cardList.size());
         return cardList.get(randomNumber);
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
}
