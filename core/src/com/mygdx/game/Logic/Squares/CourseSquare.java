package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;
import com.mygdx.game.Logic.Player;

@SuppressWarnings("unused")
public class CourseSquare extends Square {
    private int grade=0;
    private int[] salary = new int[]{5,10,20,30,40,50};
    private final int semester;
    private final String direction;
    private final String description;
    private final String professors;
    private float price;


    public CourseSquare(String name, int semester, String direction, String description, String Professors, int price) {
        super(name);
        this.semester = semester;
        this.direction = direction;
        this.description = description;
        professors = Professors;
        this.price = price;
    }

    public int getSalary(){
         return salary[grade-5];
    }

    public int getUpgradeCost() {
        if (grade==10) return 0;
        return salary[grade-4];
    }

    public int[] getSalaryArray() {
        return salary;
    }

    public void upgrade(Player aPlayer){
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSemester() {
        return semester;
    }

    public String getDirection() {
        return direction;
    }

    public String getDescription() {
        return description;
    }

    public String getProfessors() {
        return professors;
    }


    @Override
    public void runAction(GameInstance game) {

    }
}
