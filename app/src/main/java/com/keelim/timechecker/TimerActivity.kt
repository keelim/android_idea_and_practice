package com.keelim.timechecker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import com.keelim.timechecker.interfaces.IMyCounterService
import com.keelim.timechecker.services.MyCounterService
import kotlinx.android.synthetic.main.activity_temp.*

class TimerActivity : AppCompatActivity() {
    private var running = true
    private var binder: IMyCounterService? = null
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            binder = IMyCounterService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        btnPlay.setOnClickListener { v ->
            val intent = Intent(this, MyCounterService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            running = true
            Thread(GetCounterThread()).start()
        }

        btnStop.setOnClickListener { v ->
            unbindService(connection)
            running = false
        }


    }

    private inner class GetCounterThread : Runnable {
        var handler = Handler()
        override fun run() {
            while (running) {
                if (binder == null) continue
                handler.post {
                    try {
                        tvCounter.text = binder!!.count.toString() + " "
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}