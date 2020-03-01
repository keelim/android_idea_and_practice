package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R

class Game0_Start : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game0_activity_start)
        // ActionBar Hide!
        supportActionBar!!.hide()
        Buttons_Working()
    }

    private fun Buttons_Working() {
        val New_Game =
            findViewById<Button>(R.id.New_Game_Button_ID)
        val Load_Game =
            findViewById<Button>(R.id.Load_Game_Button_ID)
        // 새로시작 버튼
        New_Game.setOnClickListener {
            val Data_Box = applicationContext.getSharedPreferences(
                "Data_Box",
                Context.MODE_PRIVATE
            )
            val Editor = Data_Box.edit()
            Editor.putInt("Day", 0)
            Editor.putInt("Family_One_Hungry", 100)
            Editor.putInt("Family_One_Thirst", 100)
            Editor.putInt("Family_One_Hp", 100)
            Editor.putInt("Family_Two_Hungry", 100)
            Editor.putInt("Family_Two_Thirst", 100)
            Editor.putInt("Family_Two_Hp", 100)
            Editor.putInt("Food", 10)
            Editor.putInt("Water", 10)
            Editor.putInt("Global_Damage", -1)
            Editor.putInt("Family_One_Damage", -1)
            Editor.putInt("Family_Two_Damage", -1)
            Editor.putBoolean("Family_One_Not_Died", true)
            Editor.putBoolean("Family_Two_Not_Died", true)
            Editor.commit()
            val Launch_Activity = Intent(this@Game0_Start, Game1_Day::class.java)
            startActivity(Launch_Activity)
        }
        // 불러오기 버튼 : 그냥 이동
        Load_Game.setOnClickListener {
            val Launch_Activity = Intent(this@Game0_Start, Game1_Day::class.java)
            startActivity(Launch_Activity)
        }
    }
}