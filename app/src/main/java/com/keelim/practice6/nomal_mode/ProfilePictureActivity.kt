package com.keelim.practice6.nomal_mode

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.keelim.practice6.R
import com.keelim.practice6.model.ImageData
import kotlinx.android.synthetic.main.dialog_alert.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ProfilePictureActivity() : AppCompatActivity() {
    private var imageData: ImageData? = null
    private var propic: ImageView? = null
    private var userId: String? = null
    private var upload: Button? = null
    private var delete: Button? = null
    private var filename: String? = null
    private var propictitle: TextView? = null
    var font_one: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture)
        font_one = Typeface.createFromAsset(assets, "fonts/font_one.ttf")
        supportActionBar!!.title =
            (Html.fromHtml("<font color='#ffffff'>" + "프로필 사진 설정" + "</font>"))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        userId = LoginMainActivity.userID.trim { it <= ' ' }
        propictitle!!.text = "$userId's profile picture"
        propictitle!!.typeface = font_one
        println(userId)
        BackgroundTask().execute()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    internal inner class BackgroundTask : AsyncTask<Void?, Void?, String?>() {
        // (로딩창 띄우기 작업 3/1) 로딩창을 띄우기 위해 선언해준다.
        var dialog = ProgressDialog(this@ProfilePictureActivity)
        var target: String? = null

        override fun onPreExecute() {
            target =
                "http://ggavi2000.cafe24.com/getImageFromServer.php?userId=$userId" //해당 웹 서버에 접속
            // (로딩창 띄우기 작업 3/2)
            dialog.setMessage("로딩중")
            dialog.show()
        }

        override fun doInBackground(vararg params: Void?): String? {
            val url = URL(target)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            // 넘어오는 결과값을 그대로 저장
            val inputStream = httpURLConnection.inputStream
            // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
            var temp: String
            val stringBuilder = StringBuilder()
            // null 값이 아닐 때까지 계속 반복해서 읽어온다.
            while ((bufferedReader.readLine().also {
                    temp = it
                }) != null) { // temp에 한줄씩 추가하면서 넣어줌
                stringBuilder.append(temp + "\n")
            }
            // 끝난 뒤 닫기
            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect() //인터넷도 끊어줌
            println(">>" + stringBuilder.toString().trim { it <= ' ' })
            return stringBuilder.toString().trim { it <= ' ' }
                    }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate()
        }

        //해당 결과를 처리할 수 있는 onPostExecute()
        public override fun onPostExecute(result: String?) {
            try { // 해당 결과(result) 응답 부분을 처리
                val pos = result!!.indexOf(":")
                var test = result.substring(pos + 1)
                test = test.trim { it <= ' ' }
                if (test[0] == '[' && test[1] == ']') {
                    println("null")
                    upload!!.visibility = View.VISIBLE
                    delete!!.visibility = View.GONE
                    upload!!.setOnClickListener(View.OnClickListener {
                        Intent(this@ProfilePictureActivity, ProfilePictureUploadActivity::class.java).apply {
                            putExtra("userId", userId)
                            startActivity(this)
                        }
                        finish()
                    })
                } else {
                    upload!!.visibility = View.GONE
                    delete!!.visibility = View.VISIBLE

                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("response") //아까 변수 이름


                    val userId: String
                    val image_data: String
                    // 현재 배열의 원소값을 저장
                    val `object`: JSONObject
                    `object` = jsonArray.getJSONObject(0)

                    userId = `object`.getString("image_tag")
                    image_data = `object`.getString("image_data")
                    println("check>>$userId+$image_data")
                    filename = image_data
                    val target = "http://ggavi2000.cafe24.com/images/"
                    val index = filename!!.indexOf(target)
                    val subIndex = index + target.length
                    filename = filename!!.substring(subIndex)
                    // 하나의 공지사항에 대한 객체를 만들어줌
                    imageData =
                        ImageData(userId, image_data)
                    val path = imageData!!.image_data
                    DownloadImageTask(propic).execute(path)
                    delete!!.setOnClickListener {
                        val dialog = Dialog(this@ProfilePictureActivity) //here, the name of the activity class that you're writing a code in, needs to be replaced
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //for title bars not to be appeared (타이틀 바 안보이게)
                        dialog.setContentView(R.layout.dialog_alert) //setting view

                        dialogTitle.text = " 프로필 사진 삭제 "
                        dialogMessage.text = " 프로필 사진을 삭제하시겠습니까? "
                        dialogButton1.text = "삭제"
                        dialogButton2.text = "취소"
                        dialog.setCanceledOnTouchOutside(false)
                        dialog.setCancelable(false)
                        dialog.show()

                        dialogButton1.setOnClickListener {

                            dialog.dismiss() //to dismiss the dialog

                            val responseListener: Response.Listener<String> = Response.Listener { response ->
                                // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                                try {
                                    println("aaa>>$response")
                                    val jsonResponse = JSONObject(response)
                                    val success = jsonResponse.getBoolean("success")

                                    if (success) {
                                        CustomConfirmDialog().showConfirmDialog(this@ProfilePictureActivity, "삭제하였습니다.", false)
                                        (intent).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                            startActivity(this)
                                        }
                                        finish()
                                    } else {
                                        CustomConfirmDialog().showConfirmDialog(this@ProfilePictureActivity, "삭제를 실패하였습니다.", true)
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                            // 실질적으로 삭제할 수 있도록 생성자를 통해 객체를 만든다. (유저 ID, responseListener)
                            // 그리고 어떤 회원이 어떤 강의를 삭제한다는 데이터는 DB에 넣어야 한다.
                            val imageDelete = ImageDelete(userId, filename, responseListener) // + ""를 붙이면 문자열 형태로 바꿈
                            val queue = Volley.newRequestQueue(this@ProfilePictureActivity)
                            queue.add(imageDelete)
                        }
                        dialogButton2.setOnClickListener {
                            dialog.dismiss() //to dismiss the dialog
                        }
                    }
                }
                dialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}