package com.example.maldini.quizapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class SecondFragment extends Fragment {

    private Quiz quiz;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    TextView textViewResult;
    Button buttonExit;
    Button buttonReplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        textViewResult = (TextView) view.findViewById(R.id.textViewPercentResult);
        textViewResult.setText(quiz.getResult() + "%");
        buttonExit = (Button) view.findViewById(R.id.buttonExit);
        buttonReplay = (Button) view.findViewById(R.id.buttonReplay);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView().setVisibility(View.GONE);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                quiz = new Quiz(quiz.getTitle(), 5, 0, 0, quiz.getId());
                FirstFragment f1 = new FirstFragment();
                f1.setQuiz(quiz);
                fragmentTransaction.add(R.id.fragment_container, f1);
                fragmentTransaction.commit();
            }
        });


        return view;
    }


}
