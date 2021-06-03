package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;
import com.mygdx.game.Logic.Squares.Square;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private final String name;
    private final Pawn pawn;
    private float studyHours;
    private float startSalary;
    private int turnsToPlay;
    private boolean isActive;
    private ArrayList<CourseSquare> courseList = new ArrayList<CourseSquare>();

    public Player(String name, Pawn selectedPawn,float studyHours) {
        this.name = name;
        this.pawn = selectedPawn;
        this.studyHours = studyHours;
        this.startSalary =100;
        this.turnsToPlay = 1;
        this.isActive = true;

    }
    public void buySquare(CourseSquare c){
        this.studyHours = this.studyHours - c.getPrice();
        c.setGrade(5);
        courseList.add(c);
    }

    public float getStudyHours() {
        return studyHours;
    }

    public void setStudyHours(float studyHours) {
        this.studyHours = studyHours;
    }

    public float getStartSalary() {
        return startSalary;
    }

    public void setStartSalary(float startSalary) {
        this.startSalary = startSalary;
    }

    public int getTurnsToPlay() {
        return turnsToPlay;
    }

    public void setTurnsToPlay(int turnsToPlay) {
        this.turnsToPlay = turnsToPlay;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", pawn=" + pawn +
                '}';
    }
}
