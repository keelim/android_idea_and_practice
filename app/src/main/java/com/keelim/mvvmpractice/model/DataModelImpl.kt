package com.keelim.mvvmpractice.model


import com.keelim.mvvmpractice.model.enum.KakaoSearchSortEnum
import com.keelim.mvvmpractice.model.response.ImageSearchResponse
import com.keelim.mvvmpractice.model.service.KakaoSearchService
import io.reactivex.Single

class DataModelImpl(private val service: KakaoSearchService) : DataModel {
    private val KAKAO_APP_KEY = "YOUR_APP_KEY"

    override fun getData(
        query: String,
        sort:
        KakaoSearchSortEnum,
        page: Int,
        size: Int
    ): Single<ImageSearchResponse> {
        return service.searchImage(
            auth = "Kakao $KAKAO_APP_KEY",
            query = query,
            sort = sort.toString(),
            page = page,
            size = size
        )
    }
}
