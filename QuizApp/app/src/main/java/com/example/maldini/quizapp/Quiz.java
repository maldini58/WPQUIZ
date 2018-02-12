package com.example.maldini.quizapp;

/**
 * Created by Maldini on 2018-02-12.
 */
public class Quiz {
    private boolean started;
    private int questionNumber;
    private int wrongQuestionNumber;
    private int correctQuestionNumber;
    private int answeredQuestionNumber;

    public Quiz(int questionNumber, boolean started) {
        this.questionNumber = questionNumber;
        this.started=started;
        this.answeredQuestionNumber=0;
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
}
