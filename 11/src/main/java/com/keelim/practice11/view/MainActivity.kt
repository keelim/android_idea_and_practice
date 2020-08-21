package com.keelim.practice11.view

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.keelim.practice11.R
import com.keelim.practice11.view.MainActivity

class MainActivity : AppCompatActivity() {
    var idinfo: Button? = null
    var message: Button? = null
    var calling: Button? = null
    var uriconn: Button? = null
    var erase: Button? = null
    var lost_property: Button? = null
    var nfcAdapter: NfcAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        checkPermission()
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(applicationContext, "NFC를 활성", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
            finish()
        }
        idinfo = findViewById(R.id.writeID)
        idinfo.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@MainActivity, IdinfoActivity::class.java)) })
        message = findViewById(R.id.writeMessage)
        message.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@MainActivity, SendingMessageActivity::class.java)) })
        calling = findViewById(R.id.writeCall)
        calling.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@MainActivity, CallingActivity::class.java)) })
        uriconn = findViewById(R.id.writeUri)
        uriconn.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@MainActivity, UriTabActivity::class.java)) })
        erase = findViewById(R.id.erasebtn)
        erase.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@MainActivity, EraseActivity::class.java)) })
        lost_property = findViewById(R.id.lost_property)
        lost_property.setOnClickListener(View.OnClickListener { v: View? -> startActivity(Intent(this@MainActivity, LostPropertyActivity::class.java)) })
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view: View? ->
            val inflater = layoutInflater
            //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
            val layout = inflater.inflate(R.layout.information, findViewById(R.id.popupview))
            val aDialog = AlertDialog.Builder(this@MainActivity)
            aDialog.setTitle("이용안내") //타이틀바 제목
            aDialog.setView(layout) //dialog.xml 파일을 뷰로 셋팅
            //그냥 닫기버튼을 위한 부분
            aDialog.setNegativeButton("닫기") { dialog: DialogInterface?, which: Int -> }
            //팝업창 생성
            val ad = aDialog.create()
            ad.show() //보여줌!
        }
    }

    override fun onBackPressed() {
        val d = AlertDialog.Builder(this)
        d.setMessage("정말 종료하시겠습니까?")
        d.setPositiveButton("예") { dialog: DialogInterface?, which: Int ->
            // process전체 종료
            finish()
        }
        d.setNegativeButton("아니요") { dialog: DialogInterface, which: Int -> dialog.cancel() }
        d.show()
    }

    private fun checkPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS,
                Manifest.permission.NFC,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_PHONE_STATE
        ), 1)
    }
}