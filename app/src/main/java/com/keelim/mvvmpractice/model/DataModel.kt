package com.keelim.mvvmpractice.model

import com.keelim.mvvmpractice.model.enum.KakaoSearchSortEnum
import com.keelim.mvvmpractice.model.response.ImageSearchResponse
import io.reactivex.Single

interface DataModel {
    fun getData(
        query: String,
        sort: KakaoSearchSortEnum,
        page: Int,
        size: Int
    ): Single<ImageSearchResponse>

}