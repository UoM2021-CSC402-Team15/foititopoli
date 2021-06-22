package com.mygdx.game.Logic.Squares;

import com.mygdx.game.Logic.GameInstance;
import com.mygdx.game.Logic.Player;

/**
 * Square class that can be bought, upgraded and traded with other players.
 * Represents courses in a university
 */
@SuppressWarnings("unused")
public class CourseSquare extends Square {

    private int grade=0;
    private int[] salary = new int[]{5,10,20,30,40,50};
    private final int semester;
    private final String direction;
    private final String description;
    private final String professors;
    private float price;

    /**
     * @param name ex. ΤΕΧΝΟΛΟΓΙΑ ΛΟΓΙΣΜΙΚΟΥ (CSC402)
     * @param semester ex. 4
     * @param direction ex. ΕΤΥ
     * @param description ex. Αρχές Τεχνολογίας Λογισμικού. Προβλήματα στην ανάπτυξη έργων λογισμικού. Διαφορές από άλλα τεχνικά έργα....
     * @param Professors ex. Χατζηγεωργίου Αλέξανδρος
     * @param price ex. 50
     */
    public CourseSquare(String name, int semester, String direction, String description, String Professors, int price) {
        super(name);
        this.semester = semester;
        this.direction = direction;
        this.description = description;
        professors = Professors;
        this.price = price;
    }

    /**
     * {@inheritDoc}
     * Does nothing automatically, leaves it up to the UI
     * @param game The current Game Instance
     */
    @Override
    public void runAction(GameInstance game) {

    }

    /**
     * To be used to calculate the a player's total salary.
     * @see Player#getStartSalary()
     * @return The salary of the square taking into account its grade
     */
    public int getSalary(){
         return salary[grade-5];
    }

    /**
     * @return 0 when course fully upgraded or the price of the next upgrade
     */
    public int getUpgradeCost() {
        if (grade==10) return 0;
        return salary[grade-4];
    }

    /**
     * Raises the course grade by one for the specified price
     * @param aPlayer player that owns the course
     */
    public void upgrade(Player aPlayer){
        grade++;
        aPlayer.setStudyHours(aPlayer.getStudyHours()-salary[grade-5]);
    }

    /**
     * @return the array of salaries for each grade to be displayed by the UI
     */
    public int[] getSalaryArray() {
        return salary;
    }


    /**
     * If grade is >= 5
     * @return the grade of the course
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @param grade Sets the grade of the course. normal values in [5,10]
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    // Getters

    public float getPrice() {
        return price;
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
}
