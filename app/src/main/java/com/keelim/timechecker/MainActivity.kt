package com.keelim.timechecker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var timeThread: Thread
    private var isRunning = true
    private lateinit var handler: TimerHandler
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        btn_start.setOnClickListener { v: View ->
            v.visibility = View.GONE
            btn_stop.visibility = View.VISIBLE
            btn_record.visibility = View.VISIBLE
            btn_pause.visibility = View.VISIBLE
            handler = TimerHandler()
            timeThread = Thread(TimerThread())
            timeThread.start()
        }

        btn_stop.setOnClickListener { v ->
            v.visibility = View.GONE
            btn_record.visibility = View.GONE
            btn_start.visibility = View.VISIBLE
            btn_pause.visibility = View.GONE
            recordView.text = ""
            timeThread.interrupt()// thread control
        }

        btn_record.setOnClickListener {
            recordView.text =
                "${recordView.text}${timeView.text}\n"
        }

        btn_pause.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                btn_pause!!.text = "일시정지"
            } else {
                btn_pause!!.text = "시작"
            }
        }
    }


    private inner class TimerThread : Runnable {
        override fun run() {
            var i = 0
            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    val msg = Message()
                    msg.arg1 = i++
                    handler.sendMessage(msg)
                    try {
                        Thread.sleep(10)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        runOnUiThread {
                            timeView!!.text = ""
                            timeView!!.text = "00:00:00:00"
                        }
                        return  // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    inner class TimerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val mSec = msg.arg1 % 100
            val sec = msg.arg1 / 100 % 60
            val min = msg.arg1 / 100 / 60
            val hour = msg.arg1 / 100 / 360
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            val result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec)
            if (result == "00:01:15:00") {
                Toast.makeText(applicationContext, "1분 15초가 지났습니다.", Toast.LENGTH_SHORT).show()
            }
            timeView!!.text = result
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_admob -> {
                val intent = Intent(this, AdMobActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


}


