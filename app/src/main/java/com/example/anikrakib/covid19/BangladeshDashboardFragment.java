package com.example.anikrakib.covid19;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView bdLastUpdated,positivecases,activecases,totalrecover,totaldeath;
    RecyclerView districtRecyclerView;
    BangladeshDistrictAdapter bangladeshDistrictAdapter;

    private static final String TAG = BangladeshDistrict.class.getSimpleName();
    List<BangladeshDistrict> bangladeshDistrict;


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
        districtRecyclerView = root.findViewById(R.id.districtlist);

        getActivity().setTitle("Bd Details");

        // call Volley
        getData();

        bangladeshDistrict = new ArrayList<>();
        getDistrictFromServer();

        return root;
    }
    private void showRecyclerView() {
        bangladeshDistrictAdapter = new BangladeshDistrictAdapter(bangladeshDistrict,getActivity());
        districtRecyclerView.setAdapter(bangladeshDistrictAdapter);

//        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                showSelectedCovidCountry(covidCountries.get(position));
//            }
//        });
    }


    private void getDistrictFromServer() {
        String url = "https://corona-bd.herokuapp.com/district";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            // Extract JSONObject inside JSONObject
                            JSONObject countryInfo = data.getJSONObject("data");

                            bangladeshDistrict.add(new BangladeshDistrict(
                                    countryInfo.getString("name"), countryInfo.getString("count"),
                                    countryInfo.getString("prev_count")
                            ));
                        }


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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
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