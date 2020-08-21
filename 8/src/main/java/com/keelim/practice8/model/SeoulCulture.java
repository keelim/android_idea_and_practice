package com.keelim.practice8.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.keelim.practice8.model.ListPublicReservationCulture;

public class SeoulCulture {

    @SerializedName("ListPublicReservationCulture")
    @Expose
    private ListPublicReservationCulture listPublicReservationCulture;

    public ListPublicReservationCulture getListPublicReservationCulture() {
        return listPublicReservationCulture;
    }

    public void setListPublicReservationCulture(ListPublicReservationCulture listPublicReservationCulture) {
        this.listPublicReservationCulture = listPublicReservationCulture;
    }

}