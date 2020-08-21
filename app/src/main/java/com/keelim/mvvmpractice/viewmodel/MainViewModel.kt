package com.keelim.mvvmpractice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.keelim.mvvmpractice.base.BaseKotlinViewModel
import com.keelim.mvvmpractice.model.DataModel
import com.keelim.mvvmpractice.model.enum.KakaoSearchSortEnum
import com.keelim.mvvmpractice.model.response.ImageSearchResponse
import com.keelim.mvvmpractice.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.properties.ReadOnlyProperty

class MainViewModel(private val model: DataModel) : BaseKotlinViewModel() {
    private val TAG = "MainViewModel"

    private val _imageSearchResponseLiveData = MutableLiveData<ImageSearchResponse>()
    val imageSearchResponseLiveData: LiveData<ImageSearchResponse>
        get() = _imageSearchResponseLiveData

    fun getImageSearch(query: String, page: Int, size: Int) {
        addDisposable(model.getData(query, KakaoSearchSortEnum.Accuracy, page, size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    if (documents.size > 0) {
                        Log.d(TAG, "documents : $documents")
                        _imageSearchResponseLiveData.postValue(this)
                    }
                    Log.d(TAG, "meta : $meta")
                }
            }, {
                Log.d(TAG, "response error, message : ${it.message}")
            })
        )
    }



}