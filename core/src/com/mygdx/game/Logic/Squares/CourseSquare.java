package com.mygdx.game.Logic.Squares;

import com.badlogic.gdx.Game;
import com.mygdx.game.Logic.Player;

import java.util.ArrayList;

public class CourseSquare extends Square {
   private int grade=0;
   private int[] salary = new int[]{5,10,20,30,40,50};
   private double price;



    public CourseSquare(String name,double price) {
        super(name);
        this.price = price;


    }

    public int getSalary(){
         return salary[grade-5];
    }

    public void upgradePlayer(Player aPlayer){
        grade++;
        aPlayer.setStudyHours(aPlayer.getStudyHours()-salary[grade-5]);
    }

   //if this value is >=5 the course is owned
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }



    public void setSalary(int[] salary) {
        this.salary = salary;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
