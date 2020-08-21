package com.keelim.mvvmpractice.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.keelim.mvvmpractice.util.SnackbarMessage
import com.keelim.mvvmpractice.util.SnackbarMessageString
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseKotlinViewModel : ViewModel() {


    private val snackbarMessage = SnackbarMessage()
    private val snackbarMessageString = SnackbarMessageString()


    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    /**
     * 스낵바를 보여주고 싶으면 viewModel 에서 이 함수를 호출
     */
    fun showSnackbar(stringResourceId: Int) {
        snackbarMessage.value = stringResourceId
    }

    fun showSnackbar(str: String) {
        snackbarMessageString.value = str
    }


    fun observeSnackbarMessage(lifeCycleOwner: LifecycleOwner, ob: (Int) -> Unit) {
        snackbarMessage.observe(lifeCycleOwner, ob)
    }

    fun observeSnackbarMessageStr(lifeCycleOwner: LifecycleOwner, ob: (String) -> Unit) {
        snackbarMessageString.observe(lifeCycleOwner, ob)
    }
}
