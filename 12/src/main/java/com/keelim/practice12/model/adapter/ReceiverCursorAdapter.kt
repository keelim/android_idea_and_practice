package com.keelim.practice12.model.adapter

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.TextView
import com.keelim.practice12.R
import com.keelim.practice12.model.room.DBManagerHandler

class ReceiverCursorAdapter(context: Context, c: Cursor?) : CursorAdapter(context, c) {
    var handler: DBManagerHandler
    //Context context
    var tvName_str: String? = null
    var cnt = 0
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        cnt++
        val tvName = view.findViewById<View>(R.id.receiver_item_name) as TextView
        val tvNumber = view.findViewById<View>(R.id.receiver_item_number) as TextView
        val btDelete = view.findViewById<View>(R.id.receiver_delete_button) as Button
        tvName_str = cursor.getString(cursor.getColumnIndex("name"))
        val tvNumber_str = cursor.getString(cursor.getColumnIndex("number"))
        // final Context context1=context;
        Log.d("스트링 확인", "$tvName_str, $tvNumber_str")
        btDelete.setOnClickListener { view1: View? -> handler.deleteByTableName2("MAN", "number", tvNumber_str) }
        tvName.text = tvName_str
        tvNumber.text = tvNumber_str
    }

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.receiver_item, parent, false)
    }

    //int
//  String gu;
    init {
        Log.d("스트링 확인", context.toString())
        handler = DBManagerHandler(context)
        //  this.context=context;
    }
}