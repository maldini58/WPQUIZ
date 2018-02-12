package com.example.maldini.quizapp;

/**
 * Created by Maldini on 2018-02-12.
 */
public class Quiz {
    private boolean started;
    private String title;
    private int questionNumber;
    private int wrongQuestionNumber;
    private int correctQuestionNumber;
    private int answeredQuestionNumber;
    private double result;

    public Quiz(String title,int questionNumber, boolean started) {
        this.title = title;
        this.questionNumber = questionNumber;
        this.started=started;
        this.answeredQuestionNumber=0;
        this.wrongQuestionNumber=0;
        this.correctQuestionNumber=0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getWrongQuestionNumber() {
        return wrongQuestionNumber;
    }

    public void setWrongQuestionNumber(int wrongQuestionNumber) {
        this.wrongQuestionNumber = wrongQuestionNumber;
    }

    public int getCorrectQuestionNumber() {
        return correctQuestionNumber;
    }

    public void setCorrectQuestionNumber(int correctQuestionNumber) {
        this.correctQuestionNumber = correctQuestionNumber;
    }

    public int getAnsweredQuestionNumber() {
        return answeredQuestionNumber;
    }

    public void setAnsweredQuestionNumber(int answeredQuestionNumber) {
        this.answeredQuestionNumber = answeredQuestionNumber;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

}
