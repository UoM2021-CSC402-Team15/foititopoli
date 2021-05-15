package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.Player;

import java.util.ArrayList;

public class CourseSquare extends Square {
   private int grade=0;
   private int[] salary = new int[]{10,20,30,40,50};
   private double price;


    public CourseSquare(String name,double price) {
        super(name);
        this.price = price;


    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int[] getSalary() {
        return salary;
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
