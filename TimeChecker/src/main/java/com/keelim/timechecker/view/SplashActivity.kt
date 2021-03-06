package com.keelim.timechecker.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.keelim.timechecker.R


class SplashActivity : AppCompatActivity() {
    //인트로 액티비티를 생성한다.
    private lateinit var handler: Handler
    private val runnable = Runnable {
        //runable 작동을 하고 시작
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent) //인텐트를 넣어준다. intro -> main
        finish() //앱을 종료한다.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) //애니메이션을 넣어준다.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, 1000) //handler 를 통하여 사용
    }

    override fun onBackPressed() { //back 키 눌렀을 때

    }
}