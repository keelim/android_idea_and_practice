package com.keelim.practice8.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.keelim.practice8.model.ListPublicReservationEducation;

public class SeoulEducation {

    @SerializedName("ListPublicReservationEducation")
    @Expose
    private ListPublicReservationEducation listPublicReservationEducation;

    public ListPublicReservationEducation getListPublicReservationEducation() {
        return listPublicReservationEducation;
    }

    public void setListPublicReservationEducation(ListPublicReservationEducation listPublicReservationEducation) {
        this.listPublicReservationEducation = listPublicReservationEducation;
    }
}
