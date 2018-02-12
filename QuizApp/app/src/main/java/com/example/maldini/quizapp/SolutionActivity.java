package com.example.maldini.quizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SolutionActivity extends AppCompatActivity {

    TextView textViewTitle;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        Intent intent = getIntent();
        String title = intent.getStringExtra("com.talkingandroid.MESSAGE");

        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
       progressBar = (ProgressBar)findViewById(R.id.solutionBar);
        textViewTitle.setText(title);
        new ProgressTask().execute();
    }

    class ProgressTask extends AsyncTask<Integer,Integer,Integer>{
        @Override
        protected void onPostExecute(Integer result){
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPreExecute(){
//            progressBar.setProgress(3);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            for(int i =0;i <5;i++){
                SystemClock.sleep(1000);
                publishProgress(i+1);
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            progressBar.setProgress(progress[0]);
        }
    }
}
