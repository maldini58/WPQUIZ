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
    String pictureURL;

    public QuizField(JSONObject jsonObject) throws JSONException{

        this.title= (String) jsonObject.optString("title","");
//        this.pictureURL= (String) jsonObject.optString("mainphoto: url","");

    }

    public QuizField(){};

    public static ArrayList<QuizField> makeQuizFields (String fieldData) throws JSONException, NullPointerException{
        ArrayList<QuizField> quizFields = new ArrayList<QuizField>();
        JSONObject data = new JSONObject(fieldData);
        JSONObject count = data.optJSONObject("count");
        JSONArray itemsArray = data.optJSONArray("items");
        for(int i = 0; i < itemsArray.length(); i++) {
            JSONObject photo=	(JSONObject) itemsArray.get(i);
            QuizField currentField = new QuizField (photo);
            quizFields.add(currentField);
        }
        return quizFields;
    }



}
