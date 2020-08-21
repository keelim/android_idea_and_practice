package com.keelim.practice8.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class ListPublicReservationSport {



    @Nullable
    @SerializedName("row")
    @Expose
    private List<SeoulSportdetail> row = null;


    @Nullable
    public List<SeoulSportdetail> getRow() {
        return row;
    }

    public void setRow(@Nullable List<SeoulSportdetail> row) {
        this.row = row;
    }


}
