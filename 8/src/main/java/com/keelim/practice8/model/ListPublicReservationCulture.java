package com.keelim.practice8.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPublicReservationCulture {

    @SerializedName("list_total_count")
    @Expose
    private Integer listTotalCount;
    @SerializedName("RESULT")
    @Expose
    private RESULT rESULT;

    @Nullable
    @SerializedName("row")
    @Expose
    private List<SeoulCulturedetail> row = null;

    public Integer getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(Integer listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public RESULT getRESULT() {
        return rESULT;
    }

    public void setRESULT(RESULT rESULT) {
        this.rESULT = rESULT;
    }

    @Nullable
    public List<SeoulCulturedetail> getRow() {
        return row;
    }

    public void setRow(@Nullable List<SeoulCulturedetail> row) {
        this.row = row;
    }

}