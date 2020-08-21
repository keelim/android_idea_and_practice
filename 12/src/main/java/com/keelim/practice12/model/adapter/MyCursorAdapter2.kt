package com.keelim.practice12.model.adapter

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.keelim.practice12.R

class MyCursorAdapter2(context: Context, c: Cursor?) : CursorAdapter(context, c) {
    //DBManagerHandler handler;
//Context context;
    var line: String? = null
    var station: String? = null
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val tLine = view.findViewById<View>(R.id.find_line) as TextView
        val tStation = view.findViewById<View>(R.id.find_station) as TextView
        // TextView tGu = (TextView)view.findViewById(R.id.home_ward_tv);
//TextView tPhone= (TextView)view.findViewById(R.id.phone);
//Button call = (Button)view.findViewById(R.id.call_home);
        line = cursor.getString(cursor.getColumnIndex("line"))
        station = cursor.getString(cursor.getColumnIndex("station"))
        // final Context context1=context;
        Log.d("스트링 확인", "$line, $station")
        tLine.text = line
        tStation.text = station
        //tPhone.setText(phone);
    }

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.listview_item, parent, false)
    }

    //  String gu;
    init {
        Log.d("스트링 확인", context.toString())
        //  this.context=context;
    }
}