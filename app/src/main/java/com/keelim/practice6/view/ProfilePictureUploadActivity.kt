package com.keelim.practice6.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Base64
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import kotlinx.android.synthetic.main.activity_profile_picture_upload.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import javax.net.ssl.HttpsURLConnection

class ProfilePictureUploadActivity : AppCompatActivity() {

    var FixBitmap: Bitmap? = null
    var ImageTag = "image_tag"
    var ImageName = "image_data"
    var ServerUploadPath = "hellouploadProfileImage.php"
    var progressDialog: ProgressDialog? = null
    var byteArrayOutputStream: ByteArrayOutputStream? = null
    lateinit var byteArray: ByteArray
    var ConvertImage: String? = null
    private var userId: String? = null
    var httpURLConnection: HttpURLConnection? = null
    var url: URL? = null
    var outputStream: OutputStream? = null
    var bufferedWriter: BufferedWriter? = null
    var RC = 0
    var bufferedReader: BufferedReader? = null
    var stringBuilder: StringBuilder? = null
    var check = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture_upload)
        supportActionBar!!.title =
            Html.fromHtml("<font color='#ffffff'>" + "프로필 사진 업로드" + "</font>")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        select!!.isClickable = true
        userId = LoginMainActivity.userID.trim { it <= ' ' }
        byteArrayOutputStream = ByteArrayOutputStream()

        select!!.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1)
        }
        post!!.setOnClickListener {
            UploadImageToServer()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
            Intent(this, ProfilePictureActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish() // close this activity and return to preview activity (if there is any)
        Intent(this, ProfilePictureActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(this)
        }
    }

    override fun onActivityResult(ResultCode: Int, RequestCode: Int, I: Intent?) {
        super.onActivityResult(ResultCode, RequestCode, I)
        if (ResultCode == 1 && RequestCode == Activity.RESULT_OK && I != null && I.data != null) {
            val uri = I.data
            FixBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            selectedPic!!.setImageBitmap(FixBitmap)
        }
    }

    fun UploadImageToServer() {
        if (FixBitmap == null) {
            Toast.makeText(this, "사진을 선택해주세요.", Toast.LENGTH_LONG).show()
        } else {
            FixBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            byteArray = byteArrayOutputStream!!.toByteArray()
            ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT)
            class AsyncTaskUploadClass : AsyncTask<Void?, Void?, String?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                    progressDialog = ProgressDialog.show(
                        this@ProfilePictureUploadActivity, "업로드중", "잠시 기다려주세요.", false, false)
                }

                override fun onPostExecute(string1: String?) {
                    super.onPostExecute(string1)
                    progressDialog!!.dismiss()
                    Toast.makeText(this@ProfilePictureUploadActivity, string1, Toast.LENGTH_LONG).show()
                    Intent(this@ProfilePictureUploadActivity, ProfilePictureActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(this)
                    }
                    finish()
                }

                override fun doInBackground(vararg params: Void?): String {
                    val imageProcessClass = ImageProcessClass()
                    val HashMapParams = HashMap< String, String?>()
                    HashMapParams[ImageTag] = userId
                    HashMapParams[ImageName] = ConvertImage
                    return imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams)
                }
            }

            AsyncTaskUploadClass().apply {
                execute()
            }
        }
        // System.out.println("bitmap>>"+FixBitmap);
    }

    inner class ImageProcessClass {
        fun ImageHttpRequest(requestURL: String?, PData: HashMap<String, String?>): String {
            var stringBuilder = StringBuilder()
            try {
                url = URL(requestURL)
                httpURLConnection = url!!.openConnection() as HttpURLConnection
                httpURLConnection!!.readTimeout = 20000
                httpURLConnection!!.connectTimeout = 20000
                httpURLConnection!!.requestMethod = "POST"
                httpURLConnection!!.doInput = true
                httpURLConnection!!.doOutput = true
                outputStream = httpURLConnection!!.outputStream
                bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
                bufferedWriter!!.write(bufferedWriterDataFN(PData))
                bufferedWriter!!.flush()
                bufferedWriter!!.close()
                outputStream!!.close()
                RC = httpURLConnection!!.responseCode
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReader =
                        BufferedReader(InputStreamReader(httpURLConnection!!.inputStream))
                    stringBuilder = StringBuilder()
                    var RC2: String?
                    while (bufferedReader!!.readLine().also { RC2 = it } != null) {
                        stringBuilder.append(RC2)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }


        private fun bufferedWriterDataFN(HashMapParams: HashMap<String, String?>): String {
            stringBuilder = StringBuilder()
            for ((key, value) in HashMapParams) {
                if (check) check = false else stringBuilder!!.append("&")
                stringBuilder!!.append(URLEncoder.encode(key, "UTF-8"))
                stringBuilder!!.append("=")
                stringBuilder!!.append(URLEncoder.encode(value, "UTF-8"))
            }
            return stringBuilder.toString()
        }
    }
}