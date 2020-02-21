package com.keelim.practice6.view

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private lateinit var splashTread: Thread
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        StartAnimations()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    private fun StartAnimations() {
        var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()


        lin_lay.clearAnimation()
        lin_lay.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        anim.reset()

        splash.clearAnimation()
        splash.startAnimation(anim)
        splashTread = object : Thread() {
            override fun run() {
                var waited = 0
                // Splash screen pause time
                while (waited < 1500) {
                    sleep(100)
                    waited += 100
                }
                Intent(this@SplashActivity, FirstActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(this)
                }
                finish()
                /*
                // 아래 코드 설명 : 만약 로그인이 안 되어 있으면 'FirstActivity'로 이동되고, 로그인이 되어 있으면 '로그인Activity'로 이동된다.
                if(SavedSharedPreference.getId(Splashscreen.this).length() == 0)  //
                {
                    Intent intent = new Intent(Splashscreen.this, FirstActivity.class);  // 스플래시 실행후 넘어가는 첫번째 화면!!
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                }

                else
                {
                    Intent intent = new Intent(Splashscreen.this, a_LoginMainActivity.class);  // 스플래시 실행후 넘어가는 첫번째 화면!!
                    intent.putExtra("userID",SavedSharedPreference.getId(Splashscreen.this));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                }
*/
                finish()
            }
        }
        splashTread.start()
    }
}