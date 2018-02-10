package com.example.maldini.quizapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maldini on 2018-02-09.
 */
public class QuizField extends Object {

    String title;
    String imageUrl;

    public QuizField(String title, String imageUrl) throws JSONException{

        this.title= title;
        this.imageUrl= imageUrl;

    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public QuizField(){};

    public static ArrayList<QuizField> makeQuizFields (String fieldData) throws JSONException, NullPointerException{
        ArrayList<QuizField> quizFields = new ArrayList<QuizField>();
        JSONObject parentObject = new JSONObject(fieldData);
        JSONArray itemsArray = parentObject.getJSONArray("items");
        int count = parentObject.getInt("count");
        for(int i=0;i<count;i++){
            JSONObject itemObject = itemsArray.getJSONObject(i);
            JSONObject photoObject = new JSONObject(itemObject.getString("mainPhoto"));

            quizFields.add(new QuizField(itemObject.getString("title"),photoObject.getString("url")));
        }

        return quizFields;
    }







}
