package com.example.maldini.quizapp;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        textViewResult = (TextView)view.findViewById(R.id.textViewPercentResult);
        textViewResult.setText(quiz.getResult()+"%");
        buttonExit = (Button)view.findViewById(R.id.buttonExit);
        buttonReplay = (Button)view.findViewById(R.id.buttonReplay);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity().getApplicationContext(),SolutionActivity.class);
                getView().setVisibility(View.GONE);
                startIntent.putExtra("com.talkingandroid.MESSAGE", quiz.getTitle());
                startIntent.putExtra("com.talkingandroid.RESULT",quiz.getResult());
                startActivity(startIntent);
            }
        });



        return view;
    }

}
