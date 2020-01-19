package learn.kotlin.com.androidopencv

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.keelim.practice_opencv.R
import kotlinx.android.synthetic.main.activity_image.*
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class ImageActivity : AppCompatActivity() {
    private lateinit var mInputImage: Bitmap
    private lateinit var mOriginalImage: Bitmap


    private var mIsOpenCVReady = false

    private external fun detectEdgeJNI(inputImage: Long, outputImage: Long, th1: Int, th2: Int)

    companion object {
        private const val TAG = "AndroidOpenCv"
        private const val REQ_CODE_SELECT_IMAGE = 100
        // permission
        const val PERMISSIONS_REQUEST_CODE = 1000

        init {
            System.loadLibrary("opencv_java4")
            System.loadLibrary("native-lib")
        }
    }

    fun detectEdge() {
        val src = Mat()
        Utils.bitmapToMat(mInputImage, src)
        val edge = Mat()
        Imgproc.Canny(src, edge, 50.0, 150.0)
        Utils.matToBitmap(edge, mInputImage)
        src.release()
        edge.release()
        origin_iv.setImageBitmap(mInputImage)
    }

    fun detectEdgeUsingJNI() {
        if (!mIsOpenCVReady) {
            return
        }
        val src = Mat()
        Utils.bitmapToMat(mInputImage, src)
        origin_iv.setImageBitmap(mOriginalImage)
        val edge = Mat()
        detectEdgeJNI(src.nativeObjAddr, edge.nativeObjAddr, 50, 150)
        Utils.matToBitmap(edge, mInputImage)
        edge_iv.setImageBitmap(mInputImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        if (!hasPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            mIsOpenCVReady = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val path = getImagePathFromURI(data!!.data)
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 4
                    mOriginalImage = BitmapFactory.decodeFile(path, options)
                    mInputImage = BitmapFactory.decodeFile(path, options)
                    detectEdgeUsingJNI()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        mInputImage.recycle()
    }

    fun onButtonClicked(view: View?) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.run {
            this.type = MediaStore.Images.Media.CONTENT_TYPE
            this.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }

    private var PERMISSIONS = arrayOf("android.permission.READ_EXTERNAL_STORAGE")
    private fun hasPermissions(permissions: Array<String>): Boolean {
        var result: Int
        for (perms in permissions) {
            result = ContextCompat.checkSelfPermission(this, perms)
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    private fun getImagePathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri!!, proj, null, null, null)
        return if (cursor == null) {
            contentUri.path
        } else {
            val idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val imgPath = cursor.getString(idx)
            cursor.close()
            imgPath
        }
    }

    // permission
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> if (grantResults.isNotEmpty()) {
                val cameraPermissionAccepted = (grantResults[0]
                        == PackageManager.PERMISSION_GRANTED)
                if (!cameraPermissionAccepted) showDialogForPermission("실행을 위해 권한 허가가 필요합니다.")
            }
        }
    }


    private fun showDialogForPermission(msg: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@ImageActivity)
            .setTitle("알림")
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("예") { dialog, id ->
                requestPermissions(
                    PERMISSIONS, PERMISSIONS_REQUEST_CODE
                )
            }
            .setNegativeButton("아니오") { _, _ -> finish() }
        builder.create().show()
    }


}