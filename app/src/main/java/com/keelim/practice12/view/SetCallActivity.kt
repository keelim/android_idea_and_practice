package com.keelim.practice12.view

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice12.R
import com.keelim.practice12.model.adapter.ReceiverCursorAdapter
import com.keelim.practice12.model.room.DBManagerHandler

class SetCallActivity : AppCompatActivity() {
    var smsNumber: EditText? = null
    var Phone_list: Button? = null
    var number: String? = null
    var name: String? = null
    var dir = Environment.getExternalStorageDirectory()
    var filePath = dir.absolutePath + "/number.txt"
    var handler: DBManagerHandler? = null
    var receiverListView: ListView? = null
    var current_receiver_cnt = 0
    var receiver_tv: TextView? = null
    var init: RelativeLayout? = null
    var Phone_list2: Button? = null
    var delete: RelativeLayout? = null
    var NotInitAdd: Button? = null
    var receiverList: RelativeLayout? = null
    var delete_test //test_delete
            : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_call)
        handler = DBManagerHandler(applicationContext)
        Phone_list = findViewById<View>(R.id.picture_receiver_button) as Button
        Phone_list!!.setOnClickListener(mClickListener)
        Phone_list2 = findViewById<View>(R.id.add_text_button) as Button
        Phone_list2!!.setOnClickListener(mClickListener)
        //not_init_add_button
        NotInitAdd = findViewById<View>(R.id.not_init_add_button) as Button
        NotInitAdd!!.setOnClickListener(mClickListener)
        //receiver_tv=(TextView) findViewById(R.id.receiver_cnt);
        current_receiver_cnt = handler!!.getCountByTableName("MAN")
        init = findViewById<View>(R.id.init_add) as RelativeLayout
        delete = findViewById<View>(R.id.delete_receiver_layout) as RelativeLayout
        receiverList = findViewById<View>(R.id.receiver_list_layout) as RelativeLayout
        receiverListView = findViewById<View>(R.id.receiver_list_lv) as ListView
        // INIT VIEW
        if (current_receiver_cnt == 0) {
            init!!.visibility = RelativeLayout.VISIBLE
            delete!!.visibility = RelativeLayout.GONE
            receiverList!!.visibility = RelativeLayout.GONE
        } else  // NOT INIT
        {
            init!!.visibility = RelativeLayout.GONE
            delete!!.visibility = RelativeLayout.VISIBLE
            receiverList!!.visibility = RelativeLayout.VISIBLE
            updateReceiverList()
        }
    } //VISIBLE

    var mClickListener = View.OnClickListener { v: View ->
        when (v.id) {
            R.id.picture_receiver_button, R.id.add_text_button, R.id.not_init_add_button -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                startActivityForResult(intent, 0)
            }
        }
    }

    fun updateReceiverList() {
        val cursor = handler!!.select_quick("MAN")
        val rca = ReceiverCursorAdapter(this, cursor)
        receiverListView!!.adapter = rca
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            val cursor = contentResolver.query(data.data!!, arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER), null, null, null)
            cursor!!.moveToFirst()
            name = cursor.getString(0) //0은 이름을 얻어옵니다.
            number = cursor.getString(1) //1은 번호를 받아옵니다.
            handler!!.insert3("MAN", name, number)
            updateReceiverList()
            cursor.close()
        }
        super.onActivityResult(requestCode, resultCode, data)
        //지정인번호를 계속 저장해둬야 하므로 DB대신 txt파일에 저장
//
//        try {
//            OutputStream out = null;
//            out = new FileOutputStream(filePath);
//            out.write(number.getBytes());
//            Toast.makeText(this, "지정인이 등록되었습니다", Toast.LENGTH_SHORT).show();
//
//
//            out.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//
//        }
    }

    fun sendSMS(v: View?) {
        val phone_cursor = handler!!.select_quick("man")
        //String smsNum = smsNumber.getText().toString();
//String smsText = smsTextContext.getText().toString();
        val smsText = "000씨가 0역근처에서 위험에 처해있습니다!! "
        while (phone_cursor.moveToNext()) {
            val smsNum = phone_cursor.getString(phone_cursor
                    .getColumnIndex("name"))
            Log.d("test", smsNum)
            if (smsNum.length > 0 && smsText.length > 0) {
                sendSMS(smsNum, smsText)
            } else {
                Toast.makeText(this, "지정인이 없습니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendSMS(smsNumber: String?, smsText: String?) {
        val sentIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT_ACTION"), 0)
        val deliveredIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_DELIVERED_ACTION"), 0)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // 전송 성공
                        Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE ->  // 전송 실패
                        Toast.makeText(context, "전송 실패", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE ->  // 서비스 지역 아님
                        Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_RADIO_OFF ->  // 무선 꺼짐
                        Toast.makeText(context, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NULL_PDU ->  // PDU 실패
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show()
                }
            }
        }, IntentFilter("SMS_SENT_ACTION"))
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // 도착 완료
                        Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show()
                    Activity.RESULT_CANCELED ->  // 도착 안됨
                        Toast.makeText(context, "SMS 도착 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }, IntentFilter("SMS_DELIVERED_ACTION"))
        val mSmsManager = SmsManager.getDefault()
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent)
    }
}