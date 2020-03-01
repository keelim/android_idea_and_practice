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
        startAnimation()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFormat(PixelFormat.RGBA_8888)
    }

    private fun startAnimation() {
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
            }
        }
        splashTread.start()
    }
}