package com.keelim.practice12.view

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.keelim.practice12.R
import com.keelim.practice12.model.adapter.MyCursorAdapter
import com.keelim.practice12.model.adapter.MyCursorAdapter2
import com.keelim.practice12.model.room.DBManagerHandler
import com.keelim.practice12.services.GPSTracker
import com.keelim.practice12.utils.CNM_XMLParser
import com.keelim.practice12.utils.QuoteBank
import com.keelim.practice12.utils.StationXMLParser
import com.keelim.practice12.view.MainActivity
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val m_ListView: ListView? = null
    //private ArrayAdapter<Person> m_Adapter;
    var myAdapter: MyCursorAdapter? = null
    private var mQuoteBank: QuoteBank? = null
    private var mLines: List<String>? = null
    var handler: DBManagerHandler? = null
    var fab: ImageView? = null
    private var mPager: ViewPager? = null
    var title: TextView? = null
    var db: SQLiteDatabase? = null
    private val alarm: Button? = null
    private var mp: MediaPlayer? = null
    private var am: AudioManager? = null
    var StreamType = 0
    private var adapter: ArrayAdapter<String>? = null
    private var find_gu_tv: AutoCompleteTextView? = null
    private var listView: ListView? = null
    var call_gu: String? = null
    private val cur_lat = 0.0
    private val cur_lon = 0.0
    private var gps: GPSTracker? = null
    private var mXMLParser: StationXMLParser? = null
    private var cnmXMLParser: CNM_XMLParser? = null
    var isCNM = false
    var cur_x = 0.0
    var cur_y = 0.0
    var cnm_str: String? = null
    var phone1: String? = null
    var phone2: String? = null
    var phone3: String? = null
    var aDialog: AlertDialog.Builder? = null
    var sDialog: AlertDialog.Builder? = null
    var select: AlertDialog? = null
    var station_ok = true
    var station_list: String? = null
    var photoView: ImageView? = null
    var smsNumber: EditText? = null
    var Phone_list: Button? = null
    var number: String? = null
    var photo: Long? = null
    var arrStr: Array<String>
    var dir = Environment.getExternalStorageDirectory()
    var filePath = dir.absolutePath + "/number.txt"
    var tv: TextView? = null
    var ad: AlertDialog? = null
    var flag = true
    var mContext: Context? = null
    var c: Counter? = null
    var alarm_text: TextView? = null
    var alarm_text1: TextView? = null
    var alarm_text2: TextView? = null
    var send_text = false
    var send: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLayout()
        //requestGPS();
        title = findViewById<View>(R.id.title_tv) as TextView
        addListenerOnButton()
        handler = DBManagerHandler(applicationContext)
        if (!handler!!.getCountByTableName1("phone")) {
            handler!!.insert("phone", "종로구", "0221481111")
            handler!!.insert("phone", "중구", "0233964001")
            handler!!.insert("phone", "용산구", "0221996300")
            handler!!.insert("phone", "성동구", "0222866262")
            handler!!.insert("phone", "광진구", "024501330")
            handler!!.insert("phone", "동대문구", "0221274000")
            handler!!.insert("phone", "중랑구", "0220940114")
            handler!!.insert("phone", "성북구", "029203000")
            handler!!.insert("phone", "강북구", "029016112")
            handler!!.insert("phone", "도봉구", "0220913109")
            handler!!.insert("phone", "노원구", "0221163300")
            handler!!.insert("phone", "은평구", "023518000")
            handler!!.insert("phone", "서대문구", "023301119")
            handler!!.insert("phone", "마포구", "0231538104")
            handler!!.insert("phone", "양천구", "0226203399")
            handler!!.insert("phone", "강서구", "0226001280")
            handler!!.insert("phone", "구로구", "028602330")
            handler!!.insert("phone", "금천구", "0226272414")
            handler!!.insert("phone", "영등포구", "0226704070")
            handler!!.insert("phone", "동작구", "028201040")
            handler!!.insert("phone", "관악구", "028797640")
            handler!!.insert("phone", "서초구", "0221558510")
            handler!!.insert("phone", "강남구", "0234236000")
            handler!!.insert("phone", "송파구", "0221472799")
            handler!!.insert("phone", "강동구", "0234255009")
            mQuoteBank = QuoteBank(this)
            mLines = mQuoteBank!!.readLine(mPath)
            for (string in mLines!!) {
                val sp = string.split("/".toRegex()).toTypedArray()
                Log.d("station", sp[0])
                handler!!.insert2("subway", sp[0], sp[1], sp[2])
                sub_st.add(sp[1])
            }
        } else {
            mQuoteBank = QuoteBank(this)
            mLines = mQuoteBank!!.readLine(mPath)
            for (string in mLines!!) {
                val sp = string.split("/".toRegex()).toTypedArray()
                //andler.insert2("subway", sp[0], sp[1], sp[2]);
                sub_st.add(sp[1])
            }
        }
        sub_gu.add("종로구")
        sub_gu.add("중구")
        sub_gu.add("용산구")
        sub_gu.add("성동구")
        sub_gu.add("광진구")
        sub_gu.add("동대문구")
        sub_gu.add("중랑구")
        sub_gu.add("성북구")
        sub_gu.add("강북구")
        sub_gu.add("도봉구")
        sub_gu.add("노원구")
        sub_gu.add("은평구")
        sub_gu.add("서대문구")
        sub_gu.add("마포구")
        sub_gu.add("양천구")
        sub_gu.add("강서구")
        sub_gu.add("구로구")
        sub_gu.add("금천구")
        sub_gu.add("영등포구")
        sub_gu.add("동작구")
        sub_gu.add("관악구")
        sub_gu.add("서초구")
        sub_gu.add("강남구")
        sub_gu.add("송파구")
        sub_gu.add("강동구")
        mp = MediaPlayer.create(this, R.raw.aa)
        mPager = findViewById<View>(R.id.pager) as ViewPager
        mPager!!.adapter = PagerAdapterClass(applicationContext)
        handler!!.close()
        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    fun addListenerOnButton() { //사이렌버튼
        val StreamType = 0
        //  alarm = (Button) findViewById(R.id.btn_siren);
//사이렌 울리는 버튼 클릭이벤트
        fab = findViewById<View>(R.id.btn_siren) as ImageButton
        fab!!.setBackgroundResource(R.drawable.btn_siren)
        fab!!.setOnClickListener(View.OnClickListener { arg0: View ->
            if (arg0.id == R.id.btn_siren) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"))
                //startActivity(browserIntent);
// 소리 조절
                am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                // 소리 크기 50/
                am!!.setStreamVolume(AudioManager.STREAM_MUSIC, 50, AudioManager.FLAG_PLAY_SOUND)
                mp!!.start()
                mContext = applicationContext
                val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                val layout = inflater.inflate(R.layout.popup, findViewById<View>(R.id.popupid) as ViewGroup)
                aDialog = AlertDialog.Builder(this@MainActivity)
                //aDialog.setTitle("title"); //타이틀바 제목
                aDialog!!.setView(layout) //dialog.xml 파일을 뷰로 셋팅
                tv = layout.findViewById<View>(R.id.TimeText) as TextView
                alarm_text = layout.findViewById<View>(R.id.alarm_text1) as TextView
                alarm_text1 = layout.findViewById<View>(R.id.alarm_text2) as TextView
                alarm_text2 = layout.findViewById<View>(R.id.alarm_text3) as TextView
                send = layout.findViewById<View>(R.id.send) as Button
                send!!.setOnClickListener(mPagerListener)
                //그냥 닫기버튼을 위한 부분
                val close = layout.findViewById<View>(R.id.close) as Button
                close.setOnClickListener(mPagerListener)
                //                    aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            mp.stop();
//                            //requestGPS();
//
//                        }
//                    });
                ad = aDialog!!.create()
                ad.show()
                c = Counter()
                c!!.start()
            }
        })
    }

    inner class Counter : Thread() {
        override fun run() { // 핸들러로 카운터를 보낸다.
            var count = 0
            flag = true
            while (flag) {
                mHandler.sendEmptyMessage(count)
                //                if ( count == 6) {
//                    //ad.dismiss();
//                    //flag=false;
//                   // alarm_text.setText("");
//                }
                count++
                SystemClock.sleep(1000L)
            }
        }
    }

    val mHandler: Handler = object : Handler() {
        //핸들러를 통해 UI스레드에 접근한다.
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what != 6) {
                tv.setText(5 - msg.what.toString() + "")
            } else {
                if (!send_text) {
                    alarm_text!!.text = "신고가 접수되었습니다"
                    alarm_text1!!.text = "경보음을 끄고싶으시면"
                    alarm_text2!!.text = "취소하기 버튼을 눌러주세요."
                    requestGPS()
                    tv!!.visibility = TextView.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mp != null) {
            mp!!.release()
        }
        // am.setStreamVolume(AudioManager.STREAM_MUSIC, 0,AudioManager.FLAG_PLAY_SOUND);
    }

    // Request Input 이 함수를 부르면 위치정보 가져옴!!!!
    fun requestGPS() { // create class object
        gps = GPSTracker(this@MainActivity)
        // check if GPS enabled
        if (gps!!.canGetLocation()) {
            val latitude = gps!!.latitude
            val longitude = gps!!.longitude
            convertWGS84toCNM(longitude, latitude)
            // \n is for new line
        } else { // can't get location
// GPS or Network is not enabled
// Ask user to enable GPS/network in settings
            gps!!.showSettingsAlert()
        }
    }

    // Convert to CNM
    fun convertWGS84toCNM(x: Double, y: Double) {
        val tmp_x = (x * 1000000).toInt()
        val tmp_y = (y * 1000000).toInt()
        val posX = tmp_x.toDouble() / 1000000
        val posY = tmp_y.toDouble() / 1000000
        val url = "https://apis.daum.net/local/geo/transcoord?apikey=e2824aa5f065d79fcccd2718889409cc&fromCoord=WGS84&y=$posY&x=$posX&toCoord=CONGNAMUL&output=xml"
        val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val dataList = cnmXMLParser!!.result
                val dataListSize = dataList!!.size
                Log.d("data List Size", Integer.toString(dataListSize))
                for (i in 0 until dataListSize) {
                    Log.d("XML Parsing Result", "Result>> $i")
                    val transX = dataList[i].x.toDouble()
                    val transY = dataList[i].y.toDouble()
                    cur_x = transX / 2.5
                    cur_y = transY / 2.5
                    cnm_str = cnm_str + "\n X: " + transX / 2.5 + " ,\n Y: " + transY / 2.5
                    //Toast.makeText(getApplicationContext(), cnm_str, Toast.LENGTH_LONG).show();
//requestS
                    requestStation()
                }
                //stat.setText(cnm_str);
            }
        }
        cnmXMLParser = CNM_XMLParser(url, mHandler)
        //cnmXMLParser.run();
