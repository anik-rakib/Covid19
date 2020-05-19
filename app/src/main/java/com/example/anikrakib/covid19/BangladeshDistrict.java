package com.example.anikrakib.covid19;

import android.os.Parcel;

public class BangladeshDistrict {
    String mDistrict,mtotalCases,mYesterday;

    public BangladeshDistrict(String mDistrict, String mtotalCases, String mYesterday) {
        this.mDistrict = mDistrict;
        this.mtotalCases = mtotalCases;
        this.mYesterday = mYesterday;
    }

    public String getmDistrict() {
        return mDistrict;
    }

    public String getMtotalCases() {
        return mtotalCases;
    }

    public String getmYesterday() {
        return mYesterday;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDistrict);
        dest.writeString(this.mtotalCases);
        dest.writeString(this.mYesterday);
    }

    protected BangladeshDistrict(Parcel in) {
        this.mDistrict = in.readString();
        this.mtotalCases = in.readString();
        this.mYesterday = in.readString();
    }

//    public static final Parcelable.Creator<BangladeshDistrict> CREATOR = new Parcelable.Creator<BangladeshDistrict>() {
//        @Override
//        public BangladeshDistrict createFromParcel(Parcel source) {
//            return new BangladeshDistrict(source);
//        }
//
////        @Override
////        public BangladeshDistrict[] newArray(int size) {
////            return new BangladeshDistrict[][size];
////        }
//    };

}

