package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;

import java.util.ArrayList;

public class Trade {
   private Player sender;
   private Player Receiver;
   private ArrayList<CourseSquare> senderCoursesList= new ArrayList<CourseSquare>();
   private int senderHours;
   private ArrayList<CourseSquare> receiverCoursesList= new ArrayList<CourseSquare>();
   private int receiverHours;

    public Trade(Player sender, Player receiver) {
        this.sender = sender;
        Receiver = receiver;
    }

    public void setSenderOffer(ArrayList<CourseSquare> sCourses, int sHours){
        senderCoursesList = sCourses;
        senderHours = sHours;
    }
    public void setReceiverOffer(ArrayList<CourseSquare> rCourses, int rHours){
       receiverCoursesList = rCourses;
       receiverHours= rHours;
    }

    public void accept(){
        Receiver.setStudyHours(Receiver.getStudyHours()+senderHours);
        sender.setStudyHours(sender.getStudyHours()-senderHours);

        sender.setStudyHours(sender.getStudyHours()+receiverHours);
        Receiver.setStudyHours(Receiver.getStudyHours()-receiverHours);

        Receiver.addSquares(senderCoursesList);
        Receiver.removeSquares(receiverCoursesList);

        sender.addSquares(receiverCoursesList);
        sender.removeSquares(senderCoursesList);

    }


}
