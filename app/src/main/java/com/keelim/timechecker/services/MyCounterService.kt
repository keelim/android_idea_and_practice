package com.keelim.timechecker.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import android.widget.Toast
import com.keelim.timechecker.interfaces.IMyCounterService

class MyCounterService : Service() {
    private var num_count = 0
    private var isStop = false
    var binder: IMyCounterService.Stub = object : IMyCounterService.Stub() {

        @Throws(RemoteException::class)
        override fun getCount(): Int {
            return num_count
        }
    }

    override fun onCreate() {
        super.onCreate()
        val counter = Thread(Counter())
        counter.start()
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        isStop = true
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        isStop = true
    }

    private inner class Counter : Runnable {
        private val handler = Handler()
        override fun run() {
            num_count = 0
            while (num_count < 50) {
                if (isStop) break

                handler.post {
                    Toast.makeText(
                        applicationContext,
                        num_count.toString() + "",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                num_count++
            }
            handler.post {
                Toast.makeText(applicationContext, "서비스 종료", Toast.LENGTH_SHORT).show()
            }
        }
    }
}