package com.keelim.practice8.api;

import androidx.annotation.NonNull;

import com.keelim.practice8.model.SeoulCulture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SeoulCultureApiService {
    //@GET의 내용은 공공데이터 페이지를 조금만 살펴보면 알 수 있다.
    @NonNull
    @GET("{apikey}/json/ListPublicReservationCulture/1/100/")
    Call<SeoulCulture> get_seoul_cultureList(
            @Path("apikey") String apikey
    );


}
