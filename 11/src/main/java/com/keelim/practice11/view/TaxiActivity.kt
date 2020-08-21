package com.keelim.practice11.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import com.keelim.practice11.view.TaxiActivity

class TaxiActivity : AppCompatActivity() {
    var btn_taxi1: ImageButton? = null
    var btn_taxi2: ImageButton? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        btn_taxi1 = findViewById(R.id.btn_taxi1)
        btn_taxi1.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@TaxiActivity, LostTaxiActivity::class.java)) })
        btn_taxi2 = findViewById(R.id.btn_taxi2)
        btn_taxi2.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@TaxiActivity, LostTaxi2Activity::class.java)) })
    }
}