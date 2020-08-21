package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import kotlinx.android.synthetic.main.game0_activity_start.*

class Game0Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game0_activity_start)
        // ActionBar Hide!
        supportActionBar!!.hide()

        New_Game_Button_ID.setOnClickListener {
            val sharedPreferences = applicationContext.getSharedPreferences("Data_Box", Context.MODE_PRIVATE)

            sharedPreferences.edit().apply {
                putInt("Day", 0)
                putInt("Family_One_Hungry", 100)
                putInt("Family_One_Thirst", 100)
                putInt("Family_One_Hp", 100)
                putInt("Family_Two_Hungry", 100)
                putInt("Family_Two_Thirst", 100)
                putInt("Family_Two_Hp", 100)
                putInt("Food", 10)
                putInt("Water", 10)
                putInt("Global_Damage", -1)
                putInt("Family_One_Damage", -1)
                putInt("Family_Two_Damage", -1)
                putBoolean("Family_One_Not_Died", true)
                putBoolean("Family_Two_Not_Died", true)
                apply()
            }
            startActivity(Intent(this, Game1Actvity::class.java))
        }

        Load_Game_Button_ID.setOnClickListener {
            startActivity(Intent(this, Game1Actvity::class.java))
        }


    }

}