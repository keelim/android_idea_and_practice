package com.keelim.practice11.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R

class SplashActivity : AppCompatActivity() {
    /**
     * 로딩 화면이 떠있는 시간(밀리초단위)
     */
    private val SPLASH_DISPLAY_LENGTH = 2000

    /**
     * 처음 액티비티가 생성될때 불려진다.
     */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/Handler().postDelayed({
            /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}