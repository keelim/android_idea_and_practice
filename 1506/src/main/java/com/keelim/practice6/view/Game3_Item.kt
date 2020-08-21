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

class Game3_Item : AppCompatActivity() {
    private var Food = 0
    private var Water = 0
    private var Damage = 0
    private var Data_Box: SharedPreferences? = null
    private var Food_Amount_View: TextView? = null
    private var Water_Amount_View: TextView? = null
    private var Damage_View: TextView? = null
    var Reduced_Family_One_Damage = 0
    var Reduced_Family_Two_Damage = 0
    var New_Alert_Dialog // 다이어로그 띄우기 ('아이템을 구매할 수 없습니다!')
            : AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game3_activity_item)
        // ActionBar Hide!
        supportActionBar!!.hide()
        New_Alert_Dialog =
            AlertDialog.Builder(this@Game3_Item) // 다이어로그 띄우기 ('아이템을 구매할 수 없습니다!')
        Reduced_Family_Two_Damage = 0
        Reduced_Family_One_Damage = Reduced_Family_Two_Damage
        // ActionBar Hide!
        Data_Box = applicationContext.getSharedPreferences(
            "Data_Box",
            Context.MODE_PRIVATE
        )
        Initiating_Views()
    }

    private fun Initiating_Views() {
        Food = Data_Box!!.getInt("Food", 1)
        Water = Data_Box!!.getInt("Water", 1)
        Damage = Data_Box!!.getInt("Global_Damage", -1)
        Food_Amount_View = findViewById(R.id.Food_Amount_View_GetItem_Id)
        Water_Amount_View = findViewById(R.id.Water_Amount_View_GetItem_Id)
        Damage_View = findViewById(R.id.Damage_Amount_View_Id)
        Get_And_Update()
        val Family_One_Buy =
            findViewById<Button>(R.id.Family_One_Buy_Button_Id)
        val Family_Two_Buy =
            findViewById<Button>(R.id.Family_Two_Buy_Button_Id)
        // 첫째놈이 아이템을 구매한 경우!
        Family_One_Buy.setOnClickListener {
            if (Food >= 10 && Water >= 5) {
                Use_Dialogue("아이템을 사용했습니다.")
                Food = Food - 10
                Water = Water - 5
                Reduced_Family_One_Damage = Reduced_Family_One_Damage + 5
                Get_And_Update()
                // 만약 버튼을 1번만 누를 수 있도록 만들고 싶을 경우:
// Family_One_Buy.setEnabled(false);
            } else if (Food < 10 || Water < 5) {
                No_Use_Dialogue("아이템이 부족합니다.")
            } else {
                No_Use_Dialogue("에러! 살 수 없습니다.")
            }
        }
        // 둘째놈이 아이템을 구매한 경우!
        Family_Two_Buy.setOnClickListener {
            if (Food >= 10 && Water >= 5) {
                Use_Dialogue("아이템을 사용했습니다.")
                Food = Food - 10
                Water = Water - 5
                Reduced_Family_Two_Damage = Reduced_Family_Two_Damage + 5
                Get_And_Update()
                // 만약 버튼을 1번만 누를 수 있도록 만들고 싶을 경우:
// Family_Two_Buy.setEnabled(false);
            } else if (Food < 10 || Water < 5) {
                No_Use_Dialogue("아이템이 부족합니다.")
            } else {
                No_Use_Dialogue("에러! 살 수 없습니다.")
            }
        }
    }

    private fun Get_And_Update() {
        var Temp_Family_One_Damage: Int
        var Temp_Family_Two_Damage: Int
        Temp_Family_One_Damage = Data_Box!!.getInt("Family_One_Damage", -1)
        Temp_Family_Two_Damage = Data_Box!!.getInt("Family_Two_Damage", -1)
        Temp_Family_One_Damage = Temp_Family_One_Damage + Reduced_Family_One_Damage
        Temp_Family_Two_Damage = Temp_Family_Two_Damage + Reduced_Family_Two_Damage
        Food_Amount_View!!.text = Food.toString() + ""
        Water_Amount_View!!.text = Water.toString() + ""
        Damage_View!!.text = Damage.toString() + ""
        val Editor = Data_Box!!.edit()
        Editor.putInt("Food", Food)
        Editor.putInt("Water", Water)
        Editor.putInt("Family_One_Damage", Temp_Family_One_Damage)
        Editor.putInt("Family_Two_Damage", Temp_Family_Two_Damage)
        Editor.commit()
    }

    override fun onBackPressed() { //  super.onBackPressed();
        startActivity(Intent(this@Game3_Item, Game2Activity::class.java))
        finish()
    }

    // 다이어로그 띄우기 ('아이템을 구매할 수 없습니다!')
    private fun No_Use_Dialogue(NoUse: String) {
        New_Alert_Dialog!!.setCancelable(false)
        New_Alert_Dialog!!.setTitle("아이템 제작 불가")
        New_Alert_Dialog!!.setMessage(NoUse)
        New_Alert_Dialog!!.setNeutralButton(
            "Ok"
        ) { dialogInterface, i -> }.show()
    }

    // 다이어로그 띄우기2 ('아이템을 구매할 수 없습니다!')
    private fun Use_Dialogue(NoUse: String) {
        New_Alert_Dialog!!.setCancelable(false)
        New_Alert_Dialog!!.setTitle("아이템 제작 완료")
        New_Alert_Dialog!!.setMessage(NoUse)
        New_Alert_Dialog!!.setNeutralButton(
            "Ok"
        ) { dialogInterface, i -> }.show()
    }
}