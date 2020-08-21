package com.keelim.practice11.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import com.keelim.practice11.view.SubwayActivity

class SubwayActivity : AppCompatActivity() {
    var btn_subway1: ImageButton? = null
    var btn_subway2: ImageButton? = null
    var btn_subway3: ImageButton? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subway)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        btn_subway1 = findViewById(R.id.btn_subway1)
        btn_subway1.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@SubwayActivity, LostSubwayActivity::class.java)) })
        btn_subway2 = findViewById(R.id.btn_subway2)
        btn_subway2.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@SubwayActivity, LostSubway2Activity::class.java)) })
        btn_subway3 = findViewById(R.id.btn_subway3)
        btn_subway3.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@SubwayActivity, LostSubway3Activity::class.java)) })
    }
}