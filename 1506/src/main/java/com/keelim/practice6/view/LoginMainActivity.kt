package com.keelim.practice6.view

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Process
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.keelim.practice6.R
import com.keelim.practice6.model.ImageData
import com.keelim.practice6.task.DownloadImageTask
import com.keelim.practice6.task.Fragment_loggedInRecord
import com.keelim.practice6.utils.SavedSharedPreference
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoginMainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    // 이곳은 로그인을 할 때 넘어오는 액티비티다.
// 아래 코드 3줄: 공지.java와 공지ListAdapter.java를 넣을 변수 (공지글)
// private ListView noticeListView;
// private 공지ListAdapter adapter;
// private List<공지> noticeList;
    private var imageData: ImageData? = null
    var font_one: Typeface? = null
    private var headerUserPic: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_main)
        val actionBar = supportActionBar
        val toolbar =
            findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        (this@LoginMainActivity as AppCompatActivity).supportActionBar
            .setTitle(Html.fromHtml("<font color='#ffffff'>" + "Walk Away" + "</font>"))
        val intent = intent
        userID = intent.extras!!.getString("userID")
        println("userID=$userID")
        font_one = Typeface.createFromAsset(assets, "fonts/font_one.ttf")
        // 현재 스마트폰 화면을 세로 방향으로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //imply navigation drawer (navigation drawer적용)
        val drawer_layout =
            findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val nav_view =
            findViewById<View>(R.id.nav_view) as NavigationView
        nav_view.setNavigationItemSelectedListener(this)
        //set text view on navigation drawer's header (navigation drawer의 헤더 부분에 있는 text view를 셋팅)
        val headerView = nav_view.getHeaderView(0)
        val headerUserId =
            headerView.findViewById<View>(R.id.headerUserId) as TextView
        headerUserPic =
            headerView.findViewById<View>(R.id.headerUserPic) as ImageView
        headerUserId.text = userID
        headerUserId.setTypeface(font_one)
        // 이미지 버튼 추가
        val walk_imageButton =
            findViewById<View>(R.id.walk_imageButton) as ImageButton
        walk_imageButton.setOnClickListener {
            val intent =
                Intent(this@LoginMainActivity, LoggedInWalkActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
            finish()
        }
        // 텍스트 버튼 추가
        val start_text = findViewById<View>(R.id.start_text) as TextView
        start_text.setOnClickListener {
            val intent =
                Intent(this@LoginMainActivity, LoggedInWalkActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
            finish()
        }
        // 공지글 notice 부분
// noticeListView = (ListView) findViewById(R.id.noticeListView);
// noticeList = new ArrayList<공지>();
/*
        DB 연결하기 전에 만들었던 예시 데이터
        noticeList.add(new 공지("공지사항 예시", "이름 예시", "날짜 생략"));
        noticeList.add(new 공지("공지사항 예시", "이름 예시", "날짜 생략"));
        */
// adapter에 해당 List를 매칭 (각각 차례대로 매칭)
// adapter = new 공지ListAdapter(getApplicationContext(), noticeList);
// noticeListView.setAdapter(adapter);
        val run =
            findViewById<View>(R.id.run) as Button
        val courseButton =
            findViewById<View>(R.id.courseButton) as Button
        //final Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        val notice =
            findViewById<View>(R.id.notice) as LinearLayout //해당 Fragment 눌렀을 때 화면의 레이아웃이 바뀌는 부분
        // 선택된 버튼만 색상을 어둡게 만들고 나머지 버튼은 밝은 색상으로 변경
        run.background = resources.getDrawable(R.drawable.button_back_dark_color, theme)
        courseButton.background = resources.getDrawable(
            R.drawable.button_back_prime_color,
            theme
        )
        //statisticsButton.setBackground(getResources().getDrawable(R.drawable.button_back_prime_color));
// 1. 걷기시작 화면으로
        run.setOnClickListener {
            notice.visibility = View.GONE
            // 선택된 버튼만 색상을 어둡게 만들고 나머지 버튼은 밝은 색상으로 변경
            run.background = resources.getDrawable(
                R.drawable.button_back_dark_color,
                theme
            )
            courseButton.background = resources.getDrawable(
                R.drawable.button_back_prime_color,
                theme
            )
            //statisticsButton.setBackground(getResources().getDrawable(R.drawable.button_back_prime_color));
            val fragmentManager =
                supportFragmentManager
            val fragmentTransaction =
                fragmentManager.beginTransaction()
            // fragment 부분을, 다른 'fragment'로 대체해주는 것
            fragmentTransaction.replace(R.id.fragment, LoginMainFragment())
            fragmentTransaction.commit()
            /*
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoggedInWalk.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    */
        }
        // 2. 코스 버튼 (프래그먼트)
        courseButton.setOnClickListener {
            // 공지사항 부분이 보이지 않도록 하는 부분
// 즉 notice 라는 LinearLayout이 사라지고 다른 Fragment가 보일 수 있도록 화면을 바꿔주는 것
            notice.visibility = View.GONE
            // 선택된 버튼만 색상을 어둡게 만들고 나머지 버튼은 밝은 색상으로 변경
            run.background = resources.getDrawable(
                R.drawable.button_back_prime_color,
                theme
            )
            courseButton.background = resources.getDrawable(
                R.drawable.button_back_dark_color,
                theme
            )
            //statisticsButton.setBackground(getResources().getDrawable(R.drawable.button_back_prime_color));
            val fragmentManager =
                supportFragmentManager
            val fragmentTransaction =
                fragmentManager.beginTransaction()
            // fragment 부분을 다른 'fragment'로 대체해주는 것
            fragmentTransaction.replace(R.id.fragment, Fragment_loggedInRecord())
            fragmentTransaction.commit()
        }
        /*
        // 3. 통계 버튼
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 공지사항 부분이 보이지 않도록 하는 부분
                // 즉 notice 라는 LinearLayout이 사라지고 다른 Fragment가 보일 수 있도록 화면을 바꿔주는 것
                notice.setVisibility(View.GONE);

                // 선택된 버튼만 색상을 어둡게 만들고 나머지 버튼은 밝은 색상으로 변경
                runButton.setBackground(getResources().getDrawable(R.drawable.button_back_prime_color));
                courseButton.setBackground(getResources().getDrawable(R.drawable.button_back_prime_color));
                statisticsButton.setBackground(getResources().getDrawable(R.drawable.button_back_dark_color));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // fragment 부분을 new 다른 'Fragment'로 대체해주는 것
                fragmentTransaction.replace(R.id.fragment, new Fragment());
                fragmentTransaction.commit();
            }
        });
*/
// 정상적으로 데이터베이스에 접근해서 찾아옴
// new BackgroundTask().execute();
        BackgroundTask2().execute()
    }

    /*
    // MainFragment로 값 전달
    public Object getData() {
        return userID;
    }
*/
    internal inner class BackgroundTask2 :
        AsyncTask<Void?, Void?, String?>() {
        // (로딩창 띄우기 작업 3/1) 로딩창을 띄우기 위해 선언해준다.
        var dialog = ProgressDialog(this@LoginMainActivity)
        var target //우리가 접속할 홈페이지 주소가 들어감
                : String? = null

        override fun onPreExecute() {
            target =
                "hellogetImageFromServer.php?userId=" + userID!!.trim { it <= ' ' } //해당 웹 서버에 접속
            // (로딩창 띄우기 작업 3/2)
            dialog.setMessage("로딩중")
            dialog.show()
        }

        protected override fun doInBackground(vararg voids: Void): String? {
            try { // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                val url = URL(target)
                val httpURLConnection =
                    url.openConnection() as HttpURLConnection
                // 넘어오는 결과값을 그대로 저장
                val inputStream = httpURLConnection.inputStream
                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                val bufferedReader =
                    BufferedReader(InputStreamReader(inputStream))
                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                var temp: String
                val stringBuilder = StringBuilder()
                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while (bufferedReader.readLine().also {
                        temp = it
                    } != null) { // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n")
                }
                // 끝난 뒤 닫기
                bufferedReader.close()
                inputStream.close()
                httpURLConnection.disconnect() //인터넷도 끊어줌
                println(">>" + stringBuilder.toString().trim { it <= ' ' })
                return stringBuilder.toString().trim { it <= ' ' }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate()
        }

        //해당 결과를 처리할 수 있는 onPostExecute()
        public override fun onPostExecute(result: String?) {
            try { // 해당 결과(result) 응답 부분을 처리
                val pos = result!!.indexOf(":")
                var test = result.substring(pos + 1)
                test = test.trim { it <= ' ' }
                if (test[0] == '[' && test[1] == ']') {
                    println("null")
                } else {
                    println("exists")
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("response") //아까 변수 이름
                    if (jsonArray == null) {
                        println("null")
                    } else {
                        println("exists")
                    }
                    val count = 0
                    val userId: String
                    val image_data: String
                    // 현재 배열의 원소값을 저장
                    val `object`: JSONObject
                    `object` = jsonArray.getJSONObject(0)
                    userId = `object`.getString("image_tag")
                    image_data = `object`.getString("image_data")
                    println("check>>$userId+$image_data")
                    imageData = ImageData(userId, image_data)
                    val path = imageData!!.image_data
                    DownloadImageTask(headerUserPic).execute(path)
                }
                // (로딩창 띄우기 작업 3/3)
// 작업이 끝나면 로딩창을 종료시킨다.
                dialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*

    // 공지사항 데이터베이스에 접속할 수 있도록 만든 함수
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        // (로딩창 띄우기 작업 3/1) 로딩창을 띄우기 위해 선언해준다.
        ProgressDialog dialog = new ProgressDialog(로그인Activity.this);

        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            target = "helloNoticeList.php";  //해당 웹 서버에 접속

            // (로딩창 띄우기 작업 3/2)
            dialog.setMessage("로딩중");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // 넘어오는 결과값을 그대로 저장
                InputStream inputStream = httpURLConnection.getInputStream();

                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while ((temp = bufferedReader.readLine()) != null) {
                    // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n");
                }

                // 끝난 뒤 닫기
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();  //인터넷도 끊어줌
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override  //해당 결과를 처리할 수 있는 onPostExecute()
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 공지사항 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름

                int count = 0;
                String noticeContent, noticeName, noticeDate;

                while (count < jsonArray.length()) {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 공지사항의 Content, Name, Date에 해당하는 값을 가져와라는 뜻
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");

                    // 하나의 공지사항에 대한 객체를 만들어줌
                    공지 notice = new 공지(noticeContent, noticeName, noticeDate);

                    // 리스트에 추가해줌
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }

                // (로딩창 띄우기 작업 3/3)
                // 작업이 끝나면 로딩창을 종료시킨다.
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

*/
// 두번 뒤로가기 버튼을 누르면 종료
    private var lastTimeBackPressed: Long = 0

    override fun onBackPressed() { // 한번 버튼을 누른 뒤, 1.5초 이내에 또 누르면 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            moveTaskToBack(true)
            finish()
            Process.killProcess(Process.myPid())
            return
        }
        Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        lastTimeBackPressed = System.currentTimeMillis()
    }

    //adding the menu on tool bar (액션바에서 메뉴 추가)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.login_after_menu, menu)
        return true
    }

    // adding actions that will be done on clicking menu items
// (메뉴 아이템들을 클릭할 때 발생할 이벤트 추가)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        // 로그아웃 버튼
        if (id == R.id.navLogout) {
            logout()
        } else if (id == R.id.navMyRecord) {
            myRecord()
        } else if (id == R.id.navSetting) {
            settingAct()
        }
        val drawer =
            findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        item.isChecked = false
        return true
    }

    // 로그아웃 버튼
    fun logout() {
        val dialog = Dialog(this@LoginMainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //for title bars not to be appeared (타이틀 바 안보이게)
        dialog.setContentView(R.layout.dialog_alert)
        val dialogTitle =
            dialog.findViewById<View>(R.id.dialogTitle) as TextView
        val dialogMessage =
            dialog.findViewById<View>(R.id.dialogMessage) as TextView
        val dialogButton1 =
            dialog.findViewById<View>(R.id.dialogButton1) as Button
        val dialogButton2 =
            dialog.findViewById<View>(R.id.dialogButton2) as Button
        dialogTitle.text = "  로그아웃  "
        dialogMessage.text = " 로그아웃 하시겠습니까? "
        dialogButton1.text = "예"
        dialogButton2.text = "아니오"
        dialog.setCanceledOnTouchOutside(false) //to prevent dialog getting dismissed on outside touch
        dialog.setCancelable(false) //to prevent dialog getting dismissed on back button
        dialog.show()
        dialogButton1.setOnClickListener {
            dialog.dismiss()
            SavedSharedPreference.clearId(this@LoginMainActivity)
            val intent = Intent(this@LoginMainActivity, FirstActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
        dialogButton2.setOnClickListener { dialog.dismiss() }
    }

    // 나의 기록 버튼
    fun myRecord() {
        val intent = Intent(this@LoginMainActivity, LoggedInRecordActivity::class.java)
        intent.putExtra("userID", userID)
        finish()
        startActivity(intent)
    }

    // 설정 버튼
    fun settingAct() {
        finish()
        val intent = Intent(this@LoginMainActivity, SettingActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmField
        var userID //모든 클래스에서 접근가능
                : String? = null
    }
}