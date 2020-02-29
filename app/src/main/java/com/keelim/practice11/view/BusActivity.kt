package com.keelim.practice11.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import kotlinx.android.synthetic.main.activity_bus.*

class BusActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btn_bus1.setOnClickListener { startActivity(Intent(this, LostBusActivity::class.java)) }

        btn_bus2.setOnClickListener { startActivity(Intent(this, LostBus2Activity::class.java)) }
    }
}