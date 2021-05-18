package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;

import java.io.*;
import java.util.ArrayList;

public class DataProvider {

    static ArrayList<Pawn> pawns = new ArrayList<>();
    static ArrayList<CourseSquare> courseSquares = new ArrayList<>();

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

    public static GameInstance loadGame (String source){
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

    public static void readCourses(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        String row;
        while ( (row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (data.length == 6) {
                CourseSquare courseSquare = new CourseSquare(data[0], Integer.parseInt(data[1]), data[2], data[3], data[4], Integer.parseInt(data[5]));
                courseSquares.add(courseSquare);
            }
        }
    }

    public static ArrayList<CourseSquare> getCourses() {
        return courseSquares;
    }
}