//여기서 잠시 주석
        val thread = Thread(cnmXMLParser)
        thread.start()
        //        try{thread.join();}catch (InterruptedException e){}
// Station List
//  stat.setText(station_list);
    }

    // Display View
    fun requestStation() {
        val posX = cur_x
        val posY = cur_y
        if (posX == 0.0 || posY == 0.0) {
            Toast.makeText(applicationContext, "Not Setting CNM", Toast.LENGTH_LONG).show()
            return
        }
        val url = "http://swopenapi.seoul.go.kr/api/subway/636a79646768616e39316e6a536a53/xml/nearBy/1/5/$posX/$posY"
        val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val dataList = mXMLParser!!.result
                val dataListSize = dataList!!.size
                Log.d("data List Size", Integer.toString(dataListSize))
                station_list = ""
                for (i in 0 until dataListSize) {
                    if (i == 3) break
                    Log.d("XML Parsing Result", "Result>> $i")
                    val endX = dataList[i].subX.toDouble()
                    val endY = dataList[i].subY.toDouble()
                    station_list = (station_list + dataList[i].statnNm + "(" + dataList[i].subwayNm + ")"
                            + ", " + distance(posX, posY, endX, endY) + "m")
                    if (i < 2) station_list = station_list + "\n"
                }
                Toast.makeText(applicationContext, station_list, Toast.LENGTH_SHORT).show()
                sendSMS(station_list!!)
                //station의 정보가 station_list에 담겨있다. --> 문자로 보내야됨.
//stat.setText(station_list);
            }
        }
        mXMLParser = StationXMLParser(url, mHandler)
        val thread = Thread(mXMLParser)
        thread.start()
        //        try{thread.join();
