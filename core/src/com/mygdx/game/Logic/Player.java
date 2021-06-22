package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the Player <br>
 *
 */
public class Player implements Serializable {
    /** Name of the player */
    private final String name;
    /** Selected {@link Pawn} Object */
    private final Pawn pawn;
    /** Currency of the game */
    private float studyHours;
    /** Switches between 1 and 0 most of the time, can take other values when a player gains turns or is imprisoned */
    private int turnsToPlay;
    /** List Of {@link CourseSquare} the player has bought */
    private final ArrayList<CourseSquare> courseList = new ArrayList<>();

    /**
     * @param name User selected name
     * @param selectedPawn Pawn Object selected
     * @param studyHours Starting currency value set by the game options
     */
    public Player(String name, Pawn selectedPawn, float studyHours) {
        this.name = name;
        this.pawn = selectedPawn;
        this.studyHours = studyHours;
        this.turnsToPlay = 1;
    }

    /**
     * To be called by the UI when the player buys a Course
     * @param c The {@link CourseSquare} being bought
     */
    public void buySquare(CourseSquare c){
        this.studyHours = this.studyHours - c.getPrice();
        c.setGrade(5);
        courseList.add(c);
    }

    /**
     * The ammount of money you get by passing start is different in Foititopoli.
     * This method calculates that ammount
     * @return The sum of salaries of each course the player has bought
     */
    public float getStartSalary() {
        float startSalary = 0;
        for (CourseSquare course: courseList) {
            startSalary+=course.getSalary();
        }
        return startSalary;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", pawn=" + pawn +
                '}';
    }

    // Getters and Setters

    public float getStudyHours() {
        return studyHours;
    }

    public void setStudyHours(float studyHours) {
        this.studyHours = studyHours;
    }

    public int getTurnsToPlay() {
        return turnsToPlay;
    }

    public void setTurnsToPlay(int turnsToPlay) {
        this.turnsToPlay = turnsToPlay;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CourseSquare> getCourseList() {
        return courseList;
    }

}
