package com.keelim.practice4.view.cutoms

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.keelim.practice4.R

/**
 * Created by MinJae on 2015-10-28.
 */
class CustomDialog(
    context: Context?,
    private val mLeftClickListener: View.OnClickListener?,
    private val mRightClickListener: View.OnClickListener?
) : Dialog(context!!, android.R.style.Theme_Translucent_NoTitleBar) {
    private var textView: TextView? = null
    private var l_button: ImageView? = null
    private var r_button: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.8f
        window!!.attributes = lpWindow
        setContentView(R.layout.custom_dialog)
        textView = findViewById<View>(R.id.textView) as TextView
        l_button = findViewById<View>(R.id.left_button) as ImageView
        r_button = findViewById<View>(R.id.right_button) as ImageView
        textView!!.setBackgroundResource(R.drawable.graybox)
        if (mLeftClickListener != null && mRightClickListener != null) {
            l_button!!.setOnClickListener(mLeftClickListener)
            r_button!!.setOnClickListener(mRightClickListener)
        } else if (mLeftClickListener != null && mRightClickListener == null) {
            l_button!!.setOnClickListener(mLeftClickListener)
        } else {
        }
    }

}