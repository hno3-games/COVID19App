package com.example.covid19app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Covid_Data_Activity_v2 extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_data_v2);
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Integer> all = es.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                execute_world();
                return 1;
            }
        });

        try {
            int x = all.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        es.shutdown();
    }
    public JSONObject get_json_from_world(){
        Intent intent = getIntent();

        URL url = null;
        try {
            String inline = "";
            url = new URL("https://disease.sh/v3/covid-19/all");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            System.out.println("\nJSON data in string format");
            System.out.println(inline);
            sc.close();
            JSONObject jObject = new JSONObject(inline);
            return jObject;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void execute_world(){
        JSONObject jsonObject = get_json_from_world();
        edit_world(jsonObject);
    }
    public void edit_world(JSONObject object){
        String country_name = "All World";
        String continent = "All World";

        try {
            textView = findViewById(R.id.textView9);
            textView1=findViewById(R.id.textView11);
            textView2 = findViewById(R.id.textView12);
            textView3=findViewById(R.id.textView13);
            textView4 = findViewById(R.id.textView14);
            textView5=findViewById(R.id.textView15);
            textView6 = findViewById(R.id.textView16);
            textView7=findViewById(R.id.textView17);
            textView8 = findViewById(R.id.textView18);
            textView9=findViewById(R.id.textView19);
            textView10 = findViewById(R.id.textView20);




            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String population = formatter.format((long) object.get("population"));
            String total_cases = formatter.format((int) object.get("cases"));
            String today_cases = formatter.format((int) object.get("todayCases"));
            String total_deaths = formatter.format((int) object.get("deaths"));
            String today_deaths = formatter.format((int) object.get("todayDeaths"));
            String total_recovered = formatter.format((int) object.get("recovered"));
            String today_recovered = formatter.format((int) object.get("todayRecovered"));
            String active = formatter.format((int) object.get("active"));
            String critical = formatter.format((int) object.get("critical"));
            textView.setText(country_name);
            System.out.println(country_name);
            textView1.setText(continent);
            textView2.setText("Population: " + population);
            textView3.setText("Cases: " + total_cases);
            textView4.setText("Today Cases: " + today_cases);
            textView5.setText("Deaths: " + total_deaths);
            textView6.setText("Today Deaths: " + today_deaths);
            textView7.setText("Recovered: " + total_recovered);
            textView8.setText("Today Recovered: " + today_recovered);
            textView9.setText("Active: "+active);
            textView10.setText("Critical: "+critical);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}