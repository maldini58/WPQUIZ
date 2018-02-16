package com.example.maldini.quizapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    private Quiz quiz;


    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    TextView textViewTitle;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;
    QuizDbAdapter quizDbAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        progressBar = (ProgressBar) view.findViewById(R.id.solutionBar);
        textViewTitle.setText(quiz.getTitle());
        quizDbAdapter = new QuizDbAdapter(getActivity().getApplicationContext());

        new ProgressTask().execute();


        radioGroup = (RadioGroup) view.findViewById(R.id.myRadioGroup);
        answer1 = (RadioButton) view.findViewById(R.id.radioButtonAnswer1);
        answer2 = (RadioButton) view.findViewById(R.id.radioButtonAnswer2);
        answer3 = (RadioButton) view.findViewById(R.id.radioButtonAnswer3);
        answer4 = (RadioButton) view.findViewById(R.id.radioButtonAnswer4);

        return view;


    }


    class ProgressTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPostExecute(Integer result) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {


                    if (checkedId == answer1.getId()) {
                        answer1.setChecked(false);
                        quiz.setCorrectQuestionNumber(quiz.getCorrectQuestionNumber() + 1);
                    } else if (checkedId == answer2.getId()) {
                        quiz.setWrongQuestionNumber(quiz.getWrongQuestionNumber() + 1);
                        answer2.setChecked(false);
                    } else if (checkedId == answer3.getId()) {
                        quiz.setWrongQuestionNumber(quiz.getWrongQuestionNumber() + 1);
                        answer3.setChecked(false);
                    } else if (checkedId == answer4.getId()) {
                        quiz.setWrongQuestionNumber(quiz.getWrongQuestionNumber() + 1);
                        answer4.setChecked(false);
                    }

                    quiz.setAnsweredQuestionNumber(quiz.getAnsweredQuestionNumber() + 1);
                    try {
                        quizDbAdapter.open();
                        getQuiz().setFinished(1);
                        quizDbAdapter.updateQuiz(getQuiz().getId(), getQuiz());
                        quizDbAdapter.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    progressBar.setProgress(quiz.getAnsweredQuestionNumber());

                    if (quiz.getAnsweredQuestionNumber() == quiz.getQuestionNumber()) {

                        quiz.setResult(100 * quiz.getCorrectQuestionNumber() / quiz.getQuestionNumber());
                        try {
                            quizDbAdapter.open();
                            Quiz testQuiz = getQuiz();
                            quizDbAdapter.updateQuiz(getQuiz().getId(), getQuiz());

                            quizDbAdapter.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        getView().setVisibility(View.GONE);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        SecondFragment f2 = new SecondFragment();
                        f2.setQuiz(quiz);
                        fragmentTransaction.add(R.id.fragment_container, f2);
                        fragmentTransaction.commit();

                    }


                }
            });

        }

        @Override
        protected void onPreExecute() {


        }


        @Override
        protected Integer doInBackground(Integer... params) {


            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0]);
        }


    }


}
