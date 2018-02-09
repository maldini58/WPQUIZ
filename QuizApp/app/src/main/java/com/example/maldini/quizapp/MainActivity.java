package com.example.maldini.quizapp;

import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    ProgressBar mProgressBar;
//    private ArrayList<QuizField> mFields = new ArrayList<QuizField>();
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        new JSONTask().execute("http://quiz.o2.pl/api/v1/quizzes/0/100");


        }



    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }




        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            textView.setText(result);

        }
    }
}
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
//        if (isOnline()){
//            LoadPhotos task = new LoadPhotos();
//            task.execute();
//        }else{
////            mProgressBar.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this.getApplicationContext(), "Aby pobierać zdjęcia, musisz się połączyć z siecią!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    public ArrayList<QuizField> getFields() {
//        return mFields;
//    }
//
//    public boolean isOnline() {
//        return true;
//    }
//
//
//
//    public void showList(){
//        QuizFieldFragment photoListFragment = new QuizFieldFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.layout_container, photoListFragment);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.commit();
//    }
//
//
//
//    private class LoadPhotos extends AsyncTask<String , String , Long > {
//
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected void onPostExecute(Long result) {
//            if (result == 0) {
//                showList();
//            } else {
//                Toast.makeText(MainActivity.this.getApplicationContext(), "Coś poszło źle!", Toast.LENGTH_SHORT).show();
//            }
////            mProgressBar.setVisibility(View.GONE);
//        }
//            @Override
//            protected Long doInBackground (String...params){
//                HttpURLConnection connection = null;
//                try {
//                    URL dataUrl = new URL("http://quiz.o2.pl/api/v1/quizzes/0/100");
//                    connection = (HttpURLConnection) dataUrl.openConnection();
//                    connection.connect();
//                    int status = connection.getResponseCode();
////            Log.d("PHOTOS", status + " " + connection.getResponseMessage());
//
//                    if (status == 200) {
//                        InputStream is = connection.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                        String responseString;
//                        StringBuilder sb = new StringBuilder();
//                        while ((responseString = reader.readLine()) != null) {
//                            sb = sb.append(responseString);
//                        }
//                        String photoData = sb.toString();
//                        Log.d("PHOTOS", photoData);
//                        mFields = QuizField.makeQuizFields(photoData);
//                        return (0l);
//                    } else {
//                        return (1l);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    return (1l);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return (1l);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                    return (1l);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    return (1l);
//                } finally {
//                    connection.disconnect();
//                }
//            }
//        }
//    }
