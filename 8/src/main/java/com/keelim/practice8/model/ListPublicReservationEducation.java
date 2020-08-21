package com.keelim.practice8.model;



import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPublicReservationEducation {




    @Nullable
    @SerializedName("row")
    @Expose
    private List<SeoulEducationdetail> row = null;

    @Nullable
    public List<SeoulEducationdetail> getRow() {
        return row;
    }

    public void setRow(@Nullable List<SeoulEducationdetail> row) {
        this.row = row;
    }

}
