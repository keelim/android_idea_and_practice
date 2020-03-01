package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R

class Game1_Day : AppCompatActivity() {
    private var Day = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game1_activity_day)
        // ActionBar Hide!
        supportActionBar!!.hide()
        Initiate_Diary()
    }

    private fun Initiate_Diary() {
        val Data_Box = applicationContext.getSharedPreferences(
            "Data_Box",
            Context.MODE_PRIVATE
        )
        Day = Data_Box.getInt("Day", 0)
        // 시작 동영상 실행...
        if (Day == 0) {
            val intent = Intent(
                applicationContext,
                Game5_StartVideo::class.java
            ) // game video
            startActivity(intent) // move NUM5 screen
        }
        /*

        //   Reducing Hungry And Increasing Damage According To Day
        if (Day >= 1) {
            int Temp_One_Hungry = Data_Box.getInt("Family_One_Hungry", 100);
            int Temp_Two_Hungry = Data_Box.getInt("Family_Two_Hungry", 100);
            int Temp_One_Thirst = Data_Box.getInt("Family_One_Thirst", 100);
            int Temp_Two_Thirst = Data_Box.getInt("Family_Two_Thirst", 100);


            */
/*
            // 이 부분은 Game4_Run.java 파일로 이사갔습니다.
            // 이 구간에서 마이너스 시킬 경우 불러오기 할때마다 마이너스 되는 버그 발생
            Random r = new Random();
            int Random_One_Value = r.nextInt(30 - 10) + 10;
            Temp_One_Hungry=Temp_One_Hungry-Random_One_Value;
            int Random_Two_Value = r.nextInt(30 - 10) + 10;
            Temp_Two_Hungry=Temp_Two_Hungry-Random_Two_Value;
            int Random_One_Value2 = r.nextInt(30 - 10) + 10;
            Temp_One_Thirst=Temp_One_Thirst-Random_One_Value2;
            int Random_Two_Value2 = r.nextInt(30 - 10) + 10;
            Temp_Two_Thirst=Temp_Two_Thirst-Random_Two_Value2;
            */
/*



            int Temp_Global_Damage = Data_Box.getInt("Global_Damage", -1);
            int Temp_Family_One_Damage = Data_Box.getInt("Family_One_Damage", -1);
            int Temp_Family_Two_Damage = Data_Box.getInt("Family_Two_Damage", -1);
            int Temp_One_Hp = Data_Box.getInt("Family_One_Hp", 100);
            int Temp_Two_Hp = Data_Box.getInt("Family_Two_Hp", 100);


            // 마찬가지로 여기서 마이너스 시키면 켤때마다 마이너스가 되는 버그가 발생합니다
            //Temp_Global_Damage = Temp_Global_Damage - Day;
            //Temp_Family_One_Damage = Temp_Family_One_Damage - Day;
            //Temp_Family_Two_Damage = Temp_Family_Two_Damage - Day;
            //Temp_One_Hp = Temp_One_Hp + Temp_Family_One_Damage;
            //Temp_Two_Hp = Temp_Two_Hp + Temp_Family_Two_Damage;



            SharedPreferences.Editor editor = Data_Box.edit();
            editor.putInt("Family_One_Hungry", Temp_One_Hungry);
            editor.putInt("Family_Two_Hungry", Temp_Two_Hungry);

            editor.putInt("Family_One_Thirst", Temp_One_Thirst);
            editor.putInt("Family_Two_Thirst", Temp_Two_Thirst);


            editor.putInt("Family_One_Hp", Temp_One_Hp);
            editor.putInt("Family_Two_Hp", Temp_Two_Hp);
            editor.putInt("Global_Damage", Temp_Global_Damage);
            editor.putInt("Family_One_Damage", Temp_Family_One_Damage);
            editor.putInt("Family_Two_Damage", Temp_Family_Two_Damage);
            editor.commit();
        }

*/
        val Day_No_View = findViewById<TextView>(R.id.Day_No_View_Id)
        val Diary_Message_View = findViewById<TextView>(R.id.Diary_View_Id)
        val Next_Button =
            findViewById<Button>(R.id.Next_Button_Id)
        // 시작시 이미지 띄워주는 부분 데이터 받아오기
        val Images_Array = resources.obtainTypedArray(R.array.Images_Data)
        val Status_Image =
            findViewById<ImageView>(R.id.WalkImage_View_Id)
        Status_Image.setImageResource(Images_Array.getResourceId(Day, -1))
        // Getting Day No And Set Diary Message From String Array Saved In String File
        val Diary_Message =
            resources.getStringArray(R.array.Diary_Messages)
        Day_No_View.text = "Day $Day"
        Diary_Message_View.text = Diary_Message[Day]
        val anim =
            AnimationUtils.loadAnimation(this, R.anim.game_logo_anim)
        Day_No_View.clearAnimation()
        Day_No_View.animation = anim
        val anim_1 =
            AnimationUtils.loadAnimation(this, R.anim.game_text_anim)
        Diary_Message_View.clearAnimation()
        Diary_Message_View.animation = anim_1
        val anim_2 =
            AnimationUtils.loadAnimation(this, R.anim.game_text_anim)
        Status_Image.clearAnimation()
        Status_Image.animation = anim_2
        val Day_font = Typeface.createFromAsset(assets, "fonts/game_font2.ttf")
        val Msg_font = Typeface.createFromAsset(assets, "fonts/game_msg_font.otf")
        Day_No_View.typeface = Day_font
        //  Diary_Message_View.setTypeface(Day_font);
        Next_Button.setOnClickListener {
            if (Day < 70) // 엔딩 날짜가 아닌 경우 (현재 엔딩 날짜: 70일)
            {
                val Launch_Activity = Intent(this@Game1_Day, Game2_Main::class.java)
                startActivity(Launch_Activity)
                finish()
            } else  // 엔딩 날짜에 도달한 경우
            {
                val intent = Intent(
                    applicationContext,
                    Game5_EndingVideo::class.java
                ) // game ending
                finish() // end NUM1 screen
                startActivity(intent) // move NUM5 screen
            }
        }
    }
}