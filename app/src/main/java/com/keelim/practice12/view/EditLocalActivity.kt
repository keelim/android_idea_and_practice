package com.keelim.practice12.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice12.R
import com.keelim.practice12.model.adapter.MyCursorAdapter_local
import com.keelim.practice12.model.room.DBManagerHandler
import com.keelim.practice12.view.EditLocalActivity

class EditLocalActivity : AppCompatActivity(), View.OnClickListener {
    var listview: ListView? = null
    var handler: DBManagerHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_local)
        val add = findViewById<View>(R.id.add) as Button
        handler = DBManagerHandler(applicationContext)
        add.setOnClickListener(this)
        listview = findViewById<View>(R.id.listview) as ListView
        val cursor = handler!!.select_quick("quick")
        val init = findViewById<View>(R.id.init_add) as RelativeLayout
        val desti = findViewById<View>(R.id.desti) as LinearLayout
        val picture_receiver_button = findViewById<View>(R.id.picture_receiver_button) as Button
        picture_receiver_button.setOnClickListener(this)
        //Button add_text_button=(Button)findViewById(R.id.add_text_button);
//add_text_button.setOnClickListener(this);
// INIT VIEW
// LinearLayout addd=(LinearLayout)findViewById(R.id.addd);
        val current_receiver_cnt = handler!!.getCountByTableName("quick")
        if (current_receiver_cnt == 0) {
            init.visibility = RelativeLayout.VISIBLE
            desti.visibility = LinearLayout.GONE
        } else  // NOT INIT
        {
            init.visibility = RelativeLayout.GONE
            desti.visibility = LinearLayout.VISIBLE
            val adapter = MyCursorAdapter_local(applicationContext, cursor)
            listview!!.adapter = adapter
            // adapter.requery();
//adapter.swapCursor(cursor);
            adapter.cursor.requery()
            adapter.notifyDataSetChanged()
            //    listview.setAdapter(tagCursorAdapter);
//Cursor cursor_update = handler.select_quick("quick");
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add, R.id.add_text_button, R.id.picture_receiver_button -> {
                val intentSubActivity = Intent(this@EditLocalActivity, SetLocalActivity::class.java)
                startActivity(intentSubActivity)
            }
        }
    }
}