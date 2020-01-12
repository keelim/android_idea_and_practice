package com.keelim.timechecker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.keelim.timechecker.MainActivity
import com.keelim.timechecker.R

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            Toast.makeText(activity, "기능 준비 중입니다. 잠시만 기다려 주세요", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).replaceFragment(CountUpFragment.newInstance()) //fragmnet 화면 전환
        }

    }
}
