package com.keelim.practice6.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import com.keelim.practice6.game_mode.Game0_Start
import com.keelim.practice6.nomal_mode.LoginActivity

class FirstActivity : AppCompatActivity() {
    private var normalModeButton: Button? = null
    private var loginButton: Button? = null
    private var questionMarkButton: ImageView? = null
    private var walkAwayTitle: TextView? = null
    var font_one: Typeface? = null
    private var infoButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        supportActionBar!!.hide()
        font_one = Typeface.createFromAsset(assets, "fonts/font_one.ttf")


        walkAwayTitle!!.typeface = font_one
        normalModeButton!!.setOnClickListener { view ->
            startActivityForResult(Intent(view.context, Game0_Start::class.java), 0)
            finish()
        }
        loginButton!!.setOnClickListener { view ->
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivityForResult(intent, 0)
            finish()
        }
        questionMarkButton!!.setOnClickListener {
            val dialog = Dialog(this@FirstActivity).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE) //for title bars not to be appeared (타이틀 바 안보이게)
                setContentView(R.layout.dialog_exp)
                setCanceledOnTouchOutside(false) //to prevent dialog getting dismissed on outside touch
                setCancelable(false) //to prevent dialog getting dismissed on back button
                show()
            }
            val dialogButton = dialog.findViewById<View>(R.id.confirmButton) as Button
            dialogButton.setOnClickListener { dialog.dismiss() }
        }
        infoButton!!.setOnClickListener {
            startActivity(Intent(this@FirstActivity, PopUpActivity::class.java))
        }
    }

    // 두번 뒤로가기 버튼을 누르면 종료
    private var lastTimeBackPressed: Long = 0

    override fun onBackPressed() { // 한번 버튼을 누른 뒤, 1.5초 이내에 또 누르면 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish()
            return
        }
        Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        lastTimeBackPressed = System.currentTimeMillis()
    }
}