//            }catch (InterruptedException e){}
// Station List
//  stat.setText(station_list);
    }

    fun distance(startPointX: Double, startPointY: Double, endPointX: Double, endPointY: Double): String {
        var result = ((startPointX - endPointX) * (startPointX - endPointX)
                + (startPointY - endPointY) * (startPointY - endPointY))
        result = Math.sqrt(result)
        val mid_result = (result * 100).toInt()
        result = mid_result.toDouble() / 100
        if (result > 1000.0) {
            val tmp = (result / 10).toInt()
            result = tmp.toDouble() / 100
            return result.toString() + "k"
        }
        return result.toString() + ""
    }

    //화면아래에 있는 버튼
    override fun onClick(v: View) {
        when (v.id) {
            R.id.quick_request_btn -> {
                setCurrentInflateItem(0)
                quick_btn!!.setBackgroundResource(R.drawable.quick_request_active)
                find_btn!!.setBackgroundResource(R.drawable.find_gu_in_active)
                sett_btn!!.setBackgroundResource(R.drawable.setting_in_active)
                title!!.text = "안심귀가 신청"
            }
            R.id.find_gu_btn -> {
                setCurrentInflateItem(1)
                quick_btn!!.setBackgroundResource(R.drawable.quick_request_in_active)
                find_btn!!.setBackgroundResource(R.drawable.find_gu_active)
                sett_btn!!.setBackgroundResource(R.drawable.setting_in_active)
                title!!.text = "동네찾기"
            }
            R.id.setting_btn -> {
                setCurrentInflateItem(2)
                quick_btn!!.setBackgroundResource(R.drawable.quick_request_in_active)
                find_btn!!.setBackgroundResource(R.drawable.find_gu_in_active)
                sett_btn!!.setBackgroundResource(R.drawable.setting_active)
                title!!.text = "설정"
            }
        }
    }

    private fun setCurrentInflateItem(type: Int) {
        if (type == 0) {
            mPager!!.currentItem = 0
        } else if (type == 1) {
            mPager!!.currentItem = 1
        } else if (type == 2) {
            mPager!!.currentItem = 2
        } else if (type == 3) { //Toast.makeText(getApplicationContext(),"흠",Toast.LENGTH_SHORT).show();
            mPager!!.currentItem = 3
        } else if (type == 4) { //Toast.makeText(getApplicationContext(),"흠",Toast.LENGTH_SHORT).show();
            mPager!!.currentItem = 4
        } else if (type == 5) { //Toast.makeText(getApplicationContext(),"흠",Toast.LENGTH_SHORT).show();
            mPager!!.currentItem = 5
        }
    }

    private var quick_btn: Button? = null
    private var find_btn: Button? = null
    private var sett_btn: Button? = null
    /*
     * Layout
     */
    private fun setLayout() {
        quick_btn = findViewById<View>(R.id.quick_request_btn) as Button
        find_btn = findViewById<View>(R.id.find_gu_btn) as Button
        sett_btn = findViewById<View>(R.id.setting_btn) as Button
        quick_btn!!.setOnClickListener(this)
        find_btn!!.setOnClickListener(this)
        sett_btn!!.setOnClickListener(this)
    }

    var mPagerListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            when (v.id) {
                R.id.call_home ->  //                    String station1 = "노원역";
//                Cursor subway_cursor1 = handler.select2("subway", station1);
//                while (subway_cursor1.moveToNext()) {
//                    first = subway_cursor1.getString(subway_cursor1
//                            .getColumnIndex("gu"));
//                }
//                String number1="";
//                Cursor phone_cursor1 = handler.select("phone",first);
//                while (phone_cursor1.moveToNext()) {
//                    number1 = phone_cursor1.getString((phone_cursor1
//                            .getColumnIndex("number")));
//                }
                    startActivity(Intent("android.intent.action.DIAL", Uri
                            .parse("tel:$phone1")))
                R.id.call_one ->  //                    String station2 = "종각역";
//                    Cursor subway_cursor2 = handler.select2("subway", station2);
//                while (subway_cursor2.moveToNext()) {
//                    first = subway_cursor2.getString(subway_cursor2
//                            .getColumnIndex("gu"));
//                }
//                String number2="";
//                Cursor phone_cursor2 = handler.select("phone",first);
//                while (phone_cursor2.moveToNext()) {
//                    number2 = phone_cursor2.getString((phone_cursor2
//                            .getColumnIndex("number")));
//                }
                    startActivity(Intent("android.intent.action.DIAL", Uri
                            .parse("tel:$phone2")))
                R.id.call_two ->  //                    String station3 = "강남역";
//                   Cursor subway_cursor3 = handler.select2("subway", station3);
//                while (subway_cursor3.moveToNext()) {
//                    first = subway_cursor3.getString(subway_cursor3
//                            .getColumnIndex("gu"));
//                }
//                String number3="";
//                Cursor phone_cursor3 = handler.select("phone",first);
//                while (phone_cursor3.moveToNext()) {
//                    number3 = phone_cursor3.getString((phone_cursor3
//                            .getColumnIndex("number")));
//                }
                    startActivity(Intent("android.intent.action.DIAL", Uri
                            .parse("tel:$phone3")))
                R.id.setlocal -> {
                    //Toast.makeText(getApplicationContext(),"버튼눌림",Toast.LENGTH_SHORT).show();
                    val intentSubActivity = Intent(this@MainActivity, EditLocalActivity::class.java)
                    startActivity(intentSubActivity)
                }
                R.id.setcall -> {
                    val intentSubActivity2 = Intent(this@MainActivity, SetCallActivity::class.java)
                    startActivity(intentSubActivity2)
                }
                R.id.setalarm -> {
                    val intentSubActivity3 = Intent(this@MainActivity, SetAlarmActivity::class.java)
                    startActivity(intentSubActivity3)
                }
                R.id.getGuide -> {
                    val intentSubActivity4 = Intent(this@MainActivity, GetGuideActivity::class.java)
                    startActivity(intentSubActivity4)
                }
                R.id.call -> startActivity(Intent("android.intent.action.DIAL", Uri
                        .parse("tel:$call_gu")))
                R.id.send -> {
                    requestGPS()
                    send_text = true
                    alarm_text!!.text = "신고가 접수되었습니다"
                    alarm_text1!!.text = "경보음을 끄고싶으시면"
                    alarm_text2!!.text = "취소하기 버튼을 눌러주세요."
                    tv!!.visibility = TextView.GONE
                    send!!.visibility = Button.GONE
                }
                R.id.close -> {
                    mp!!.stop()
                    ad!!.dismiss()
                    flag = false
                }
                R.id.change -> {
                    val inflater = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                    val layout = inflater.inflate(R.layout.popup_gu, findViewById<View>(R.id.popupguid) as ViewGroup)
                    sDialog = AlertDialog.Builder(this@MainActivity)
                    //aDialog.setTitle("title"); //타이틀바 제목
                    sDialog!!.setView(layout) //dialog.xml 파일을 뷰로 셋팅
                    val search_st = layout.findViewById<View>(R.id.search_st) as RadioButton
                    val search_gu = layout.findViewById<View>(R.id.search_gu) as RadioButton
                    search_st.setOnClickListener(this)
                    search_gu.setOnClickListener(this)
                    select = sDialog!!.create()
                    select.show()
                }
                R.id.search_st -> {
                    Log.d("choice", "1")
                    arrStr = sub_st.toTypedArray()
                    adapter = ArrayAdapter(applicationContext, android.R.layout.simple_dropdown_item_1line, arrStr)
                    find_gu_tv!!.setAdapter<ArrayAdapter<String>>(adapter)
                    //arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
                    find_gu_tv!!.hint = "역으로검색"
                    station_ok = true
                    select!!.dismiss()
                }
                R.id.search_gu -> {
                    Log.d("choice", "2")
                    arrStr = sub_gu.toTypedArray()
                    adapter = ArrayAdapter(applicationContext, android.R.layout.simple_dropdown_item_1line, arrStr)
                    find_gu_tv!!.setAdapter<ArrayAdapter<String>>(adapter)
                    find_gu_tv!!.hint = "구로 검색"
                    station_ok = false
                    select!!.dismiss()
                }
            }
        }
    }

    //주소록에서 번호 가져오기
    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            val cr = contentResolver
            val cursor = cr.query(data.data!!, arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID), null, null, null)
            cursor!!.moveToFirst()
            //name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            number = cursor.getString(1) //1은 번호를 받아옵니다.
            photo = cursor.getLong(2) //photo받아옴
            //Long contactId = cursor.getLong(contactId_idx);
