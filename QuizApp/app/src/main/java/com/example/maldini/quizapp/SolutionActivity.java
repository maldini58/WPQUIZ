package com.example.maldini.quizapp;

import android.app.Activity;
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

public class SolutionActivity extends AppCompatActivity {

    TextView textViewTitle;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;
    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        Intent intent = getIntent();
        String title = intent.getStringExtra("com.talkingandroid.MESSAGE");

        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
       progressBar = (ProgressBar)findViewById(R.id.solutionBar);
        textViewTitle.setText(title);
        quiz = new Quiz(5,false);
        new ProgressTask().execute();


        radioGroup = (RadioGroup)findViewById(R.id.myRadioGroup);
        answer1 = (RadioButton)findViewById(R.id.radioButtonAnswer1);
        answer2 = (RadioButton)findViewById(R.id.radioButtonAnswer2);
        answer3 = (RadioButton)findViewById(R.id.radioButtonAnswer3);
        answer4 = (RadioButton)findViewById(R.id.radioButtonAnswer4);


    }

    class ProgressTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected void onPostExecute(Integer result){
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {


                    if (checkedId == answer1.getId()) {
                        Toast.makeText(getApplicationContext(), answer1.getText(), Toast.LENGTH_SHORT).show();
                        answer1.setChecked(false);
                    } else if (checkedId == answer2.getId()) {
                        Toast.makeText(getApplicationContext(), answer2.getText(), Toast.LENGTH_SHORT).show();
                    } else if (checkedId == answer3.getId()) {
                        Toast.makeText(getApplicationContext(), answer3.getText(), Toast.LENGTH_SHORT).show();
                    } else if (checkedId == answer4.getId()) {
                        Toast.makeText(getApplicationContext(), answer4.getText(), Toast.LENGTH_SHORT).show();
                    }

                    quiz.setAnsweredQuestionNumber(quiz.getAnsweredQuestionNumber()+1);
                    progressBar.setProgress(quiz.getAnsweredQuestionNumber());


                }
            });

        }

        @Override
        protected void onPreExecute(){




        }



        @Override
        protected Integer doInBackground(Integer... params) {





            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            progressBar.setProgress(progress[0]);
        }
    }
}
