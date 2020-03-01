package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import java.util.*

class Game2_Main : AppCompatActivity() {
    private var Die = false
    private var Family_One_Hungry = 0
    private var Family_One_Thirst = 0
    private var Family_One_Hp = 0
    private var Family_One_Damage = 0
    private var Family_Two_Hungry = 0
    private var Family_Two_Thirst = 0
    private var Family_Two_Hp = 0
    private var Family_Two_Damage = 0
    private var Food = 0
    private var Water = 0
    private var Damage = 0
    private var Family_One_Hungry_View: TextView? = null
    private var Family_One_Thirst_View: TextView? = null
    private var Family_One_Hp_View: TextView? = null
    private var Family_One_Damage_View: TextView? = null
    private var Family_Two_Hungry_View: TextView? = null
    private var Family_Two_Thirst_View: TextView? = null
    private var Family_Two_Hp_View: TextView? = null
    private var Family_Two_Damage_View: TextView? = null
    private var Food_View: TextView? = null
    private var Water_View: TextView? = null
    private var Damage_View: TextView? = null
    private var Family_One_Not_Died = true
    private var Family_Two_Not_Died = true
    private var Data_Box: SharedPreferences? = null
    var New_Alert_Dialog: AlertDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game2_activity_main)
        // ActionBar Hide!
        supportActionBar!!.hide()
        New_Alert_Dialog = AlertDialog.Builder(this@Game2_Main)
        Data_Box = applicationContext.getSharedPreferences(
            "Data_Box",
            Context.MODE_PRIVATE
        )
        Getting_Data()
    }

    //     Getting Data From Shared Preference For A New Or A Loaded Game
    private fun Getting_Data() {
        Family_One_Hungry = Data_Box!!.getInt("Family_One_Hungry", 100)
        Family_One_Thirst = Data_Box!!.getInt("Family_One_Thirst", 100)
        Family_One_Hp = Data_Box!!.getInt("Family_One_Hp", 100)
        Family_One_Damage = Data_Box!!.getInt("Family_One_Damage", -1)
        Family_Two_Hungry = Data_Box!!.getInt("Family_Two_Hungry", 100)
        Family_Two_Thirst = Data_Box!!.getInt("Family_Two_Thirst", 100)
        Family_Two_Hp = Data_Box!!.getInt("Family_Two_Hp", 100)
        Family_Two_Damage = Data_Box!!.getInt("Family_Two_Damage", -1)
        Water = Data_Box!!.getInt("Water", 10)
        Food = Data_Box!!.getInt("Food", 10)
        Damage = Data_Box!!.getInt("Global_Damage", -1)
        Family_One_Not_Died = Data_Box!!.getBoolean("Family_One_Not_Died", true)
        Family_Two_Not_Died = Data_Box!!.getBoolean("Family_Two_Not_Died", true)
        // 하루가 지나면 배고픔, 목마름 지수가 마이너스 시킨다.
// 그런데 이 구간에서 마이너스 시킬 경우 불러오기 할때마다 마이너스 되는 버그 발생
/*
        Random r = new Random();
        int Random_One_Value1 = r.nextInt(30 - 10) + 10;
        int Random_One_Value2 = r.nextInt(30 - 10) + 10;
        int Random_Two_Value1 = r.nextInt(30 - 10) + 10;
        int Random_Two_Value2 = r.nextInt(30 - 10) + 10;

        Family_One_Hungry = Family_One_Hungry - Random_One_Value1;
        Family_One_Thirst = Family_One_Thirst - Random_One_Value2;
        Family_Two_Hungry = Family_Two_Hungry - Random_Two_Value1;
        Family_Two_Thirst = Family_Two_Thirst - Random_Two_Value2;
*/Initiating_Views()
    }

    // Initiating and Views And Button
    private fun Initiating_Views() { // Family One View Initiating
        Family_One_Hungry_View = findViewById(R.id.Family_One_Hungry_View_Id)
        Family_One_Thirst_View = findViewById(R.id.Family_One_Thirst_View_Id)
        Family_One_Hp_View = findViewById(R.id.Family_One_Hp_View_Id)
        Family_One_Damage_View = findViewById(R.id.Family_One_Damage_View_Id)
        val family_One_Food_Button =
            findViewById<Button>(R.id.Family_One_Food_Button_Id)
        val family_One_Water_Button =
            findViewById<Button>(R.id.Family_One_Water_Button_Id)
        // Family Two View Initiating
        Family_Two_Hungry_View = findViewById(R.id.Family_Two_Hungry_View_Id)
        Family_Two_Thirst_View = findViewById(R.id.Family_Two_Thirst_View_Id)
        Family_Two_Hp_View = findViewById(R.id.Family_Two_Hp_View_Id)
        Family_Two_Damage_View = findViewById(R.id.Family_Two_Damage_View_Id)
        val family_Two_Food_Button =
            findViewById<Button>(R.id.Family_Two_Food_Button_Id)
        val family_Two_Water_Button =
            findViewById<Button>(R.id.Family_Two_Water_Button_Id)
        Food_View = findViewById(R.id.Food_Amount_View_ID)
        Water_View = findViewById(R.id.Water_Amount_View_Id)
        Damage_View = findViewById(R.id.Damage_Amount_View_Id)
        val Get_Item_Button: Button
        val Running_Button: Button
        Get_Item_Button = findViewById(R.id.Item_List_Button_Id)
        Running_Button = findViewById(R.id.Start_Running_Button_Id)
        Get_Item_Button.setOnClickListener {
            startActivity(Intent(this@Game2_Main, Game3_Item::class.java))
            finish()
        }
        Running_Button.setOnClickListener {
            startActivity(Intent(this@Game2_Main, Game4_Run::class.java))
            finish()
        }
        Put_And_Update_Data()
        // 첫째놈 Food 버튼 클릭시!!!!!
        family_One_Food_Button.setOnClickListener {
            if (Food > 0 && Family_One_Hungry < 100) {
                Food--
                val r = Random()
                val Random_One_Value = r.nextInt(30 - 10) + 10
                Family_One_Hungry = Family_One_Hungry + Random_One_Value
                // 100 이상 회복될 경우 그냥 100으로 고정
                if (Family_One_Hungry > 100) {
                    Family_One_Hungry = 100
                }
                Put_And_Update_Data()
            } else if (Food <= 0) {
                No_Use_Dialogue("아이템이 부족합니다.")
            } else if (Family_One_Hungry >= 100) {
                No_Use_Dialogue("더 줄 필요가 없습니다.")
            } else {
                No_Use_Dialogue("에러! 줄 수 없습니다!")
            }
        }
        // 첫째놈 Water 버튼 클릭시!!!!!
        family_One_Water_Button.setOnClickListener {
            if (Water > 0 && Family_One_Thirst < 100) {
                Water--
                val r = Random()
                val Random_One_Value = r.nextInt(30 - 10) + 10
                Family_One_Thirst = Family_One_Thirst + Random_One_Value
                // 100 이상 회복될 경우 그냥 100으로 고정
                if (Family_One_Thirst > 100) {
                    Family_One_Thirst = 100
                }
                Put_And_Update_Data()
            } else if (Water <= 0) {
                No_Use_Dialogue("아이템이 부족합니다.")
            } else if (Family_One_Thirst >= 100) {
                No_Use_Dialogue("더 줄 필요가 없습니다.")
            } else {
                No_Use_Dialogue("에러! 줄 수 없습니다!")
            }
        }
        // 둘째놈 Food 버튼 클릭시!!
        family_Two_Food_Button.setOnClickListener {
            if (Food > 0 && Family_Two_Hungry < 100) {
                Food--
                val r = Random()
                val Random_One_Value = r.nextInt(30 - 10) + 10
                Family_Two_Hungry = Family_Two_Hungry + Random_One_Value
                // 100 이상 회복될 경우 그냥 100으로 고정
                if (Family_Two_Hungry > 100) {
                    Family_Two_Hungry = 100
                }
                Put_And_Update_Data()
            } else if (Food <= 0) {
                No_Use_Dialogue("아이템이 부족합니다.")
            } else if (Family_Two_Hungry >= 100) {
                No_Use_Dialogue("더 줄 필요가 없습니다.")
            } else {
                No_Use_Dialogue("에러! 줄 수 없습니다!")
            }
        }
        // 둘째놈 Water 버튼 클릭시!!
        family_Two_Water_Button.setOnClickListener {
            if (Water > 0 && Family_Two_Thirst < 100) {
                Water--
                val r = Random()
                val Random_One_Value = r.nextInt(30 - 10) + 10
                Family_Two_Thirst = Family_Two_Thirst + Random_One_Value
                if (Family_Two_Thirst > 100) {
                    Family_Two_Thirst = 100
                }
                Put_And_Update_Data()
            } else if (Water <= 0) {
                No_Use_Dialogue("아이템이 부족합니다.")
            } else if (Family_Two_Thirst >= 100) {
                No_Use_Dialogue("더 줄 필요가 없습니다.")
            } else {
                No_Use_Dialogue("에러! 줄 수 없습니다!")
            }
        }
    }

    // Updating Data Into Views And Shared Preference
    private fun Put_And_Update_Data() {
        val Editor = Data_Box!!.edit()
        // Set Data
// 첫째놈이 죽은 경우
        if (Family_One_Hungry <= 0 || Family_One_Thirst <= 0 || Family_One_Hp <= 0 || !Family_One_Not_Died) // 주인공이 죽었을 경우 Family_One_Not_Died는 False (즉 느낌표를 붙여서 True가 되면 사망상태)
        {
            Family_One_Hungry_View!!.text = " _"
            Family_One_Thirst_View!!.text = " _ "
            Family_One_Hp_View!!.text = " _ "
            Family_One_Damage_View!!.text = " _ "
            if (Family_One_Hungry <= 0) {
                Kill_Dialogue("첫째가 굶어 죽었습니다...")
            } else if (Family_One_Thirst <= 0) {
                Kill_Dialogue("첫째가 목말라 죽었습니다...")
            } else if (Family_One_Hp <= 0) {
                Kill_Dialogue("첫째가 마력 부족으로 죽었습니다...")
            } else {
                Kill_Dialogue("에러! 첫째가 좀비처럼 또 죽었습니다...")
            }
            // 만약 둘중 한명만 사망해도 게임오버 안되고 계속 게임을 진행 가능하게 만들 경우를 대비해 만든 구간
// 그러나 현재는 예산부족으로 인해(...) 둘중 한놈만 죽어도 바로 [Kill_Dialogue]에서 엔딩 동영상으로 이어지게 해놨다.
//if (Family_One_Not_Died) {}
            Family_One_Not_Died = false
            Editor.putBoolean("Family_One_Not_Died", Family_One_Not_Died)
        } else {
            Family_One_Hungry_View!!.text = Family_One_Hungry.toString()
            Family_One_Thirst_View!!.text = Family_One_Thirst.toString()
            Family_One_Hp_View!!.text = Family_One_Hp.toString()
            Family_One_Damage_View!!.text = Family_One_Damage.toString()
            Editor.putInt("Family_One_Hungry", Family_One_Hungry)
            Editor.putInt("Family_One_Thirst", Family_One_Thirst)
            Editor.putInt("Family_One_Hp", Family_One_Hp)
            Editor.putBoolean("Family_One_Not_Died", Family_One_Not_Died)
        }
        // 둘째 놈이 죽은 경우
        if (Family_Two_Hungry <= 0 || Family_Two_Thirst <= 0 || Family_Two_Hp <= 0 || !Family_Two_Not_Died) // 주인공이 죽었을 경우 Family_One_Not_Died는 False (즉 느낌표를 붙여서 True가 되면 사망상태)
        {
            Family_Two_Hungry_View!!.text = " _ "
            Family_Two_Thirst_View!!.text = " _ "
            Family_Two_Hp_View!!.text = " _ "
            Family_Two_Damage_View!!.text = " _ "
            if (Family_Two_Hungry <= 0) {
                Kill_Dialogue("둘째가 굶어 죽었습니다...")
            } else if (Family_Two_Thirst <= 0) {
                Kill_Dialogue("둘째가 목말라 죽었습니다...")
            } else if (Family_Two_Hp <= 0) {
                Kill_Dialogue("둘째가 마력 부족으로 죽었습니다...")
            } else {
                Kill_Dialogue("에러! 둘째가 좀비처럼 또 죽었습니다...")
            }
            Family_Two_Not_Died = false
            Editor.putBoolean("Family_Two_Not_Died", Family_Two_Not_Died)
        } else {
            Family_Two_Hungry_View!!.text = Family_Two_Hungry.toString()
            Family_Two_Thirst_View!!.text = Family_Two_Thirst.toString()
            Family_Two_Hp_View!!.text = Family_Two_Hp.toString()
            Family_Two_Damage_View!!.text = Family_Two_Damage.toString()
            Editor.putInt("Family_Two_Hungry", Family_Two_Hungry)
            Editor.putInt("Family_Two_Thirst", Family_Two_Thirst)
            Editor.putInt("Family_Two_Hp", Family_Two_Hp)
            Editor.putBoolean("Family_Two_Not_Died", Family_Two_Not_Died)
        }
        Food_View!!.text = Food.toString()
        Water_View!!.text = Water.toString()
        Damage_View!!.text = Damage.toString()
        Editor.putInt("Food", Food)
        Editor.putInt("Water", Water)
        Editor.commit()
    }

    override fun onResume() {
        Getting_Data()
        super.onResume()
    }

    // 다이어로그1 : 사망
    private fun Kill_Dialogue(Family_No: String) { // 이미 죽은 경우 다이얼로그 안띄우고 걍 종료시킨다!
        if (Die == true) return
        Die = true
        New_Alert_Dialog!!.setCancelable(false)
        New_Alert_Dialog!!.setTitle("주인공이 사망했습니다.")
        New_Alert_Dialog!!.setMessage(Family_No)
        New_Alert_Dialog!!.setNeutralButton(
            "Ok"
        ) { dialogInterface, i ->
            val intent = Intent(
                applicationContext,
                Game5_DeadVideo::class.java
            ) // game ending
            finish() // end NUM1 screen
            startActivity(intent) // move NUM5 screen
        }.show()
    }

    // 다이어로그2 : 아이템 사용불가
    private fun No_Use_Dialogue(NoUse: String) {
        New_Alert_Dialog!!.setCancelable(false)
        New_Alert_Dialog!!.setTitle("아이템 사용 불가")
        New_Alert_Dialog!!.setMessage(NoUse)
        New_Alert_Dialog!!.setNeutralButton(
            "Ok"
        ) { dialogInterface, i -> }.show()
    } // end onCreate()
} // end Main_Activity
