package com.keelim.practice10.model.retrofit;

import androidx.annotation.NonNull;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {
    @NonNull
    @Multipart
    @POST("api/photo")
    Call<ImageInfo> uploadImage(@Part("image\"; filename=\"myPicture.jpg\" ") RequestBody file,
                                @Part("storeID") RequestBody storeID,
                                @Part("clothesID") RequestBody clothesID);

}
