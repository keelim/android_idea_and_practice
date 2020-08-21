package com.keelim.practice11.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import com.keelim.practice11.view.CallingActivity
import java.io.IOException
import java.nio.charset.StandardCharsets

class CallingActivity : AppCompatActivity() {
    var nfcAdapter: NfcAdapter? = null
    var phoneno: EditText? = null
    var callbtn: Button? = null
    var urlAddress: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calling)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        phoneno = findViewById(R.id.editName)
        callbtn = findViewById(R.id.callBtn)
        callbtn!!.setOnClickListener { view: View? ->
            val smsNumber = phoneno!!.getText().toString()
            urlAddress = smsNumber
            val inflater = layoutInflater
            //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
            val layout = inflater.inflate(R.layout.writetag, findViewById(R.id.writepopup))
            val aDialog = AlertDialog.Builder(this@CallingActivity)
            //aDialog.setTitle("태그를 터치하세요"); //타이틀바 제목
            aDialog.setView(layout) //dialog.xml 파일을 뷰로 셋팅
            ///그냥 닫기버튼을 위한 부분
            aDialog.setNegativeButton("닫기") { dialog: DialogInterface?, which: Int -> }
            //팝업창 생성
            val ad = aDialog.create()
            ad.show() //보여줌!
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onPostResume()
        enableForegroundDispatchSystem()
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatchSystem()
    }

    //Enable foreground dispatcher
    private fun enableForegroundDispatchSystem() {
        val intent = Intent(this, CallingActivity::class.java).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val intentFilters = arrayOf<IntentFilter>()
        nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }

    //disable foreground dispatcher
    private fun disableForegroundDispatchSystem() {
        nfcAdapter!!.disableForegroundDispatch(this)
    }

    public override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i("Foreground dispatch", "Discovered tag with intent: $intent")
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val uriField = urlAddress!!.toByteArray(StandardCharsets.UTF_8)
        val payload = ByteArray(uriField.size + 1)
        payload[0] = 0x05
        System.arraycopy(uriField, 0, payload, 1, uriField.size)
        val URIRecord = NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_URI, ByteArray(0), payload)
        val newMessage = NdefMessage(arrayOf(URIRecord))
        writeNdefMessageToTag(newMessage, tag)
    }

    fun writeNdefMessageToTag(message: NdefMessage, detectedTag: Tag?): Boolean {
        val size = message.toByteArray().size
        try {
            val ndef = Ndef.get(detectedTag)
            return if (ndef != null) {
                ndef.connect()
                if (!ndef.isWritable) {
                    Toast.makeText(this, "Tag is read-only.", Toast.LENGTH_SHORT).show()
                    return false
                }
                if (ndef.maxSize < size) {
                    Toast.makeText(this, "The data cannot written to tag, Tag capacity is " + ndef.maxSize + " bytes, message is "
                            + size + " bytes.", Toast.LENGTH_SHORT).show()
                    return false
                }
                ndef.writeNdefMessage(message)
                ndef.close()
                Toast.makeText(this, "저장 완료",
                        Toast.LENGTH_SHORT).show()
                finish()
                true
            } else {
                val ndefFormat = NdefFormatable.get(detectedTag)
                if (ndefFormat != null) {
                    try {
                        ndefFormat.connect()
                        ndefFormat.format(message)
                        ndefFormat.close()
                        Toast.makeText(this, "저장 완료",
                                Toast.LENGTH_SHORT).show()
                        true
                    } catch (e: IOException) {
                        Toast.makeText(this, "저장 실패",
                                Toast.LENGTH_SHORT).show()
                        false
                    }
                } else {
                    Toast.makeText(this, "NDEF is not supported",
                            Toast.LENGTH_SHORT).show()
                    false
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Write opreation is failed",
                    Toast.LENGTH_SHORT).show()
        }
        return false
    }
}