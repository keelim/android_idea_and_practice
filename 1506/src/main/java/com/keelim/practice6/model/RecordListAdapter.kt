package com.keelim.practice6.model

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.keelim.practice6.R
import com.keelim.practice6.task.EachRecordDelete
import com.keelim.practice6.view.LoginMainActivity
import com.keelim.practice6.view.customs.CustomConfirmDialog
import org.json.JSONObject

class RecordListAdapter(private val context: Context, private val recordList: List<Record>) :
    BaseAdapter() {

    private var userId: String? = null
    private var datetimeS: String? = null
    override fun getCount(): Int {
        return recordList.size
    }

    override fun getItem(i: Int): Any //i는 position
    {
        return recordList[i]
    }

    override fun getItemId(i: Int): Long //i는 position
    {
        return i.toLong()
    }

    override fun getView(
        i: Int,
        convertView: View,
        parent: ViewGroup
    ): View //i는 position
    { // 하나의 View로 만들어 줄 수 있도록 한다. ('R.layout.이름'으로 배달)
        val v = View.inflate(context, R.layout.login_logged_in_record_item, null)
        val pedometer = v.findViewById<View>(R.id.pedometerRecord) as TextView
        val distance = v.findViewById<View>(R.id.distanceRecord) as TextView
        val calorie = v.findViewById<View>(R.id.calorieRecord) as TextView
        val time = v.findViewById<View>(R.id.timeRecord) as TextView
        val speed = v.findViewById<View>(R.id.speedRecord) as TextView
        val date = v.findViewById<View>(R.id.dateRecord) as TextView
        val serialNum = v.findViewById<View>(R.id.serialNum) as TextView
        val progress = v.findViewById<View>(R.id.progress) as TextView
        val datetime = v.findViewById<View>(R.id.dateTimeRecord) as TextView
        val del =
            v.findViewById<View>(R.id.deleteEachRec) as Button
        // 현재 리스트에 있는 값으로 넣어줄 수 있도록 한다.
        pedometer.text = recordList[i].pedometer
        distance.text = recordList[i].distance
        calorie.text = recordList[i].calorie
        time.text = recordList[i].time
        speed.text = recordList[i].speed
        date.text = recordList[i].date
        datetime.text = recordList[i].datetime
        datetimeS = datetime.text.toString()

        val progressSaved = recordList[i].progress.trim { it <= ' ' }
        if (progressSaved == "skipped" || progressSaved == "") {
            progress.text = "목표를 지정하지 않았습니다."
            progress.setBackgroundColor(Color.parseColor("#c5c5c5"))
        } else {
            if (progressSaved.toInt() <= 33) { //low
                progress.setBackgroundColor(Color.parseColor("#fc8080"))
            } else if (progressSaved.toInt() > 33 && progressSaved.toInt() <= 66) { //middle
                progress.setBackgroundColor(Color.parseColor("#fcc980"))
            } else if (progressSaved.toInt() > 66) { //high
                progress.setBackgroundColor(Color.parseColor("#80fcc9"))
            }
            progress.text = "목표 달성률: " + recordList[i].progress + "%"
        }
        serialNum.setText((i + 1).toString() + ")")
        del.setOnClickListener {
            userId = LoginMainActivity.userID
            //  Toast.makeText(parent.getContext(),datetimeS+" "+userId,Toast.LENGTH_LONG).show();
            val dialog =
                Dialog(parent.context) //here, the name of the activity class that you're writing a code in, needs to be replaced
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //for title bars not to be appeared (타이틀 바 안보이게)
            dialog.setContentView(R.layout.dialog_alert) //setting view
            //getting textviews and buttons from dialog
            val dialogTitle =
                dialog.findViewById<View>(R.id.dialogTitle) as TextView
            val dialogMessage =
                dialog.findViewById<View>(R.id.dialogMessage) as TextView
            val dialogButton1 =
                dialog.findViewById<View>(R.id.dialogButton1) as Button
            val dialogButton2 =
                dialog.findViewById<View>(R.id.dialogButton2) as Button
            //You can change the texts on java code shown as below
            dialogTitle.text = " 기록 삭제 "
            dialogMessage.text = "삭제하시겠습니까?"
            dialogButton1.text = "삭제"
            dialogButton2.text = "취소"
            dialog.setCanceledOnTouchOutside(false) //dialog won't be dismissed on outside touch
            dialog.setCancelable(false) //dialog won't be dismissed on pressed back
            dialog.show() //show the dialog
            //here, I will only dismiss the dialog on clicking on buttons. You can change it to your code.
            dialogButton1.setOnClickListener {
                //your code here
                dialog.dismiss() //to dismiss the dialog
                // 정상적으로 ID 값을 입력했을 경우 중복체크 시작
                val responseListener =
                    Response.Listener<String> { response ->
                        // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                        try {
                            println("aaa>>$response")
                            val jsonResponse = JSONObject(response)
                            val success =
                                jsonResponse.getBoolean("success")
                            // 만약 삭제할 수 있다면
                            if (success) {
                                CustomConfirmDialog().showConfirmDialog(
                                    parent.context,
                                    "삭제하였습니다.",
                                    false
                                )
                                val intent =
                                    (parent.context as Activity).intent
                                intent.putExtra("userID", userId)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                (parent.context as Activity).finish()
                                (parent.context as Activity).startActivity(intent)
                            } else {
                                CustomConfirmDialog().showConfirmDialog(
                                    parent.context,
                                    "삭제를 실패하였습니다.",
                                    true
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                val recordDelete = EachRecordDelete(
                    userId,
                    datetimeS,
                    responseListener
                ) // + ""를 붙이면 문자열 형태로 바꿈
                val queue = Volley.newRequestQueue(parent.context)
                queue.add(recordDelete)
            }
            dialogButton2.setOnClickListener {
                //your code here
                dialog.dismiss() //to dismiss the dialog
            }
        }
        v.tag = recordList[i].userId
        return v
    }

}