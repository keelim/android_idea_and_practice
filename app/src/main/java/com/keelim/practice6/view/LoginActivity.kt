package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.keelim.practice6.R
import com.keelim.practice6.view.customs.CustomConfirmDialog
import com.keelim.practice6.task.a_LoginRequest
import com.keelim.practice6.utils.SavedSharedPreference
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private var saveLoginData = false
    private lateinit var id: String
    private lateinit var pwd: String
    private lateinit var appData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (this as AppCompatActivity).supportActionBar!!.title = Html.fromHtml("<font color='#ffffff'>" + "로그인" + "</font>")

        //getting the set value (설정 값 불러오기)
        appData = getSharedPreferences("appData", Context.MODE_PRIVATE)
        valueLoad()

        // (2)로그인 창에서 회원가입 버튼을 눌렀을 때 화면이 넘어가는 부분
        registerButton.setOnClickListener {
            startActivity(Intent(this, a_RegisterActivity::class.java))
        }

        if (saveLoginData) {
            idText!!.setText(id)
            passwordText!!.setText(pwd)
            checkBox!!.isChecked = saveLoginData
        }

        // (5)로그인 버튼을 눌렀을 때 발생하는 이벤트 처리
        loginButton!!.setOnClickListener {
            val userID = idText!!.text.toString()
            val userPassword = passwordText!!.text.toString()
            val responseLister: Response.Listener<String?> = Response.Listener { response ->
                val jsonResponse = JSONObject(response)
                val success = jsonResponse.getBoolean("success")
                // success 이놈이 나올 경우 (로그인에 성공한 경우)
                if (success) {
                    save() //if logged in successfully, save the data (로그인 성공하면 데이터를 저장)
                    SavedSharedPreference.setId(this, userID)
                    CustomConfirmDialog()
                        .showConfirmDialog(this, "로그인에 성공하였습니다.", false)
                    // 화면 전환 (로그인창 -> 메인창)

                    Intent(this, LoginMainActivity::class.java).apply {
                        putExtra("userID", userID)
                        startActivity(this)
                    }
                    finish() //현재 액티비티 닫기
                } else CustomConfirmDialog().showConfirmDialog(this@LoginActivity, "계정을 다시 확인하세요.",true)
            }
            // 실제로 로그인을 보낼 수 있는 로그인 리퀘스트
            val loginRequest = a_LoginRequest(
                userID,
                userPassword,
                responseLister
            )
            val queue = Volley.newRequestQueue(this)
            queue.add(loginRequest)
        }
    }

    // a function that saves the set data (설정값을 저장하는 함수)
    private fun save() { // only SharedPreferences is not enough, use Editor (SharedPreferences 객체만으론 저장 불가능 Editor 사용)
        appData.edit().apply {
            putBoolean("SAVE_LOGIN_DATA", checkBox!!.isChecked)
            putString("ID", idText!!.text.toString().trim { it <= ' ' })
            putString("PWD", passwordText!!.text.toString().trim { it <= ' ' })
            apply()
        }
    }

    private fun valueLoad() {
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false)
        id = appData.getString("ID", "").toString()
        pwd = appData.getString("PWD", "").toString()
    }

    override fun onBackPressed() {
        startActivity(Intent(this@LoginActivity, FirstActivity::class.java))
        finish()
    }
}