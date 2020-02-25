package com.keelim.practice8.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice8.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val hd = Handler(mainLooper)

        hd.postDelayed({ finish() }, 2000)
    }
}