package com.keelim.practice8.model;



import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPublicReservationInstitution {

    @Nullable
    @SerializedName("row")
    @Expose
    private List<SeoulInstitutiondetail> row = null;

    @Nullable
    public List<SeoulInstitutiondetail> getRow() {
        return row;
    }

    public void setRow(@Nullable List<SeoulInstitutiondetail> row) {
        this.row = row;
    }


}
