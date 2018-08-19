package com.grasp.models;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Result {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;

 //   @ManyToOne
   // private Topic topic;


    private String topic;

    @NotNull
    private int totalQuestion;

    @NotNull
    private int correctAnswer;

    @CreationTimestamp
    private Date TestTakenOnDate;

    private String TotalTimeTaken;

    public Result() {
    }


    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Date getTestTakenOnDate() {
        return TestTakenOnDate;
    }

    public void setTestTakenOnDate(  Date testTakenOnDate) {
        TestTakenOnDate = testTakenOnDate;
    }

    public String getTotalTimeTaken() {
        return TotalTimeTaken;
    }

    public void setTotalTimeTaken(String totalTimeTaken) {
        TotalTimeTaken = totalTimeTaken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    //  public Topic getTopic() {
    //    return topic;
   // }

   // public void setTopic(Topic topic) {
     //   this.topic = topic;
   // }

  @Override
    public String toString(){
        return "Exam Topic : " + this.getTopic() + " Correct Answers : " + this.getCorrectAnswer() + " Total Questions Attempted : " + this.getTotalQuestion() + " On Date : " + this.getTestTakenOnDate();
    }
}
