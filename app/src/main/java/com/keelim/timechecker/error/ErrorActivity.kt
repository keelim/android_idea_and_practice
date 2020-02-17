package com.keelim.timechecker.error

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.keelim.timechecker.R
import kotlinx.android.synthetic.main.activity_error.*


class ErrorActivity : AppCompatActivity() {



    private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>(EXTRA_INTENT) }
    private val errorText by lazy { intent.getStringExtra(EXTRA_ERROR_TEXT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_error)

        tv_error_log.text = errorText

        btn_reload.setOnClickListener {
            startActivity(lastActivityIntent)
            finish()
        }

        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_INTENT = "EXTRA_INTENT"
        const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"
    }
}