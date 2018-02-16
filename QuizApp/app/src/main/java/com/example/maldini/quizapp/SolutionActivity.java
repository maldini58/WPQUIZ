package com.example.maldini.quizapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SolutionActivity extends Activity {

    Quiz quiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        Intent intent = getIntent();
        String title = intent.getStringExtra("com.talkingandroid.MESSAGE");
        int id = intent.getIntExtra("com.talkingandroid.MESSAGE_ID", -1);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        quiz = new Quiz(title, 5, 0, 0, id);


        FirstFragment f1 = new FirstFragment();
        f1.setQuiz(quiz);
        fragmentTransaction.add(R.id.fragment_container, f1);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
