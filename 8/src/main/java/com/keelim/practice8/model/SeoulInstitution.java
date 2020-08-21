package com.keelim.practice8.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.keelim.practice8.model.ListPublicReservationInstitution;

public class SeoulInstitution {
    @SerializedName("ListPublicReservationInstitution")
    @Expose
    private ListPublicReservationInstitution listPublicReservationInstitution;

    public ListPublicReservationInstitution getListPublicReservationInstitution() {
        return listPublicReservationInstitution;
    }

    public void setListPublicReservationInstitution(ListPublicReservationInstitution listPublicReservationInstitution) {
        this.listPublicReservationInstitution = listPublicReservationInstitution;
    }
}
