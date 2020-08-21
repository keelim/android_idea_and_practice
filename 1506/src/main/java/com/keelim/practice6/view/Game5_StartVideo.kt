package com.keelim.practice6.view

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R

class Game5_StartVideo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game5_activity_video_start)
        val vv = findViewById<View>(R.id.vv) as VideoView
        // http://www ~~~ possible
        val uriPath =
            "android.resource://" + packageName + "/" + R.raw.game3start
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
        // 마우스 클릭
        val SkipButton =
            findViewById<Button>(R.id.SkipButton_Id)
        SkipButton.setOnClickListener { finish() }
    }
}