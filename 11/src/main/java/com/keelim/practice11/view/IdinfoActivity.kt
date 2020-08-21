package com.keelim.practice11.view

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import com.keelim.practice11.view.IdinfoActivity
import java.io.IOException

class IdinfoActivity : AppCompatActivity() {
    private var mResumed = false
    private var mWriteMode = false
    var mNfcAdapter: NfcAdapter? = null
    var mNote: EditText? = null
    var mNote2: EditText? = null
    var mNote3: EditText? = null
    var mNote4: EditText? = null
    var mNote5: EditText? = null
    var mNfcPendingIntent: PendingIntent? = null
    var mWriteTagFilters: Array<IntentFilter>
    var mNdefExchangeFilters: Array<IntentFilter>
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        setContentView(R.layout.activity_idinfo)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        findViewById<View>(R.id.savebtn).setOnClickListener(mTagWriter)
        mNote = findViewById(R.id.editName)
        mNote.addTextChangedListener(mTextWatcher)
        mNote2 = findViewById(R.id.editHomephone)
        mNote2.addTextChangedListener(mTextWatcher)
        mNote3 = findViewById(R.id.editCellphoneinfo)
        mNote3.addTextChangedListener(mTextWatcher)
        mNote4 = findViewById(R.id.editAddress)
        mNote4.addTextChangedListener(mTextWatcher)
        mNote5 = findViewById(R.id.editEmail)
        mNote5.addTextChangedListener(mTextWatcher)
        // Handle all of our received NFC intents in this activity.
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        // Intent filters for reading a note from a tag or exchanging over p2p.
        val ndefDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndefDetected.addDataType("d")
        } catch (e: MalformedMimeTypeException) {
        }
        mNdefExchangeFilters = arrayOf(ndefDetected)
        // Intent filters for writing to a tag
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        mWriteTagFilters = arrayOf(tagDetected)
    }

    override fun onResume() {
        super.onResume()
        mResumed = true
        // Sticky notes received from Android
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val messages = getNdefMessages(intent)
            val payload = messages!![0]!!.records[0].payload
            setNoteBody(String(payload))
            intent = Intent() // Consume this intent.
        }
        enableNdefExchangeMode()
    }

    override fun onPause() {
        super.onPause()
        mResumed = false
        mNfcAdapter!!.disableForegroundNdefPush(this)
    }

    override fun onNewIntent(intent: Intent) { // NDEF exchange mode
        super.onNewIntent(intent)
        if (!mWriteMode && NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val msgs = getNdefMessages(intent)
            promptForContent(msgs!![0]!!)
        }
        // Tag writing mode
        if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val detectedTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            writeTag(noteAsNdef, detectedTag)
        }
    }

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
        override fun afterTextChanged(arg0: Editable) {
            if (mResumed) {
                mNfcAdapter!!.enableForegroundNdefPush(this@IdinfoActivity, noteAsNdef)
            }
        }
    }
    private val mTagWriter = View.OnClickListener { arg0: View? ->
        // Write to a tag for as long as the dialog is shown.
        disableNdefExchangeMode()
        enableTagWriteMode()
        val inflater = layoutInflater
        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        val layout = inflater.inflate(R.layout.writetag, findViewById(R.id.writepopup))
        val aDialog = AlertDialog.Builder(this@IdinfoActivity)
        //aDialog.setTitle("태그를 터치하세요"); //타이틀바 제목
        aDialog.setView(layout) //dialog.xml 파일을 뷰로 셋팅
        aDialog.setOnCancelListener { dialog: DialogInterface? ->
            disableTagWriteMode()
            enableNdefExchangeMode()
        }
        ///그냥 닫기버튼을 위한 부분
        aDialog.setNegativeButton("닫기") { dialog: DialogInterface?, which: Int -> }
        //팝업창 생성
        val ad = aDialog.create()
        ad.show() //보여줌!
    }

    private fun promptForContent(msg: NdefMessage) {
        AlertDialog.Builder(this).setTitle("Replace current content?")
                .setPositiveButton("Yes") { arg0: DialogInterface?, arg1: Int ->
                    val body = String(msg.records[0].payload)
                    setNoteBody(body)
                }
                .setNegativeButton("No") { arg0: DialogInterface?, arg1: Int -> }.show()
    }

    private fun setNoteBody(body: String) {
        val text = mNote!!.text
        text.clear()
        text.append(body)
        val text2 = mNote2!!.text
        text2.clear()
        text2.append(body)
        val text3 = mNote3!!.text
        text3.clear()
        text3.append(body)
        val text4 = mNote4!!.text
        text4.clear()
        text4.append(body)
        val text5 = mNote5!!.text
        text5.clear()
        text5.append(body)
    }

    private val noteAsNdef: NdefMessage
        private get() {
            val textBytes = mNote!!.text.toString().toByteArray()
            val textBytes2 = mNote2!!.text.toString().toByteArray()
            val textBytes3 = mNote3!!.text.toString().toByteArray()
            val textBytes4 = mNote4!!.text.toString().toByteArray()
            val textBytes5 = mNote5!!.text.toString().toByteArray()
            val textRecord = NdefRecord(NdefRecord.TNF_ABSOLUTE_URI, textBytes, byteArrayOf(), textBytes)
            val textRecord2 = NdefRecord(NdefRecord.TNF_ABSOLUTE_URI, textBytes2, byteArrayOf(), textBytes2)
            val textRecord3 = NdefRecord(NdefRecord.TNF_ABSOLUTE_URI, textBytes3, byteArrayOf(), textBytes3)
            val textRecord4 = NdefRecord(NdefRecord.TNF_ABSOLUTE_URI, textBytes4, byteArrayOf(), textBytes4)
            val textRecord5 = NdefRecord(NdefRecord.TNF_ABSOLUTE_URI, textBytes5, byteArrayOf(), textBytes5)
            return NdefMessage(arrayOf(
                    textRecord, textRecord2, textRecord3, textRecord4, textRecord5
            ))
        }

    fun getNdefMessages(intent: Intent): Array<NdefMessage?>? { // Parse the intent
        var msgs: Array<NdefMessage?>? = null
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMsgs != null) {
                msgs = arrayOfNulls(rawMsgs.size)
                for (i in rawMsgs.indices) {
                    msgs[i] = rawMsgs[i] as NdefMessage
                }
            } else { // Unknown tag type
                val empty = byteArrayOf()
                val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty)
                val msg = NdefMessage(arrayOf(
                        record
                ))
                msgs = arrayOf(
                        msg
                )
            }
        } else {
            Log.d(TAG, "Unknown intent.")
            finish()
        }
        return msgs
    }

    private fun enableNdefExchangeMode() {
        mNfcAdapter!!.enableForegroundNdefPush(this@IdinfoActivity, noteAsNdef)
        mNfcAdapter!!.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null)
    }

    private fun disableNdefExchangeMode() {
        mNfcAdapter!!.disableForegroundNdefPush(this)
        mNfcAdapter!!.disableForegroundDispatch(this)
    }

    private fun enableTagWriteMode() {
        mWriteMode = true
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        mWriteTagFilters = arrayOf(
                tagDetected
        )
        mNfcAdapter!!.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null)
    }

    private fun disableTagWriteMode() {
        mWriteMode = false
        mNfcAdapter!!.disableForegroundDispatch(this)
    }

    fun writeTag(message: NdefMessage, tag: Tag?): Boolean {
        val size = message.toByteArray().size
        try {
            val ndef = Ndef.get(tag)
            return if (ndef != null) {
                ndef.connect()
                if (!ndef.isWritable) {
                    toast("Read-Only 태그")
                    return false
                }
                if (ndef.maxSize < size) {
                    toast("최대 글자 바이트 " + ndef.maxSize + " 인데 메세지는 " + size
                            + " 입니다.")
                    return false
                }
                ndef.writeNdefMessage(message)
                toast("태그 완료")
                finish()
                true
            } else {
                val format = NdefFormatable.get(tag)
                if (format != null) {
                    try {
                        format.connect()
                        format.format(message)
                        toast("태그 저장 완료")
                        true
                    } catch (e: IOException) {
                        toast("태그 포맷 실패")
                        false
                    }
                } else {
                    toast("NDEF 호환 안됩니다.")
                    false
                }
            }
        } catch (e: Exception) {
            toast("실패")
        }
        return false
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "S"
    }
}