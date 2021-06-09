package com.mygdx.game.Logic;

import com.mygdx.game.Logic.Squares.CourseSquare;
import java.util.ArrayList;

public class Trade {
   private Player sender;
   private Player receiver;
   private ArrayList<CourseSquare> senderCourseList = new ArrayList<CourseSquare>();
   private int senderHours;
   private ArrayList<CourseSquare> receiverCourseList = new ArrayList<CourseSquare>();
   private int receiverHours;

    public Trade(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setSenderOffer(ArrayList<CourseSquare> sCourses, int sHours){
        senderCourseList = sCourses;
        senderHours = sHours;
    }
    public void setReceiverOffer(ArrayList<CourseSquare> rCourses, int rHours){
       receiverCourseList = rCourses;
       receiverHours= rHours;
    }

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
