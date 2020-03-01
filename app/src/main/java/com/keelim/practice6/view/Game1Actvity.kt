package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import kotlinx.android.synthetic.main.activity_game1.*

class Game1Actvity : AppCompatActivity() {
    private var Day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game1)
        // ActionBar Hide!
        supportActionBar!!.hide()
        Initiate_Diary()
    }

    private fun Initiate_Diary() {
        val sharedPreferences = applicationContext.getSharedPreferences("Data_Box", Context.MODE_PRIVATE)
        Day = sharedPreferences.getInt("Day", 0)

        if (Day == 0) {
            startActivity(Intent(this, Game5_StartVideo::class.java)) // move NUM5 screen
        }

        val Images_Array = resources.obtainTypedArray(R.array.Images_Data)

        WalkImage_View_Id.setImageResource(Images_Array.getResourceId(Day, -1))
        // Getting Day No And Set Diary Message From String Array Saved In String File
        val diaryMessage = resources.getStringArray(R.array.Diary_Messages)
        Day_No_View_Id.text = "Day $Day"
        Diary_View_Id.text = diaryMessage[Day]

        val anim = AnimationUtils.loadAnimation(this, R.anim.game_logo_anim)
        Day_No_View_Id.clearAnimation()
        Day_No_View_Id.animation = anim
        val anim_1 = AnimationUtils.loadAnimation(this, R.anim.game_text_anim)
        Diary_View_Id.clearAnimation()
        Diary_View_Id.animation = anim_1
        val anim_2 = AnimationUtils.loadAnimation(this, R.anim.game_text_anim)
        WalkImage_View_Id.clearAnimation()
        WalkImage_View_Id.animation = anim_2
        val Day_font = Typeface.createFromAsset(assets, "fonts/game_font2.ttf")

        Day_No_View_Id.typeface = Day_font
        //  Diary_Message_View.setTypeface(Day_font);
        Next_Button_Id.setOnClickListener {
            if (Day < 70) // 엔딩 날짜가 아닌 경우 (현재 엔딩 날짜: 70일)
                startActivity(Intent(this@Game1Actvity, Game2Activity::class.java))
            finish()
        }
    }
}
