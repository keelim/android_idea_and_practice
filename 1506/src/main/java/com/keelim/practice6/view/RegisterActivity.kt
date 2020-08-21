package com.keelim.practice6.view

import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.keelim.practice6.R
import com.keelim.practice6.task.a_RegisterRequest
import com.keelim.practice6.task.a_ValidateRequest
import com.keelim.practice6.view.customs.CustomConfirmDialog
import kotlinx.android.synthetic.main.login_activity1_register.*
import org.json.JSONObject
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private var userGender: String? = null
    private var validate = false //사용할 수 있는 회원 아이디인지 체크

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity1_register)
        supportActionBar!!.title = Html.fromHtml("<font color='#ffffff'>" + "회원가입" + "</font>")

        majorSpinner!!.adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item)
        // 디자인에서 만들었던 녀석들 매칭


        val genderGroupID = genderGroup.checkedRadioButtonId //현재 뭐가 선택되어 있는지 확인 (젠더 그룹에 선택된 게 남자인지 여자인지)
        userGender = (findViewById<View>(genderGroupID) as RadioButton).text.toString() //현재 선택된 버튼을 매칭 (남자 or 여자)

        // 라디오 버튼이 클릭됐을 때에 대한 이벤트 처리
        genderGroup.setOnCheckedChangeListener { radiogroup, i ->
            val genderButton =
                findViewById<View>(i) as RadioButton
            userGender = genderButton.text.toString() //현재 선택된 라디오 버튼을 찾은 뒤 유저젠더의 값을 바꿔줌
        }
        // (4)회원 중복 체크 버튼

        validateButton.setOnClickListener(View.OnClickListener {
            val userID = idText.text.toString()
            // 현재 만약에 validate 체크가 이뤄져있다면 바로 함수 종료
            if (validate) {
                return@OnClickListener
            }
            // 현재 체크가 안되어 있지만 ID 값에 아무런 값이 없다면 오류 발생 (ID는 빈 공간일 수 없으므로)
            if (userID == "") {
                CustomConfirmDialog().showConfirmDialog(this, "아이디는 빈칸일 수 없습니다.", true)
                return@OnClickListener
            }
            // 아이디가 5글자 이하 12글자 이상일때
            if (userID.length < 5 || userID.length > 12) {
                CustomConfirmDialog().showConfirmDialog(this, "아이디는 5자이상 12자미만입니다.", true)
                return@OnClickListener
            }
            // 한글 입력 안되게 제한
            if (!Pattern.matches("^[a-zA-Z0-9]$", userID)) {
                CustomConfirmDialog().showConfirmDialog(this, "아이디는 영어 대/소문자와 숫자만 가능합니다.", true)
                return@OnClickListener
            }
            // 정상적으로 ID 값을 입력했을 경우 중복체크 시작
            val responseListener: Response.Listener<String?> =
                Response.Listener<String?> { response ->
                    // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        // 만약 사용할 수 있는 아이디라면
                        if (success) {
                            CustomConfirmDialog().showConfirmDialog(
                                this@RegisterActivity,
                                "사용할 수 있는 아이디입니다.",
                                true
                            )
                            idText.isEnabled = false // ID 값을 더 이상 바꿀 수 없도록 고정
                            validate = true // 체크 완료
                            idText.setBackgroundColor(resources.getColor(R.color.colorGray))
                            validateButton.setBackgroundColor(resources.getColor(R.color.colorGray))
                        } else {
                            CustomConfirmDialog().showConfirmDialog(
                                this@RegisterActivity,
                                "사용할 수 없는 아이디입니다.",
                                true
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            // 실질적으로 접속할 수 있도록 생성자를 통해 객체를 만든다. (유저 ID, responseListener)
            // a_ValidateRequest.java라는 파일을 만들어야 한다.
            val validateRequest = a_ValidateRequest(userID, responseListener)
            val queue = Volley.newRequestQueue(this@RegisterActivity)
            queue.add(validateRequest)
        })
        // 회원가입 버튼

        registerButton.setOnClickListener(View.OnClickListener {
            val userID = idText.text.toString()
            val userPassword = passwordText.text.toString()
            val userMajor = majorSpinner!!.selectedItem.toString()
            val userEmail = emailText.text.toString()
            // 현재 중복체크가 되어 있지 않을 경우
            if (!validate) {
                CustomConfirmDialog().showConfirmDialog(this, "중복체크를 해주세요.", true)
                return@OnClickListener
            }
            // 하나라도 빈 공간이 있을 경우
            if (userID == "" || userPassword == "" || userMajor == "" || userEmail == "" || userGender == "") {
                CustomConfirmDialog().showConfirmDialog(this, "빈 칸 없이 입력해주세요.", true)
                return@OnClickListener
            }
            // 유효성 검사 추가
            if (userPassword.length < 5 || userPassword.length > 15) {
                CustomConfirmDialog().showConfirmDialog(this, "패스워드는 5자이상 15자미만입니다.", true)
                return@OnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                CustomConfirmDialog().showConfirmDialog(this, "잘못된 이메일형식입니다.", true)
                return@OnClickListener
            }
            val responseListener: Response.Listener<String?> =
                Response.Listener { response ->
                    // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        // 만약 사용할 수 있는 아이디라면
                        if (success) {
                            CustomConfirmDialog().showConfirmDialog(
                                this@RegisterActivity,
                                "회원 등록에 성공하였습니다.",
                                false
                            )
                            finish() //회원가입 창을 닫는다.
                        } else {
                            CustomConfirmDialog().showConfirmDialog(
                                this@RegisterActivity,
                                "회원 등록에 실패하였습니다.",
                                true
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } // 실질적으로 접속할 수 있도록 생성자를 통해 객체를 만든다.

            val registerRequest = a_RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener)
            val queue = Volley.newRequestQueue(this)
            queue.add(registerRequest)
        })
    }
}