package com.example.maldini.quizapp;


public class Quiz {
    private int id;
    private int finished;
    private String title;
    private int questionNumber;
    private int wrongQuestionNumber;
    private int correctQuestionNumber;
    private int answeredQuestionNumber;
    private double result;
    static int counter = 0;

    public Quiz() {
    }

    public Quiz(String title, int questionNumber, int finished, double result) {
        this.title = title;
        this.questionNumber = questionNumber;
        this.answeredQuestionNumber = 0;
        this.wrongQuestionNumber = 0;
        this.correctQuestionNumber = 0;
        this.result = result;
        this.id = counter;
        counter++;
        if (counter >= 100) {
            counter = 0;
        }
    }

    public Quiz(String title, int questionNumber, int finished, double result, int id) {
        this.title = title;
        this.questionNumber = questionNumber;
        this.answeredQuestionNumber = 0;
        this.wrongQuestionNumber = 0;
        this.correctQuestionNumber = 0;
        this.result = result;
        this.id = id;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
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
