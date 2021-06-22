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

/**
 *Contains Methods that parse data from files and return lists of objects
 */
public class DataProvider {

   private static ArrayList<CourseSquare> courseSquares = new ArrayList<>();
   private static ArrayList<Card> cardList = new ArrayList<>();

    /**
     * @param stream file stream of serialized {@link GameInstance} Object
     * @return the deserialized {@link GameInstance} Object
     */
    public static GameInstance loadGame (InputStream stream) {
        try {
            ObjectInputStream oist = new ObjectInputStream(stream);
            GameInstance aGame =  (GameInstance) oist.readObject();
            oist.close();
            stream.close();

            return aGame;
        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return a list of the default Monopoly pawns
     */
    public static ArrayList<Pawn> getPawns() {
        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add( new Pawn("battleship") );
        pawns.add( new Pawn("boot") );
        pawns.add( new Pawn("dog") );
        pawns.add( new Pawn("hat") );
        pawns.add( new Pawn("iron") );
        pawns.add( new Pawn("racing car") );
        pawns.add( new Pawn("thimble") );
        pawns.add( new Pawn("wheelbarrow") );
        return pawns;
    }

    /**
     * @param stream the stream of a .csv file with data to construct {@link CourseSquare} Objects
     * @see <a href="https://docs.google.com/spreadsheets/d/17S6XhOd0g3WOGAadJXZW2S_RjGm83rnoW0tYwpeP1HM/edit#gid=1362035742">here</a> for the Google Sheet that the .csv was exported from
     */
    public static void readCourses(InputStream stream) {
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

    /**
     * You must first run {@link #readCourses(InputStream)} or the list will be empty
     * @return a list of {@link CourseSquare}
     */
    public static ArrayList<CourseSquare> getCourses() {
        return courseSquares;
    }

    /**
     * private method to be used by {@link #readCards(InputStream, InputStream, InputStream)}
     * @param stream the stream of a .csv file with data to construct {@link MoveCard} Objects
     * @return a list with {@link MoveCard} Objects
     */
    private static ArrayList<MoveCard> readMoveCards(InputStream stream) {
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

    /**
     * private method to be used by {@link #readCards(InputStream, InputStream, InputStream)}
     * @param stream the stream of a .csv file with data to construct {@link MoneyCard} Objects
     * @return a list with {@link MoneyCard} Objects
     */
    private static ArrayList<MoneyCard> readMoneyCards(InputStream stream) {
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

    /**
     * private method to be used by {@link #readCards(InputStream, InputStream, InputStream)}
     * @param stream the stream of a .csv file with data to construct {@link JailCard} Objects
     * @return a list with {@link JailCard} Objects
     */
    private static ArrayList<JailCard> readJailCards(InputStream stream) {
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

    /**
     * @param moveStream the stream of a .csv file with data to construct {@link MoveCard} Objects
     * @param moneyStream the stream of a .csv file with data to construct {@link MoneyCard} Objects
     * @param jailStream the stream of a .csv file with data to construct {@link JailCard} Objects
     */
    public static void readCards(InputStream moveStream, InputStream moneyStream, InputStream jailStream) {
        ArrayList<MoveCard> moveCards = readMoveCards(moveStream);
        ArrayList<MoneyCard> moneyCards = readMoneyCards(moneyStream);
        ArrayList<JailCard> jailCards = readJailCards(jailStream);
        cardList.clear();
        cardList.addAll(moveCards);
        cardList.addAll(moneyCards);
        cardList.addAll(jailCards);
    }

    /**
     * @return A list of {@link Card} Objects (currently: {@link MoveCard}, {@link MoneyCard} and {@link JailCard})
     */
    public static  Card drawCard() {
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
}
