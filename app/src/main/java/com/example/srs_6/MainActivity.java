package com.example.srs_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView city, temp, condition;
    ImageView icon;
    ArrayList<Weather> wea = new ArrayList<Weather>();
    WeatherAdapter adapter;
    RecyclerView recyclerView;
    String gorod;
    private RequestQueue mqueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        city =(TextView)findViewById(R.id.city);
        temp = (TextView)findViewById(R.id.temp);
        condition = (TextView)findViewById(R.id.condition);
        icon = (ImageView)findViewById(R.id.icon);
        GetArray();
    }
    public  void GetArray(){
        mqueue = (RequestQueue) Volley.newRequestQueue(this);
        String URL = "http://api.weatherapi.com/v1/forecast.json?key=98de5378be844d7082a65204232810q=London&days=1&aqi=no&alerts=no";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.d("mylog222222", "success" );
                    JSONObject location = response.getJSONObject("location");
                    String name = location.getString("name");
                    JSONObject current = response.getJSONObject("current");
                    JSONObject cond = current.getJSONObject("condition");
                    String text = cond.getString("text");
                    String url = "https:" + cond.getString("icon");
                    Picasso.get().load(url).fit().centerInside().into(icon);
                    condition.setText(text);
                    String temp_c = current.getString("temp_c");
                    temp.setText(temp_c);
                    city.setText(name);
                    JSONObject Forecast = response.getJSONObject("forecast");
                    JSONArray Forecastday = Forecast.getJSONArray("forecastday");

                    for(int i = 0; i< Forecastday.length(); i++)
                    {
                        JSONObject ForecastDayElement = Forecastday.getJSONObject(i);
                        String date = ForecastDayElement.getString("date");
                        JSONObject day = ForecastDayElement.getJSONObject("day");
                        JSONObject condition = day.getJSONObject("condition");
                        url = "https:" + condition.getString("icon");
                        String maxtemp_c = day.getString("maxtemp_c");
                        wea.add(new Weather(date,maxtemp_c,url));
                    }
                    adapter = new WeatherAdapter(MainActivity.this, wea);
                    // устанавливаем для списка адаптер
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.d("mylog222222", "error" );
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog222222", "onerrorresponse");
            }
        });
        mqueue.add(request);
    }
}