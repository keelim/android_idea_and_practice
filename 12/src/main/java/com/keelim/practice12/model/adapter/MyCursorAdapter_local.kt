package com.keelim.practice12.model.adapter

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import com.keelim.practice12.R
import com.keelim.practice12.model.room.DBManagerHandler

class MyCursorAdapter_local //    String name;
//    int icon;
//    String station;
//    String gu;
//    String phone;
//    int id;
(var context: Context, c: Cursor?) : CursorAdapter(context, c) {
    var handler: DBManagerHandler? = null
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        handler = DBManagerHandler(context)
        val tName = view.findViewById<View>(R.id.home_tv) as TextView
        val image = view.findViewById<View>(R.id.home_ic) as ImageView
        val tStation = view.findViewById<View>(R.id.home_sub_tv) as TextView
        val tGu = view.findViewById<View>(R.id.home_ward_tv) as TextView
        val image1 = view.findViewById<View>(R.id.home_ward) as ImageView
        val image2 = view.findViewById<View>(R.id.home_sub_img) as ImageView
        //TextView tPhone= (TextView)view.findViewById(R.id.phone);
        val call = view.findViewById<View>(R.id.call_home) as Button
        val id = cursor.getInt(cursor.getColumnIndex("_id"))
        val name = cursor.getString(cursor.getColumnIndex("name"))
        image1.setBackgroundResource(R.drawable.add)
        image2.setBackgroundResource(R.drawable.subway)
        var icon = cursor.getInt(cursor.getColumnIndex("icon"))
        if (icon == 0) icon = R.drawable.home else if (icon == 1) icon = R.drawable.office
        val station = cursor.getString(cursor.getColumnIndex("station"))
        val gu = cursor.getString(cursor.getColumnIndex("gu"))
        //fianl String phone=cursor.getString(cursor.getColumnIndex("phone"));
//final Context context1=context;
        val context1 = context
        //Log.d("스트링 확인", name + ", " + station);
        tName.text = name
        call.setBackgroundResource(R.drawable.delete)
        image.setBackgroundResource(icon)
        tStation.text = station
        tGu.text = gu
        // tGu.setText( gu );
        val oldCursor = getCursor()
        //tPhone.setText(phone);
        call.setOnClickListener { v: View? ->
            // Do things Here
            Log.d("delete", name)
            handler!!.deleteByTableName("quick", id)
        }
    }

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.edit_local_item, parent, false)
    }

}