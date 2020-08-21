package com.keelim.mvvmpractice.di

import com.keelim.mvvmpractice.MainSearchRecyclerViewAdapter
import com.keelim.mvvmpractice.model.DataModel
import com.keelim.mvvmpractice.model.DataModelImpl
import com.keelim.mvvmpractice.model.service.KakaoSearchService
import com.keelim.mvvmpractice.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


var retrofit = module{
    single<KakaoSearchService>{
        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoSearchService::class.java)
    }
}

var adapterPart = module{
    factory {
        MainSearchRecyclerViewAdapter()
    }
}

var modelPart = module{
    factory<DataModel>{
        DataModelImpl(get())

    }
}

var viewModelPart = module{
    viewModel{
        MainViewModel(get())
    }
}

var myDiModule = listOf(adapterPart, modelPart, viewModelPart )