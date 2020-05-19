package com.example.anikrakib.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BangladeshDistrictWiseUpdate extends AppCompatActivity {
    RecyclerView districtRecyclerView;
    BangladeshDistrictAdapter bangladeshDistrictAdapter;
    String lastUpdateTime;

    private static final String TAG = BangladeshDistrict.class.getSimpleName();
    List<BangladeshDistrict> bangladeshDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangladesh_district_wise_update);

        districtRecyclerView = findViewById(R.id.districtList);


        districtRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        bangladeshDistrict = new ArrayList<>();
        getDistrictFromServer();

    }

    private void getDistrictFromServer() {
        String url = "https://corona-bd.herokuapp.com/district";
        bangladeshDistrict = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            bangladeshDistrict.add(new BangladeshDistrict(
                                    data.getString("name"), data.getString("count"),
                                    data.getString("prev_count")
                            ));

                        }
                        lastUpdateTime = jsonObject.getString("updated_on");
                        onTitleChanged(lastUpdateTime,Color.BLACK);


                        showRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "onResponse: " + error);
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void showRecyclerView() {
        bangladeshDistrictAdapter = new BangladeshDistrictAdapter(bangladeshDistrict);
        districtRecyclerView.setAdapter(bangladeshDistrictAdapter);

//        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                showSelectedCovidCountry(covidCountries.get(position));
//            }
//        });
    }

}
