package com.keelim.practice6.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.keelim.practice6.R
import kotlinx.android.synthetic.main.fragment_login_main.*

class LoginMainFragment : Fragment() {
    // '로그인MainActivity'에서 받아오기 위해 선언
    private lateinit var userID: String


    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)

        walk_imageButton.setOnClickListener {
            userID = activity!!.intent.extras!!.getString("userID").toString()
            Intent(activity, LoggedInWalkActivity::class.java).apply {
                putExtra("userID", userID)
                startActivity(this)
            }
            activity!!.finish()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_main, container, false)
    }

}