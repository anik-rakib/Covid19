package com.example.anikrakib.covid19;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BangladeshDashboardFragment extends Fragment {
    private TextView bdLastUpdated,positivecases,activecases,totalrecover,totaldeath,viewDistrict;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bangladesh_dashboard, container, false);

        bdLastUpdated = root.findViewById(R.id.lastUpdatedTv);
        positivecases = root.findViewById(R.id.confirmedTv);
        activecases = root.findViewById(R.id.activeTv);
        totalrecover = root.findViewById(R.id.recoveredTv);
        totaldeath = root.findViewById(R.id.deceasedTv);
        viewDistrict = root.findViewById(R.id.viewAll);


        getActivity().setTitle("Bd Details");

        // call Volley
        getData();

        viewDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BangladeshDistrictWiseUpdate.class);
                startActivity(intent);
            }
        });


        return root;
    }




    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //String url = "https://corona.lmao.ninja/all";
        String url1 = "https://disease.sh/v2/countries/bangladesh";
        String url = "https://corona-bd.herokuapp.com/stats";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
//                    JSONObject jsonObject = new JSONObject(response.toString());
//                    JSONObject cases = jsonObject.getJSONObject("positive");
//                    JSONObject death = jsonObject.getJSONObject("death");
//                    JSONObject recover = jsonObject.getJSONObject("recovered");
//                    JSONObject active = jsonObject.getJSONObject("positive");
//                    bdLastUpdated.setText(jsonObject.getString("updated_on"));
//                    positivecases.setText(cases.getString("total"));
//                    activecases.setText(active.getString("total"));
//                    totalrecover.setText(recover.getString("total"));
//                    totaldeath.setText(death.getString("total"));

                    JSONObject jsonObject = new JSONObject(response.toString());
                    //bdLastUpdated.setText(jsonObject.getString("updated"));
                    positivecases.setText(jsonObject.getString("cases"));
                    activecases.setText(jsonObject.getString("active"));
                    totalrecover.setText(jsonObject.getString("recovered"));
                    totaldeath.setText(jsonObject.getString("deaths"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error Response", error.toString());
            }
        });
        queue.add(stringRequest);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    bdLastUpdated.setText("Last Updated\n"+jsonObject.getString("updated_on"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error Response", error.toString());
            }
        });

        queue.add(stringRequest1);
    }

}