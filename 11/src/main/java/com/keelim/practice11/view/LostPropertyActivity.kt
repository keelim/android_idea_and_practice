package com.keelim.practice11.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import com.keelim.practice11.view.LostPropertyActivity

class LostPropertyActivity : AppCompatActivity() {
    var bus: Button? = null
    var subway: Button? = null
    var taxi: Button? = null
    var public_institution: Button? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lost_property)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        bus = findViewById(R.id.bus)
        bus.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@LostPropertyActivity, BusActivity::class.java)) })
        subway = findViewById(R.id.subway)
        subway.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@LostPropertyActivity, SubwayActivity::class.java)) })
        taxi = findViewById(R.id.taxi)
        taxi.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@LostPropertyActivity, TaxiActivity::class.java)) })
        public_institution = findViewById(R.id.public_institution)
        public_institution.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@LostPropertyActivity, Public_institutionActivity::class.java)) })
    }
}