//Log.e("###", contactId_idx+" | "+contactId+ " | "+name);
            val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, photo!!)
            val input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri)
            // 사진없으면 기본 사진 보여주기
            if (input != null) {
                val contactPhoto = BitmapFactory.decodeStream(input)
                photoView!!.setImageBitmap(contactPhoto)
            } else {
                photoView!!.setImageDrawable(resources.getDrawable(R.drawable.school))
            }
            cursor.close()
        }
        super.onActivityResult(requestCode, resultCode, data)
        //smsNumber=(EditText)findViewById(R.id.pn);
        smsNumber!!.setText(number)
        //지정인번호를 계속 저장해둬야 하므로 DB대신 txt파일에 저장
// File file = new File("number.txt");
        try { //this.deleteFile("number.txt");
//File file = new File("number.txt");
            val out: OutputStream
            out = FileOutputStream(filePath)
            out.write(number!!.toByteArray())
            Toast.makeText(this, "지정인이 등록되었습니다", Toast.LENGTH_SHORT).show()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //    TimerTask myTask = new TimerTask() {
//        public void run() {
//            // <-- 요기에 반복할 작업을 넣으시면 됩니다.(예, sendSMS(View V);)
//
//            Handler mHandler = new Handler(Looper.getMainLooper());
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // 내용
//                    sendSMS();
//
//                }
//            }, 0);
//
//        }
//    };
//문자발송
    fun sendSMS(station: String) { // String smsNum="01051070178";
        val smsText: String
        smsText = station
        val phone_cursor = handler!!.select_quick("man")
        while (phone_cursor.moveToNext()) {
            val smsNum = phone_cursor.getString(phone_cursor
                    .getColumnIndex("number"))
            Log.d("test", smsNum)
            if (smsNum.length > 0 && smsText.length > 0) {
                sendSMS(smsNum, smsText)
            } else {
                Toast.makeText(this, "지정인이 없습니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendSMS(smsNumber: String?, smsText: String) {
        val sentIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT_ACTION"), PendingIntent.FLAG_UPDATE_CURRENT)
        val deliveredIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_DELIVERED_ACTION"), 0)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // 전송 성공
                        Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE ->  // 전송 실패
                        Toast.makeText(context, "전송실패", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE ->  // 서비스 지역 아님
                        Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_RADIO_OFF ->  // 무선 꺼짐
                        Toast.makeText(context, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NULL_PDU ->  // PDU 실패
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show()
                }
                //                Toast.makeText(getApplicationContext(),String.valueOf(getResultCode()), Toast.LENGTH_SHORT).show();
            }
        }, IntentFilter("SMS_SENT_ACTION"))
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // 도착 완료
                        Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show()
                    Activity.RESULT_CANCELED ->  // 도착 안됨
                        Toast.makeText(context, "SMS 도착 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }, IntentFilter("SMS_DELIVERED_ACTION"))
        val mSmsManager = SmsManager.getDefault()
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent)
        Toast.makeText(this, "2$smsText", Toast.LENGTH_SHORT).show()
    }

    private inner class PagerAdapterClass(c: Context?) : PagerAdapter() {
        private val mInflater: LayoutInflater
        override fun getCount(): Int {
            return 3
        }

        override fun instantiateItem(pager: View, position: Int): Any {
            var v: View? = null
            if (position == 0) { //quick신청 코드
                v = mInflater.inflate(R.layout.inflate_one, null)
                //버튼들
                val call_home = v.findViewById<View>(R.id.call_home) as Button
                val call_one = v.findViewById<View>(R.id.call_one) as Button
                val call_two = v.findViewById<View>(R.id.call_two) as Button
                v.findViewById<View>(R.id.call_home).setOnClickListener(mPagerListener)
                v.findViewById<View>(R.id.call_one).setOnClickListener(mPagerListener)
                v.findViewById<View>(R.id.call_two).setOnClickListener(mPagerListener)
                //1번 주소지정지에 대한 내용
                val home_tv = v.findViewById<View>(R.id.home_tv) as TextView
                val home_sub_tv = v.findViewById<View>(R.id.home_sub_tv) as TextView
                val home_ward_tv = v.findViewById<View>(R.id.home_ward_tv) as TextView
                //2번
                val set1_tv = v.findViewById<View>(R.id.set1_tv) as TextView
                val set1_sub_tv = v.findViewById<View>(R.id.set1_sub_tv) as TextView
                val set1_ward_tv = v.findViewById<View>(R.id.set1_ward_tv) as TextView
                //3번
                val set2_tv = v.findViewById<View>(R.id.set2_tv) as TextView
                val set2_sub_tv = v.findViewById<View>(R.id.set2_sub_tv) as TextView
                val set2_ward_tv = v.findViewById<View>(R.id.set2_ward_tv) as TextView
                val quick_cursor1 = handler!!.select_quick("QUICK")
                var t = 3
                while (quick_cursor1.moveToNext()) {
                    val name1 = quick_cursor1.getString(quick_cursor1
                            .getColumnIndex("name"))
                    val station1 = quick_cursor1.getString(quick_cursor1.getColumnIndex("station"))
                    val gu1 = quick_cursor1.getString(quick_cursor1.getColumnIndex("gu"))
                    val number1 = quick_cursor1.getString(quick_cursor1.getColumnIndex("phone"))
                    var icon1 = quick_cursor1.getInt(quick_cursor1.getColumnIndex("icon"))
                    if (icon1 == 0) icon1 = R.drawable.home else if (icon1 == 1) icon1 = R.drawable.office
                    when (t) {
                        3 -> {
                            home_tv.text = name1
                            home_sub_tv.text = station1
                            home_ward_tv.text = gu1
                            phone1 = number1
                            v.findViewById<View>(R.id.home_ic).setBackgroundResource(icon1)
                            v.findViewById<View>(R.id.home_ward).setBackgroundResource(R.drawable.add)
                            v.findViewById<View>(R.id.home_sub_img).setBackgroundResource(R.drawable.subway)
                        }
                        2 -> {
                            set1_tv.text = name1
                            set1_sub_tv.text = station1
                            set1_ward_tv.text = gu1
                            phone2 = number1
                            v.findViewById<View>(R.id.set1_ic).setBackgroundResource(icon1)
                            v.findViewById<View>(R.id.set1_ward_img).setBackgroundResource(R.drawable.add)
                            v.findViewById<View>(R.id.set1_sub_img).setBackgroundResource(R.drawable.subway)
                        }
                        1 -> {
                            set2_tv.text = name1
                            set2_sub_tv.text = station1
                            set2_ward_tv.text = gu1
                            phone3 = number1
                            v.findViewById<View>(R.id.set2_ic).setBackgroundResource(icon1)
                            v.findViewById<View>(R.id.set2_ward_img).setBackgroundResource(R.drawable.add)
                            v.findViewById<View>(R.id.set2_sub_img).setBackgroundResource(R.drawable.subway)
                        }
                        else -> {
                        }
                    }
                    t--
                    //String msg=name1+station1+gu1+number1;
//Log.d("test",msg);
                }
                //myAdapter = new MyCursorAdapter ( getApplicationContext(), quick_cursor1);
// PersonAdapter m_adapter = new PersonAdapter(getApplicationContext(), R.layout.list_view, m_orders);
// 어댑터를 생성합니다.
// ListView listview = (ListView)v.findViewById(R.id.listview);
//listview.setAdapter(myAdapter);
            } else if (position == 1) { //지정 신청 코드
                v = mInflater.inflate(R.layout.inflate_two, null)
                v.findViewById<View>(R.id.iv_two)
                mContext = applicationContext
                val find = v.findViewById<View>(R.id.find_gu_btn_by_name) as Button
                Log.d("station_ok", station_ok.toString())
                find_gu_tv = v.findViewById<View>(R.id.find_gu_ed) as AutoCompleteTextView
                arrStr = if (station_ok) {
                    sub_st.toTypedArray()
                } else {
                    sub_gu.toTypedArray()
                }
                adapter = ArrayAdapter(applicationContext, android.R.layout.simple_dropdown_item_1line, arrStr)
                find_gu_tv!!.setAdapter<ArrayAdapter<String>>(adapter)
                // auto make text
                listView = v.findViewById<View>(R.id.listView) as ListView
                val change = v.findViewById<View>(R.id.change) as Button
                change.setOnClickListener(mPagerListener)
                val call = v.findViewById<View>(R.id.call) as Button
                val set_gu = v.findViewById<View>(R.id.set_gu) as TextView
                val set_gu_text = v.findViewById<View>(R.id.set_gu_text) as TextView
                val c = applicationContext
                val subway = v.findViewById<View>(R.id.subway) as ImageView
                call.setOnClickListener(mPagerListener)
                find.setOnClickListener { v1: View? ->
                    if (station_ok) { //역검색
                        val station = find_gu_tv!!.text.toString()
                        Log.d("station", station)
                        var cursor = handler!!.select2("subway", station)
                        //                                Boolean a=cursor.moveToNext();
//                                Log.d("yes",String.valueOf(a));
//String gu = cursor.getString(cursor.getColumnIndex("gu"));
                        if (!cursor.moveToNext()) {
                            set_gu.text = "검색결과가 없습니다"
                        } else {
                            subway.setBackgroundResource(R.drawable.subway)
                            val s_gu = cursor.getString(cursor.getColumnIndex("gu"))
                            val cursor2 = handler!!.select("phone", s_gu)
                            cursor = handler!!.select("subway", s_gu)
                            call_gu = cursor2.getString(cursor2.getColumnIndex("number"))
                            set_gu.text = s_gu
                            val num_c = cursor.count
                            val gu_text = num_c.toString() + "개 역이 검색되었습니다"
                            set_gu_text.text = gu_text
                            call.setBackgroundResource(R.drawable.request_active)
                            val myadapter = MyCursorAdapter2(c, cursor)
                            //  CustomAdapter custom = new CustomAdapter(getApplicationContext(), 0, sub_st);
                            listView!!.adapter = myadapter
                        }
                    } else { //구검색
                        subway.setBackgroundResource(R.drawable.subway)
                        val gu = find_gu_tv!!.text.toString()
                        Log.d("station", gu)
                        val cursor = handler!!.select("subway", gu)
                        //                                Boolean a=cursor.moveToNext();
//                                Log.d("yes",String.valueOf(a));
//String gu = cursor.getString(cursor.getColumnIndex("gu"));
                        if (!cursor.moveToNext()) {
                            set_gu.text = "검색결과가 없습니다"
                        } else { //String s_gu = cursor.getString(cursor.getColumnIndex("gu"));
                            val cursor2 = handler!!.select("phone", gu)
                            //cursor = handler.select("subway", s_gu);
                            call_gu = cursor2.getString(cursor2.getColumnIndex("number"))
                            set_gu.text = gu
                            val num_c = cursor.count
                            val gu_text = num_c.toString() + "개 역이 검색되었습니다"
                            set_gu_text.text = gu_text
                            call.setBackgroundResource(R.drawable.request_active)
                            val myadapter = MyCursorAdapter2(c, cursor)
                            //  CustomAdapter custom = new CustomAdapter(getApplicationContext(), 0, sub_st);
                            listView!!.adapter = myadapter
                        }
                    }
                }
                //String abc = find_gu_tv.getResources().getString(0);
            } else if (position == 2) { //설정 코드
                v = mInflater.inflate(R.layout.inflate_three, null)
                v.findViewById<View>(R.id.iv_three)
                v.findViewById<View>(R.id.setlocal).setOnClickListener(mPagerListener)
                v.findViewById<View>(R.id.setcall).setOnClickListener(mPagerListener)
                v.findViewById<View>(R.id.setalarm).setOnClickListener(mPagerListener)
                v.findViewById<View>(R.id.getGuide).setOnClickListener(mPagerListener)
                //v.findViewById(R.id.getTutorial).setOnClickListener(mPagerListener);
            }
            //            }else if(position==3) {//setlocal
//                //Toast.makeText(getApplicationContext(),"흠2",Toast.LENGTH_SHORT);
//                v = mInflater.inflate(R.layout.set_local, null);
//
//            }
//            }else if(position==4){//setcall
//                v=mInflater.inflate(R.layout.set_call,null);
//                photoView = (ImageView)v.findViewById(R.id.image);
//                title.setText("보호자 연락처");
//                smsNumber=(EditText) v.findViewById(R.id.pn);
//                try {// 전에 지정해둔 지정인 불러오기
//                    InputStream fis = new FileInputStream(filePath);
//                    byte[] data= new byte[fis.available()];
//                    fis.read(data);
//                    fis.close();
//                    number = new String(data);
//
//                    smsNumber.setText(number);
//                    //Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//
//                v.findViewById(R.id.setpn).setOnClickListener(mPagerListener);
//            }else if(position==5)//setalarm
//            {
//                v=mInflater.inflate(R.layout.set_alarm,null);
//            }
            (pager as ViewPager).addView(v, 0)
            return v!!
        }

        override fun destroyItem(pager: View, position: Int, view: Any) {
            (pager as ViewPager).removeView(view as View)
        }

        override fun isViewFromObject(pager: View, obj: Any): Boolean {
            return pager === obj
        }

        override fun restoreState(arg0: Parcelable?, arg1: ClassLoader?) {}
        override fun saveState(): Parcelable? {
            return null
        }

        override fun startUpdate(arg0: View) {}
        override fun finishUpdate(arg0: View) {}

        init {
            mInflater = LayoutInflater.from(c)
        }
    }

    companion object {
        var first = ""
        var sub_st = ArrayList<String>()
        var sub_gu = ArrayList<String>()
        const val mPath = "db_subway.txt"
    }
}