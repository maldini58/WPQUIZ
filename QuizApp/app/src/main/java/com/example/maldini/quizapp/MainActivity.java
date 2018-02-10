package com.example.maldini.quizapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    ListView lsItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start



        lsItem = (ListView)findViewById(R.id.lvItems);
//        textView = (TextView)findViewById(R.id.textView);
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

                for(int i =0;i<count;i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    JSONObject childObject = new JSONObject(finalObject.getString("mainPhoto"));
                    ItemModel itemModel = new ItemModel(finalObject.getString("title"),childObject.getString("url"));
//                    ItemModel itemModel = new ItemModel(finalObject.getString("title"));


                    String title = finalObject.getString("title");
                    String imageUrl = childObject.getString("url");

                    result+= itemModel.getTitle() +"\n";
                    itemModelList.add(itemModel);
                }
//                ArrayList<QuizField> quizFields = QuizField.makeQuizFields(finalJSON);

//                return quizFields.get(0).toString();
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
        protected void onPostExecute(List<ItemModel> result){
            super.onPostExecute(result);
//            textView.setText(result);
            ItemAdapter itemAdapter = new ItemAdapter (getApplicationContext(),R.layout.item_layout,result);
            lsItem.setAdapter(itemAdapter);

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

            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_layout,null);
            }
            TextView textViewTitle;
            TextView textViewResult;
            ImageView imageView;

            textViewTitle = (TextView)convertView.findViewById(R.id.textViewTitle);
            textViewResult = (TextView)convertView.findViewById(R.id.textViewResult);
            imageView = (ImageView)convertView.findViewById(R.id.imageView);

            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(itemModelList.get(position).getMainPhoto().getUrl(), imageView); // Default options will be used

            textViewTitle.setText(itemModelList.get(position).getTitle());
            textViewResult.setText(itemModelList.get(position).getMainPhoto().getUrl());


            return convertView;

        }


    }

  
}

