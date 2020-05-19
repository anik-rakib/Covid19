package com.example.anikrakib.covid19;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class BangladeshDistrictAdapter extends RecyclerView.Adapter<BangladeshDistrictAdapter.ViewHolder> {

    private List<BangladeshDistrict> bangladeshDistricts;
    private List<BangladeshDistrict> bangladeshDistrictsfull;
    private Context context;
//    public BangladeshDistrictAdapter(List<BangladeshDistrict> districtBangladesh, Context context) {
//        this.bangladeshDistricts = districtBangladesh;
//        this.context = context;
//        bangladeshDistrictsfull = new ArrayList<>(districtBangladesh);
//    }
public BangladeshDistrictAdapter(List<BangladeshDistrict> districtBangladesh) {
    this.bangladeshDistricts = districtBangladesh;
    this.context = context;
    bangladeshDistrictsfull = new ArrayList<>(districtBangladesh);
}



    @NonNull
    @Override
    public BangladeshDistrictAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_district, parent, false);

        return new BangladeshDistrictAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BangladeshDistrictAdapter.ViewHolder holder, int position) {
        BangladeshDistrict bangladeshDistrict = bangladeshDistricts.get(position);
        holder.district.setText(bangladeshDistrict.getmDistrict());
        holder.totalCases.setText(bangladeshDistrict.getMtotalCases());
        holder.yesterdayCases.setText(bangladeshDistrict.getmYesterday());
        int a = Integer.parseInt(bangladeshDistrict.mtotalCases);
        int b = Integer.parseInt(bangladeshDistrict.mYesterday);
        holder.todayCases.setText(""+(a-b));
    }

    @Override
    public int getItemCount() {
        return bangladeshDistricts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView district, totalCases,yesterdayCases,todayCases;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            district = itemView.findViewById(R.id.districtTv);
            totalCases = itemView.findViewById(R.id.totalPositive);
            yesterdayCases = itemView.findViewById(R.id.prePositive);
            todayCases = itemView.findViewById(R.id.todayPositive);
        }
    }
    public Filter getFilter() {
        return covidCountriesFilter;
    }

    private Filter covidCountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BangladeshDistrict> filteredCovidCountry = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredCovidCountry.addAll(bangladeshDistrictsfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BangladeshDistrict itemCovidCountry : bangladeshDistrictsfull) {
                    if (itemCovidCountry.getmDistrict().toLowerCase().contains(filterPattern)) {
                        filteredCovidCountry.add(itemCovidCountry);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bangladeshDistricts.clear();
            bangladeshDistricts.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}