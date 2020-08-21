package com.keelim.practice12.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice12.R
import com.keelim.practice12.model.adapter.MyCursorAdapter2
import com.keelim.practice12.model.room.DBManagerHandler
import com.keelim.practice12.utils.QuoteBank
import com.keelim.practice12.view.SetLocalActivity
import java.util.*

class SetLocalActivity : AppCompatActivity(), View.OnClickListener {
    //DBManagerHandler handler;
    var name: String? = null
    var set_name: EditText? = null
    var handler: DBManagerHandler? = null
    var aDialog: AlertDialog.Builder? = null
    var ad: AlertDialog? = null
    var cancel: Button? = null
    var next: Button? = null
    var icon_home: RadioButton? = null
    var icon_office: RadioButton? = null
    var home_ok: Boolean? = null
    var find_gu_tv: AutoCompleteTextView? = null
    var mQuoteBank: QuoteBank? = null
    var call_gu: String? = null
    private var mLines: List<String>? = null
    var adapter: ArrayAdapter<String>? = null
    var listView: ListView? = null
    var set_gu: TextView? = null
    var set_gu_text: TextView? = null
    var call: Button? = null
    var icon = 0
    var station: String? = null
    var s_gu: String? = null
    var subway: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_two)
        subway = findViewById<View>(R.id.subway) as ImageView
        handler = DBManagerHandler(applicationContext)
        if (handler!!.getCountByTableName("quick") >= 3) {
            Toast.makeText(applicationContext, "목적지 수는 최대 3개만 가능합니다.", Toast.LENGTH_SHORT).show()
            finish()
        } // mContext = getApplicationContext();
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        val layout = inflater.inflate(R.layout.local_popup, findViewById<View>(R.id.localpopupid) as ViewGroup)
        aDialog = AlertDialog.Builder(this)
        //aDialog.setTitle("목적지 등록"); //타이틀바 제목
        aDialog!!.setView(layout) //dialog.xml 파일을 뷰로 셋팅
        set_name = layout.findViewById<View>(R.id.set_name) as EditText
        icon_home = layout.findViewById<View>(R.id.icon_home) as RadioButton
        icon_office = layout.findViewById<View>(R.id.icon_office) as RadioButton
        icon_home!!.setOnClickListener(this)
        icon_office!!.setOnClickListener(this)
        cancel = layout.findViewById<View>(R.id.cancel) as Button
        next = layout.findViewById<View>(R.id.next) as Button
        cancel!!.setOnClickListener(this)
        next!!.setOnClickListener(this)
        ad = aDialog!!.create()
        ad.show()
        val find = findViewById<View>(R.id.find_gu_btn_by_name) as Button
        //Log.d("station_ok",String.valueOf(station_ok));
        find_gu_tv = findViewById<View>(R.id.find_gu_ed) as AutoCompleteTextView
        mQuoteBank = QuoteBank(this)
        mLines = mQuoteBank!!.readLine(mPath)
        for (string in mLines!!) {
            val sp = string.split("/".toRegex()).toTypedArray()
            //andler.insert2("subway", sp[0], sp[1], sp[2]);
            sub_st.add(sp[1])
        }
        val arrStr: Array<String>
        arrStr = sub_st.toTypedArray()
        adapter = ArrayAdapter(applicationContext, android.R.layout.simple_dropdown_item_1line, arrStr)
        find_gu_tv!!.setAdapter<ArrayAdapter<String>>(adapter)
        // auto make text
        listView = findViewById<View>(R.id.listView) as ListView
        //Button change=(Button)findViewById(R.id.change);
//change.setOnClickListener(mPagerListener);
        call = findViewById<View>(R.id.call) as Button
        set_gu = findViewById<View>(R.id.set_gu) as TextView
        set_gu_text = findViewById<View>(R.id.set_gu_text) as TextView
        val c = applicationContext
        call!!.setOnClickListener(this)
        find.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.icon_home -> {
                home_ok = true
                icon_office!!.isActivated = false
            }
            R.id.icon_office -> home_ok = false
            R.id.cancel -> finish()
            R.id.next -> if (set_name!!.text != null) {
                name = set_name!!.text.toString()
                ad!!.dismiss()
            }
            R.id.call -> {
                icon = if (home_ok!!) 0 else 1
                handler!!.insert_quick("quick", name!!, icon, station, s_gu, call_gu)
                val intentSubActivity = Intent(this@SetLocalActivity, EditLocalActivity::class.java)
                startActivity(intentSubActivity)
            }
            R.id.find_gu_btn_by_name -> {
                station = find_gu_tv!!.text.toString()
                Log.d("station", station!!)
                var cursor = handler!!.select2("subway", station)
                //                                Boolean a=cursor.moveToNext();
//                                Log.d("yes",String.valueOf(a));
//String gu = cursor.getString(cursor.getColumnIndex("gu"));
                if (!cursor.moveToNext()) {
                    set_gu!!.text = "검색결과가 없습니다"
                } else {
                    subway!!.setBackgroundResource(R.drawable.subway)
                    s_gu = cursor.getString(cursor.getColumnIndex("gu"))
                    val cursor2 = handler!!.select("phone", s_gu)
                    cursor = handler!!.select("subway", s_gu)
                    call_gu = cursor2.getString(cursor2.getColumnIndex("number"))
                    set_gu!!.text = s_gu
                    val num_c = cursor.count
                    val gu_text = num_c.toString() + "개 역이 검색되었습니다"
                    set_gu_text!!.text = gu_text
                    call!!.setBackgroundResource(R.drawable.setlocal)
                    val myadapter = MyCursorAdapter2(applicationContext, cursor)
                    //  CustomAdapter custom = new CustomAdapter(getApplicationContext(), 0, sub_st);
                    listView!!.adapter = myadapter
                    break
                }
            }
        }
    }

    companion object {
        const val mPath = "db_subway.txt"
        var sub_st = ArrayList<String>()
    }
}