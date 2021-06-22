package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;

import java.util.ArrayList;

/**
 * Helper class that holds trade data and executes the trade between 2 players
 */
public class Trade {
    /** The current {@link Player} or the one who initiated the trade */
    private final Player sender;
    /** The receiving {@link Player} or the one who has to accept the trade */
    private final Player receiver;
    /** The list of {@link CourseSquare} objects the sender is to give */
    private ArrayList<CourseSquare> senderCourseList = new ArrayList<>();
    /** The number of hours the sender is to give */
    private int senderHours;
    /** The list of {@link CourseSquare} objects that the receiver is to give */
    private ArrayList<CourseSquare> receiverCourseList = new ArrayList<>();
    /** The number of hours the receiver is to give */
    private int receiverHours;

    /**
     * @param sender The current {@link Player} or the one who initiated the trade
     * @param receiver The receiving {@link Player} or the one who has to accept the trade
     */
    public Trade(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Sets the Courses and Hours the sender is to give
     * @param sCourses List of {@link CourseSquare} objects
     * @param sHours Number of hours
     */
    public void setSenderOffer(ArrayList<CourseSquare> sCourses, int sHours){
        senderCourseList = sCourses;
        senderHours = sHours;
    }

    /**
     * Sets the Courses and Hours the receiver is to give
     * @param rCourses List of {@link CourseSquare} objects
     * @param rHours Number of hours
     */
    public void setReceiverOffer(ArrayList<CourseSquare> rCourses, int rHours){
       receiverCourseList = rCourses;
       receiverHours= rHours;
    }

    /**
     * Executes the Trade
     */
    public void accept(){
        receiver.setStudyHours(receiver.getStudyHours()+senderHours);
        sender.setStudyHours(sender.getStudyHours()-senderHours);

        sender.setStudyHours(sender.getStudyHours()+receiverHours);
        receiver.setStudyHours(receiver.getStudyHours()-receiverHours);

        receiver.getCourseList().addAll(senderCourseList);
        receiver.getCourseList().removeAll(receiverCourseList);

        sender.getCourseList().addAll(receiverCourseList);
        sender.getCourseList().removeAll(senderCourseList);
    }
}
