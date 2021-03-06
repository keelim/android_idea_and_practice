package com.keelim.practice1

import android.app.Application
import com.keelim.practice1.error.ExceptionHandler

class MyApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        setCrashHandler()
    }
    private fun setCrashHandler() {

        val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { _, _ ->
            // Crashlytics에서 기본 handler를 호출하기 때문에 이중으로 호출되는것을 막기위해 빈 handler로 설정
        }

        val fabricExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(
                ExceptionHandler(
                        this,
                        defaultExceptionHandler,
                        fabricExceptionHandler
                )
        )
    }

}