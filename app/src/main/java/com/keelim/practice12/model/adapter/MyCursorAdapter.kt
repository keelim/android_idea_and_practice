package com.keelim.practice12.model.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.TextView
import com.keelim.practice12.R
import com.keelim.practice12.model.room.DBManagerHandler

class MyCursorAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    var handler: DBManagerHandler? = null
    //Context context;
    var name: String? = null
    var station: String? = null
    var gu: String? = null
    var phone: String? = null
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val tName = view.findViewById<View>(R.id.home_tv) as TextView
        val tStation = view.findViewById<View>(R.id.home_sub_tv) as TextView
        val tGu = view.findViewById<View>(R.id.home_ward_tv) as TextView
        //TextView tPhone= (TextView)view.findViewById(R.id.phone);
        val call = view.findViewById<View>(R.id.call_home) as Button
        name = cursor.getString(cursor.getColumnIndex("name"))
        station = cursor.getString(cursor.getColumnIndex("station"))
        gu = cursor.getString(cursor.getColumnIndex("gu"))
        phone = cursor.getString(cursor.getColumnIndex("phone"))
        Log.d("스트링 확인", "$name, $station")
        tName.text = name
        tStation.text = station
        tGu.text = gu
        //tPhone.setText(phone);
        call.setOnClickListener { v: View? ->
            // Do things Here
//                startActivity(new Intent("android.intent.action.DIAL", Uri
//                        .parse("tel:" + phone + "")));
            try {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phone")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e("전화걸기", "전화걸기에 실패했습니다.", e)
            }
        }
    }

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.list_view, parent, false)
    }
}