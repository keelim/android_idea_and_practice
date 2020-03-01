package com.keelim.practice6.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import com.keelim.practice6.utils.SavedSharedPreference
import kotlinx.android.synthetic.main.activity_login_setting.*
import kotlinx.android.synthetic.main.dialog_weight.*

class SettingActivity : AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var font_two: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_setting)

        supportActionBar!!.title = Html.fromHtml("<font color='#ffffff'>" + "설정" + "</font>")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        userId = LoginMainActivity.userID

        font_two = Typeface.createFromAsset(assets, "fonts/font_two.ttf") //NHC 고도 마음체 godoM
        // 첫번째 버튼: 몸무게 설정
        loggedInWeightSetting!!.typeface = font_two
        loggedInWeightSetting!!.setOnClickListener { weightSetting() }
        /*
        // 두번째 버튼: 전송할 전화번호 설정
        BlueTooth = (Button) findViewById(R.id.BlueTooth);
        BlueTooth.setTypeface(font_two);
        BlueTooth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bluegetheart.class);
                startActivity(intent);
            }
        });
        */
/*

        // 세번째 버튼: 심박수 블루투스 연결
        SMS_setting = (Button) findViewById(R.id.SMS_setting);
        SMS_setting.setTypeface(font_two);
        SMS_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SmsMainActivity.class);
                startActivity(intent);
            }
        });
*/
        propicsetting!!.typeface = font_two
        propicsetting!!.setOnClickListener {
            startActivity(Intent(applicationContext, ProfilePictureActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // handle arrow click here
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, LoginMainActivity::class.java)
            intent.putExtra("userID",
                LoginMainActivity.userID
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Intent(this, LoginMainActivity::class.java).apply {
            putExtra("userID",
                LoginMainActivity.userID
            )
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(this)
        }
        finish()
    }

    private fun weightSetting() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE) //for title bars not to be appeared (타이틀 바 안보이게)
            setContentView(R.layout.dialog_weight) //setting view
            show()
        }

        val savedLoggedInModeWeight = SavedSharedPreference.getLoggedInModeWeight(this)

        if (savedLoggedInModeWeight.isNotEmpty()) {
            weightEntered.setText(savedLoggedInModeWeight)
            if (SavedSharedPreference.getLoggedInModeWeightType(this).trim { it <= ' ' } == "lbs") {
                lbs.isChecked = true
            } else {
                kg.isChecked = true
            }
        }
        submitButton.setOnClickListener {

            val weightEnteredToString = weightEntered.text.toString()
            val selectedId = weightType.checkedRadioButtonId
            SavedSharedPreference.setLoggedInModeWeight(this, weightEnteredToString)
            SavedSharedPreference.setLoggedInModeWeightType(this, (dialog.findViewById<View>(selectedId) as RadioButton).text.toString())
            dialog.dismiss()
        }
    }
}