package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import kotlinx.android.synthetic.main.activity_game2.*
import java.util.*

class Game2Activity : AppCompatActivity() {
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

    private var Family_One_Not_Died = true
    private var Family_Two_Not_Died = true
    private var sharedPreferences: SharedPreferences? = null
    var New_Alert_Dialog: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)
        // ActionBar Hide!
        supportActionBar!!.hide()
        New_Alert_Dialog = AlertDialog.Builder(this)
        sharedPreferences = applicationContext.getSharedPreferences("Data_Box", Context.MODE_PRIVATE)
        Initiating_Views()
        Getting_Data()

        Item_List_Button_Id.setOnClickListener {
            startActivity(Intent(this, Game3_Item::class.java))
            finish()
        }
        Start_Running_Button_Id.setOnClickListener {
            startActivity(Intent(this, Game4_Run::class.java))
            finish()
        }
    }

    //     Getting Data From Shared Preference For A New Or A Loaded Game
    private fun Getting_Data() {
        Family_One_Hungry = sharedPreferences!!.getInt("Family_One_Hungry", 100)
        Family_One_Thirst = sharedPreferences!!.getInt("Family_One_Thirst", 100)
        Family_One_Hp = sharedPreferences!!.getInt("Family_One_Hp", 100)
        Family_One_Damage = sharedPreferences!!.getInt("Family_One_Damage", -1)
        Family_Two_Hungry = sharedPreferences!!.getInt("Family_Two_Hungry", 100)
        Family_Two_Thirst = sharedPreferences!!.getInt("Family_Two_Thirst", 100)
        Family_Two_Hp = sharedPreferences!!.getInt("Family_Two_Hp", 100)
        Family_Two_Damage = sharedPreferences!!.getInt("Family_Two_Damage", -1)
        Water = sharedPreferences!!.getInt("Water", 10)
        Food = sharedPreferences!!.getInt("Food", 10)
        Damage = sharedPreferences!!.getInt("Global_Damage", -1)
        Family_One_Not_Died = sharedPreferences!!.getBoolean("Family_One_Not_Died", true)
        Family_Two_Not_Died = sharedPreferences!!.getBoolean("Family_Two_Not_Died", true)

    }

    private fun Initiating_Views() { // Family One View Initiating

        Put_And_Update_Data()
        // 첫째놈 Food 버튼 클릭시!!!!!
        Family_One_Food_Button_Id.setOnClickListener {
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
        Family_One_Water_Button_Id.setOnClickListener {
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
        Family_Two_Food_Button_Id.setOnClickListener {
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
        Family_Two_Water_Button_Id.setOnClickListener {
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
        val Editor = sharedPreferences!!.edit()
        // Set Data
// 첫째놈이 죽은 경우
        if (Family_One_Hungry <= 0 || Family_One_Thirst <= 0 || Family_One_Hp <= 0 || !Family_One_Not_Died) // 주인공이 죽었을 경우 Family_One_Not_Died는 False (즉 느낌표를 붙여서 True가 되면 사망상태)
        {
            Family_One_Hungry_View_Id!!.text = " _"
            Family_One_Thirst_View_Id!!.text = " _ "
            Family_One_Hp_View_Id!!.text = " _ "
            Family_One_Damage_View_Id!!.text = " _ "
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
            Family_One_Hungry_View_Id!!.text = Family_One_Hungry.toString()
            Family_One_Thirst_View_Id!!.text = Family_One_Thirst.toString()
            Family_One_Hp_View_Id!!.text = Family_One_Hp.toString()
            Family_One_Damage_View_Id!!.text = Family_One_Damage.toString()
            Editor.putInt("Family_One_Hungry", Family_One_Hungry)
            Editor.putInt("Family_One_Thirst", Family_One_Thirst)
            Editor.putInt("Family_One_Hp", Family_One_Hp)
            Editor.putBoolean("Family_One_Not_Died", Family_One_Not_Died)
        }
        // 둘째 놈이 죽은 경우
        if (Family_Two_Hungry <= 0 || Family_Two_Thirst <= 0 || Family_Two_Hp <= 0 || !Family_Two_Not_Died) // 주인공이 죽었을 경우 Family_One_Not_Died는 False (즉 느낌표를 붙여서 True가 되면 사망상태)
        {
            Family_Two_Hungry_View_Id!!.text = " _ "
            Family_Two_Thirst_View_Id!!.text = " _ "
            Family_Two_Hp_View_Id!!.text = " _ "
            Family_Two_Damage_View_Id!!.text = " _ "
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
            Family_Two_Hungry_View_Id!!.text = Family_Two_Hungry.toString()
            Family_Two_Thirst_View_Id!!.text = Family_Two_Thirst.toString()
            Family_Two_Hp_View_Id!!.text = Family_Two_Hp.toString()
            Family_Two_Damage_View_Id!!.text = Family_Two_Damage.toString()
            Editor.putInt("Family_Two_Hungry", Family_Two_Hungry)
            Editor.putInt("Family_Two_Thirst", Family_Two_Thirst)
            Editor.putInt("Family_Two_Hp", Family_Two_Hp)
            Editor.putBoolean("Family_Two_Not_Died", Family_Two_Not_Died)
        }
        Food_Amount_View_ID!!.text = Food.toString()
        Water_Amount_View_Id!!.text = Water.toString()
        Damage_Amount_View_Id!!.text = Damage.toString()
        Editor.putInt("Food", Food)
        Editor.putInt("Water", Water)
        Editor.apply()
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
