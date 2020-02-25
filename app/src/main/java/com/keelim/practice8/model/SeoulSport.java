package com.keelim.practice8.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.keelim.practice8.model.ListPublicReservationSport;

public class SeoulSport {
    @SerializedName("ListPublicReservationSport")
    @Expose
    private ListPublicReservationSport listPublicReservationSport;

    public ListPublicReservationSport getListPublicReservationSport() {
        return listPublicReservationSport;
    }

    public void setListPublicReservationSport(ListPublicReservationSport listPublicReservationSport) {
        this.listPublicReservationSport = listPublicReservationSport;
    }


}
