package com.example.covid19app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.neovisionaries.i18n.CountryCode;

import org.json.JSONArray;
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

public class Covid_Data_Activity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_data);
        Intent intent = getIntent();

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Integer> country = es.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                execute_country();
                return 1;
            }
        });


        try {
                int x = country.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    public JSONObject get_json_from_country(){
        Intent intent = getIntent();
        String country_code = CountryCode.findByName(intent.getStringExtra("country")).get(0).name();
        URL url = null;
        try {
            String inline = "";
            url = new URL("https://disease.sh/v3/covid-19/countries/"+country_code);
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
    public void execute_country(){
        JSONObject object = get_json_from_country();

        edit_country(object);


    }
    public void edit_country(JSONObject object){


        String country_name = null;
        ImageView imageView = null;
        String imageURL= null;


        try {
            JSONObject jsonObject = object.getJSONObject("countryInfo");
            imageURL = (String) jsonObject.getString("flag");
            InputStream in=new java.net.URL(imageURL).openStream();
            Bitmap bimage= BitmapFactory.decodeStream(in);
            System.out.println(bimage);
            imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bimage);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



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
            country_name = (String) object.get("country");
            String continent = (String) object.get("continent");


            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String population = formatter.format((int) object.get("population"));
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
