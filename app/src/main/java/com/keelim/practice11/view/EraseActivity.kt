package com.keelim.practice11.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.nfc.*
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice11.R
import java.io.IOException

class EraseActivity : AppCompatActivity() {
    var sd: String? = null
    var sp = " "
    var l = 1
    var k = 1
    var j = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_erase)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        enableNfcWrite()
    }

    override fun onPause() {
        super.onPause()
        disableNfcWrite()
    }

    private val pendingIntent: PendingIntent
        private get() = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    private fun enableNfcWrite() {
        val adapter = NfcAdapter.getDefaultAdapter(this)
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val writeTagFilters = arrayOf(tagDetected)
        adapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null)
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private fun disableNfcWrite() {
        val adapter = NfcAdapter.getDefaultAdapter(this)
        adapter.disableForegroundDispatch(this)
    }

    @SuppressLint("NewApi")
    private fun writeSomeStuffToTag(tag: Tag?) {
        val ndefTag = Ndef.get(tag)
        val stringBytes = sp.toByteArray()
        val dataToWrite = NdefRecord.createMime("text/plain", stringBytes)
        try {
            ndefTag.connect()
            ndefTag.writeNdefMessage(NdefMessage(dataToWrite))
            ndefTag.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FormatException) {
            e.printStackTrace()
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val discoveredTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(discoveredTag)
            Log.d(TAG, ndef.type)
            Log.d(TAG, if (ndef.isWritable) "true" else "false")
            try {
                ndef.connect()
                val ndefMessage = ndef.ndefMessage
                Log.d(TAG, ndefMessage.toString())
                Log.d(TAG, Integer.toString(ndef.maxSize))
                val record = ndefMessage.records[0]
                val payload = record.payload
                Log.d(TAG, String(payload))
                sd = String(payload)
                ndef.close()
            } catch (e: IOException) {
                Log.e(TAG, "IOException")
            } catch (e: FormatException) {
            }
            writeSomeStuffToTag(discoveredTag)
            toast("삭제 완료")
            finish()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val MIME_TEXT_PLAIN = "text/plain"
        const val TAG = "NfcDemo"
    }
}