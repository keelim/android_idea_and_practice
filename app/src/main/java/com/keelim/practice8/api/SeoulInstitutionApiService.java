package com.keelim.practice8.api;

import androidx.annotation.NonNull;

import com.keelim.practice8.model.SeoulInstitution;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SeoulInstitutionApiService {
    @NonNull
    @GET("{apikey}/json/ListPublicReservationInstitution/{first}/{last}/{inst}")
    Call<SeoulInstitution> get_seoul_institutionList(
            @Path("apikey") String apikey,
            @Path("first") String first,
            @Path("last") String last,
            @Path("inst") String inst
    );


}