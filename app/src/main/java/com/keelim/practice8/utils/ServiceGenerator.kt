package com.keelim.practice8.utils

import com.keelim.practice8.api.SeoulCultureApiService
import com.keelim.practice8.api.SeoulEducationApiService
import com.keelim.practice8.api.SeoulInstitutionApiService
import com.keelim.practice8.api.SeoulSportApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private const val SW_API_ROOT_URL = "http://openAPI.seoul.go.kr:8088/"

    private val listInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(SW_API_ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //서울시 문화정보
    @JvmStatic
    val seoul_Culture_ListApiService: SeoulCultureApiService
        get() = listInstance.create(
            SeoulCultureApiService::class.java
        )

    //서울시 스포츠
    @JvmStatic
    val seoul_Sport_ListApiService: SeoulSportApiService
        get() = listInstance
            .create(SeoulSportApiService::class.java)

    //서울시 교육
    @JvmStatic
    val seoul_Education_ListApiService: SeoulEducationApiService
        get() = listInstance.create(
            SeoulEducationApiService::class.java
        )

    //서울시 시설대관
    @JvmStatic
    val seoul_Institution_ListApiService: SeoulInstitutionApiService
        get() = listInstance.create(
            SeoulInstitutionApiService::class.java
        )
}