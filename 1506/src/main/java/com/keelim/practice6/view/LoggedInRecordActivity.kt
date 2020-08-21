package com.keelim.practice6.view

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.keelim.practice6.R
import com.keelim.practice6.model.Record
import com.keelim.practice6.model.RecordListAdapter
import com.keelim.practice6.task.RecordDelete
import com.keelim.practice6.view.LoggedInRecordActivity
import com.keelim.practice6.view.LoginMainActivity
import com.keelim.practice6.view.customs.CustomConfirmDialog
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class LoggedInRecordActivity : AppCompatActivity() {
    private var recordListView: ListView? = null
    private var adapter: RecordListAdapter? = null
    private var recordList: MutableList<Record>? =
        null
    private var noRecAlert: TextView? = null
    private var deleteAllRec: Button? = null
    private var titleForRecord: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_logged_in_record)
        recordListView =
            findViewById<View>(R.id.recordListView) as ListView
        (this@LoggedInRecordActivity as AppCompatActivity).supportActionBar
            .setTitle(Html.fromHtml("<font color='#ffffff'>" + "나의 기록 관리" + "</font>"))
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        recordList = ArrayList()
        val intent = intent
        userID = intent.extras!!.getString("userID").toString()
        noRecAlert = findViewById<View>(R.id.noRecAlert) as TextView
        deleteAllRec = findViewById<View>(R.id.deleteAllRec) as Button
        titleForRecord = findViewById<View>(R.id.titleForRecord) as TextView
        titleForRecord!!.text = "사용자 " + userID + "님의 상세 기록입니다."
        deleteAllRec!!.setOnClickListener { deleteAllRec() }
        // adapter에 해당 List를 매칭 (각각 차례대로 매칭)
        adapter = RecordListAdapter(applicationContext, recordList)
        recordListView!!.adapter = adapter
        BackgroundTask().execute()
    }

    internal inner class BackgroundTask :
        AsyncTask<Void?, Void?, String?>() {
        // (로딩창 띄우기 작업 3/1) 로딩창을 띄우기 위해 선언해준다.
        var dialog =
            ProgressDialog(this@LoggedInRecordActivity)
        var target //우리가 접속할 홈페이지 주소가 들어감
                : String? = null

        override fun onPreExecute() {
            target =
                "helloRecordList.php?userId=$userID" //해당 웹 서버에 접속
            // (로딩창 띄우기 작업 3/2)
            dialog.setMessage("로딩중")
            dialog.show()
        }

        protected override fun doInBackground(vararg voids: Void): String? {
            try { // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                val url = URL(target)
                val httpURLConnection =
                    url.openConnection() as HttpURLConnection
                // 넘어오는 결과값을 그대로 저장
                val inputStream = httpURLConnection.inputStream
                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                val bufferedReader =
                    BufferedReader(InputStreamReader(inputStream))
                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                var temp: String
                val stringBuilder = StringBuilder()
                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while (bufferedReader.readLine().also {
                        temp = it
                    } != null) { // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n")
                }
                // 끝난 뒤 닫기
                bufferedReader.close()
                inputStream.close()
                httpURLConnection.disconnect() //인터넷도 끊어줌
                return stringBuilder.toString().trim { it <= ' ' }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate()
        }

        //해당 결과를 처리할 수 있는 onPostExecute()
        public override fun onPostExecute(result: String?) {
            try { // 해당 결과(result) 응답 부분을 처리
                val jsonObject = JSONObject(result)
                // response에 각각의 공지사항 리스트가 담기게 됨
                val jsonArray = jsonObject.getJSONArray("response") //아까 변수 이름
                println("length of jsonArray>>" + jsonArray.length())
                if (jsonArray.length() == 0) {
                    noRecAlert!!.visibility = View.VISIBLE
                    deleteAllRec!!.visibility = View.GONE
                } else {
                    noRecAlert!!.visibility = View.GONE
                    deleteAllRec!!.visibility = View.VISIBLE
                }
                var count = 0
                var userId: String
                var pedometer: String
                var distance: String
                var calorie: String
                var time: String
                var speed: String
                var date: String
                var progress: String
                var datetime: String
                while (count < jsonArray.length()) { // 현재 배열의 원소값을 저장
                    val `object` = jsonArray.getJSONObject(count)
                    // 공지사항의 Content, Name, Date에 해당하는 값을 가져와라는 뜻
                    userId = `object`.getString("userId")
                    pedometer = `object`.getString("pedometer")
                    distance = `object`.getString("distance")
                    calorie = `object`.getString("calorie")
                    time = `object`.getString("time")
                    speed = `object`.getString("speed")
                    date = `object`.getString("date")
                    val pos = date.indexOf(" ")
                    date = date.substring(0, pos)
                    datetime = `object`.getString("date")
                    progress = `object`.getString("progress")
                    // 하나의 공지사항에 대한 객체를 만들어줌
                    val record =
                        Record(
                            userId,
                            pedometer,
                            distance,
                            calorie,
                            time,
                            speed,
                            date,
                            progress,
                            datetime
                        )
                    // 리스트에 추가해줌
                    recordList!!.add(record)
                    adapter!!.notifyDataSetChanged()
                    count++
                }
                // (로딩창 띄우기 작업 3/3)
// 작업이 끝나면 로딩창을 종료시킨다.
                dialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //adding the menu on tool bar (액션바에서 메뉴 추가)
// adding actions that will be done on clicking menu items
// (메뉴 아이템들을 클릭할 때 발생할 이벤트 추가)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                val intent =
                    Intent(this@LoggedInRecordActivity, LoginMainActivity::class.java)
                intent.putExtra("userID", LoginMainActivity.userID)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    override fun onBackPressed() {
        finish() // close this activity and return to preview activity (if there is any)
        val intent = Intent(this@LoggedInRecordActivity, LoginMainActivity::class.java)
        intent.putExtra("userID", LoginMainActivity.userID)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    fun deleteAllRec() {
        val dialog =
            Dialog(this@LoggedInRecordActivity) //here, the name of the activity class that you're writing a code in, needs to be replaced
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
        dialogTitle.text = " 모든 기록 삭제 "
        dialogMessage.text = "사용자 " + userID + "님의 모든 기록을 삭제하시겠습니까?"
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
                        println("response>>$response")
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        // 만약 삭제할 수 있다면
                        if (success) {
                            CustomConfirmDialog().showConfirmDialog(
                                this@LoggedInRecordActivity,
                                "삭제하였습니다.",
                                false
                            )
                            val intent = intent
                            intent.putExtra(
                                "userID",
                                userID
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            finish()
                            startActivity(intent)
                        } else {
                            CustomConfirmDialog().showConfirmDialog(
                                this@LoggedInRecordActivity,
                                "삭제를 실패하였습니다.",
                                true
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            val recordDelete = RecordDelete(
                userID,
                responseListener
            ) // + ""를 붙이면 문자열 형태로 바꿈
            val queue = Volley.newRequestQueue(this@LoggedInRecordActivity)
            queue.add(recordDelete)
        }
        dialogButton2.setOnClickListener {
            //your code here
            dialog.dismiss() //to dismiss the dialog
        }
    }

    companion object {
        private var userID = ""
    }
}