package com.example.maldini.quizapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    ListView lsItem;
    List<Quiz> quizesList;
    List<Integer> listResults = new ArrayList<>();
    public List<Quiz> quizesResultList = new ArrayList<>();
    Context context;
    QuizDbAdapter quizDbAdapter;
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        quizDbAdapter = new QuizDbAdapter(context);
        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start



        lsItem = (ListView)findViewById(R.id.lvItems);

//        textView = (TextView)findViewById(R.id.textView);






    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!listResults.isEmpty()) {
            listResults.clear();

        }

        if(!quizesResultList.isEmpty()) {
            quizesResultList.clear();

        }

//        try {
////            quizDbAdapter = new QuizDbAdapter(context);
//            quizDbAdapter.open();
////            Quiz testQuiz = new Quiz("LOL",5,0,99,9999);
////            quizDbAdapter.updateQuiz(0, testQuiz);
//
//            // TU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//            Cursor cursor = quizDbAdapter.getQuizes();
//            if(cursor.moveToFirst()){
//                do{
//                    Quiz quiz = quizDbAdapter.getQuizFromCursor(cursor);
//                    listResults.add((int) quiz.getResult());
//                }while(cursor.moveToNext());
//            }
//            cursor.close();
//            quizDbAdapter.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }



        new JSONTask().execute("http://quiz.o2.pl/api/v1/quizzes/0/100");

    }






    public class JSONTask extends AsyncTask<String,String,List<ItemModel>>{

        @Override
        protected List<ItemModel> doInBackground(String... params) {
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
                String result ="";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArray = parentObject.getJSONArray("items");
                int count = parentObject.getInt("count");

                List<ItemModel> itemModelList = new ArrayList<>();
                quizesList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i =0;i<count;i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    JSONObject childObject = new JSONObject(finalObject.getString("mainPhoto"));
                    ItemModel itemModel = gson.fromJson(finalObject.toString(),ItemModel.class);
//                    ItemModel itemModel = new ItemModel(finalObject.getString("title"),childObject.getString("url"));
//                    ItemModel itemModel = new ItemModel(finalObject.getString("title"));


                    String title = finalObject.getString("title");
                    String imageUrl = childObject.getString("url");

                    result+= itemModel.getTitle() +"\n";
                    itemModelList.add(itemModel);
                    quizesList.add(new Quiz(title,5,5,0));
                }





                return itemModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final List<ItemModel> result){
            super.onPostExecute(result);




            /////////////////////////////////////////////////
            ;
            try {
                quizDbAdapter.open();
                for (Quiz quiz : quizesList) {

                    quizDbAdapter.insertQuiz(quiz);


                }



                Cursor cursor = quizDbAdapter.getQuizes();
                if (cursor.moveToFirst()) {
                    do {
                        Quiz quiz = quizDbAdapter.getQuizFromCursor(cursor);
                        listResults.add((int) quiz.getResult());
                        quizesResultList.add(quiz);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                quizDbAdapter.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }


            itemAdapter = new ItemAdapter (getApplicationContext(),R.layout.item_layout,result);
//                itemAdapter = new ItemAdapter(getApplicationContext(), R.layout.item_layout, result);





                lsItem.setAdapter(itemAdapter);
                lsItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(getApplicationContext(),result.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                        Intent startIntent = new Intent(getApplicationContext(), SolutionActivity.class);
                        startIntent.putExtra("com.talkingandroid.MESSAGE", result.get(position).getTitle());
                        startIntent.putExtra("com.talkingandroid.MESSAGE_ID",quizesList.get(position).getId());
                        startActivity(startIntent);
                    }
                });





            /////////////////////////////////////////////////




        }
    }

    public class ItemAdapter extends ArrayAdapter{

        private List<ItemModel> itemModelList;
        private int resource;
        private LayoutInflater inflater;


        public ItemAdapter(Context context, int resource, List<ItemModel> objects) {
            super(context, resource, objects);
            itemModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(resource,null);
                holder.textViewTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
                holder.textViewResult = (TextView)convertView.findViewById(R.id.textViewResult);
                holder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }


            final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(itemModelList.get(position).getMainPhoto().getUrl(), holder.imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });


            holder.textViewTitle.setText(itemModelList.get(position).getTitle());


            holder.textViewResult.setText("Wynik: " + quizesResultList.get(position).getResult());

            return convertView;

        }

         class ViewHolder{
            private TextView textViewTitle;
            private TextView textViewResult;
            private ImageView imageView;
        }


    }




//    private List<Double> createListResults(QuizDbAdapter quizDbAdapter){
//        List<Double> listResults = new ArrayList<>();
//        Cursor quizCursor = quizDbAdapter.getQuizes();
//        if(quizCursor.moveToFirst()){
//          do {
//              Quiz quiz = quizDbAdapter.getQuizFromCursor(quizCursor);
//              listResults.add((double) quiz.getId());
//          }while(quizCursor.moveToNext());
//        }
//
//        quizCursor.close();
//        return listResults;
//    }


    public ContentValues createNewContentValues(Quiz quiz){
        ContentValues newValues = new ContentValues();
        newValues.put(QuizDbAdapter.TITLE,quiz.getTitle());
        newValues.put(QuizDbAdapter.FINISHED,quiz.getFinished());
        newValues.put(QuizDbAdapter.RESULT, quiz.getResult());
        newValues.put(QuizDbAdapter.QUESTION_NUMBER,quiz.getQuestionNumber());
        newValues.put(QuizDbAdapter.WRONG_QUESTION_NUMBER,quiz.getWrongQuestionNumber());
        newValues.put(QuizDbAdapter.CORRECT_QUESTION_NUMBER, quiz.getCorrectQuestionNumber());
        newValues.put(QuizDbAdapter.ANSWERED_QUESTION_NUMBER, quiz.getAnsweredQuestionNumber());

        return newValues;
    }

}

