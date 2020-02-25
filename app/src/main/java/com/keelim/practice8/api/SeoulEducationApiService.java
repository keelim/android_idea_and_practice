package com.keelim.practice8.api;

import androidx.annotation.NonNull;

import com.keelim.practice8.model.SeoulEducation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SeoulEducationApiService {
    @NonNull
    @GET("{apikey}/json/ListPublicReservationEducation/{first}/{last}/{edu}")
    Call<SeoulEducation> get_seoul_educationList(
            @Path("apikey") String apikey,
            @Path("first") String first,
            @Path("last") String last,
            @Path("edu") String edu
    );


}