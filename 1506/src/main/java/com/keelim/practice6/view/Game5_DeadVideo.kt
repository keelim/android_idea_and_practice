package com.keelim.practice6.view

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R

class Game5_DeadVideo : AppCompatActivity() {
    // 뒤로가기 버튼 변수
    private var backPressCloseHandler: BackPressCloseHandler? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game5_activity_video_dead)
        backPressCloseHandler =
            BackPressCloseHandler(this) // 뒤로가기 버튼용 변수
        val vv = findViewById<View>(R.id.vv) as VideoView
        // http://www ~~~ possible
        val uriPath = "android.resource://" + packageName + "/" + R.raw.game1bad
        val uri = Uri.parse(uriPath)
        vv.setVideoURI(uri)
        vv.requestFocus()
        vv.start()
        // Event
        vv.setOnInfoListener { mp, what, extra ->
            when (what) {
                MediaPlayer.MEDIA_ERROR_TIMED_OUT -> Toast.makeText(
                    applicationContext,
                    "MEDIA_ERROR_TIMED_OUT",
                    Toast.LENGTH_LONG
                ).show()
                MediaPlayer.MEDIA_INFO_BUFFERING_START ->  // Progress Diaglog 출력(Print)
                    Toast.makeText(
                        applicationContext,
                        "MEDIA_INFO_BUFFERING_START",
                        Toast.LENGTH_LONG
                    ).show()
                MediaPlayer.MEDIA_INFO_BUFFERING_END ->  // Progress Dialog 삭제(Delete)
                    Toast.makeText(
                        applicationContext,
                        "MEDIA_INFO_BUFFERING_END",
                        Toast.LENGTH_LONG
                    ).show()
            }
            false
        }
    }

    // 뒤로가기 버튼 누를시 동작
    override fun onBackPressed() {
        backPressCloseHandler!!.onBackPressed()
    }

    inner class BackPressCloseHandler(private val activity: AppCompatActivity) {
        private var backKeyPressedTime: Long = 0
        private var toast: Toast? = null
        fun onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
                backKeyPressedTime = System.currentTimeMillis()
                showGuide()
                return
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 1000) {
                finish()
                activity.finishAffinity()
                Process.killProcess(Process.myPid())
                toast!!.cancel()
            }
        }

        fun showGuide() {
            toast = Toast.makeText(activity, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
            toast.show()
        }

    }
}