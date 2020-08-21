package com.keelim.practice6.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Process
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice6.R
import java.util.*

class Game4_Run : AppCompatActivity(), SensorEventListener {
    //private BackPressCloseHandler backPressCloseHandler;  // 뒤로가기 버튼용 변수
    private var Collected_Food = 0
    private var Collected_Water = 0
    private var View_Collected_Food = 0
    private var View_Collected_Water = 0
    var Temp_Step_Taken = 0
    var Steps_View: TextView? = null
    var Collected_Food_View: TextView? = null
    var Collected_Water_View: TextView? = null
    var Data_Box: SharedPreferences? = null
    // 만보기 변수선언 투척
    private var lastTime: Long = 0
    private var speed = 0f
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var x = 0f
    private var y = 0f
    private var z = 0f
    private var sensorManager: SensorManager? = null
    private var accelerormeterSensor: Sensor? = null
    //////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game4_activity_run)
        // ActionBar Hide!
        supportActionBar!!.hide()
        //backPressCloseHandler = new BackPressCloseHandler(this);  // 뒤로가기 버튼용 변수
        Collected_Water = 0
        Collected_Food = Collected_Water
        Temp_Step_Taken = Collected_Food
        View_Collected_Water = 0
        View_Collected_Food = View_Collected_Water
        Data_Box = applicationContext.getSharedPreferences(
            "Data_Box",
            Context.MODE_PRIVATE
        )
        Image_Setting()
        Initiating_Views()
        //// 만보기 기능 투척 ////
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerormeterSensor =
            sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        Steps_View = findViewById<View>(R.id.Steps_Taken_View_Id) as TextView
        Steps_View!!.text = "" + Temp_Step_Taken
        /////////////////////////
    }

    //// 만보기 함수 투척 ////
    public override fun onStart() {
        super.onStart()
        if (accelerormeterSensor != null) sensorManager!!.registerListener(
            this, accelerormeterSensor,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    public override fun onStop() {
        super.onStop()
        if (sensorManager != null) sensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            val gabOfTime = currentTime - lastTime
            if (gabOfTime > 120) {
                lastTime = currentTime
                x = event.values[SensorManager.DATA_X]
                y = event.values[SensorManager.DATA_Y]
                z = event.values[SensorManager.DATA_Z]
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000
                if (speed > SHAKE_THRESHOLD) {
                    Temp_Step_Taken++
                    if (Temp_Step_Taken % 100 == 0) {
                        Collected_Food = Collected_Food + 2
                        Collected_Water++
                    } else {
                        Collected_Food = 0
                        Collected_Water = 0
                    }
                    Put_And_Update()
                }
                lastX = event.values[DATA_X]
                lastY = event.values[DATA_Y]
                lastZ = event.values[DATA_Z]
            }
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
    }

    // ↑ 여기까지가 만보기 관련 함수
/////////////////////////////////////////////////////////////////////
    private fun Initiating_Views() {
        val Chronometer_View = findViewById<Chronometer>(R.id.Chronometer_View_Id)
        Steps_View = findViewById(R.id.Steps_Taken_View_Id)
        Collected_Food_View = findViewById(R.id.Collected_Food_View_Id)
        Collected_Water_View = findViewById(R.id.Collected_Water_View_Id)
        val Click_Button =
            findViewById<Button>(R.id.Run_Button_Id)
        val End_Walk =
            findViewById<Button>(R.id.Stop_walk_Button_Id)
        Put_And_Update()
        Chronometer_View.base = SystemClock.elapsedRealtime()
        Chronometer_View.start()
        // 마우스 클릭
        Click_Button.setOnClickListener {
            Temp_Step_Taken++
            if (Temp_Step_Taken % 100 == 0) {
                Collected_Food = Collected_Food + 2
                Collected_Water++
            } else {
                Collected_Food = 0
                Collected_Water = 0
            }
            Put_And_Update()
        }
        // 걷기 종료를 누른 경우
        End_Walk.setOnClickListener {
            var Temp_Day = Data_Box!!.getInt("Day", 0)
            Temp_Day++ // 다음 날짜로 ++
            // 1-1. 배고픔, 목마름 수치 받아오기
            var Temp_One_Hungry = Data_Box!!.getInt("Family_One_Hungry", 100)
            var Temp_Two_Hungry = Data_Box!!.getInt("Family_Two_Hungry", 100)
            var Temp_One_Thirst = Data_Box!!.getInt("Family_One_Thirst", 100)
            var Temp_Two_Thirst = Data_Box!!.getInt("Family_Two_Thirst", 100)
            // 1-2. 데미지 수치 받아오기
            var Temp_Global_Damage = Data_Box!!.getInt("Global_Damage", -1)
            var Temp_Family_One_Damage = Data_Box!!.getInt("Family_One_Damage", -1)
            var Temp_Family_Two_Damage = Data_Box!!.getInt("Family_Two_Damage", -1)
            var Temp_One_Hp = Data_Box!!.getInt("Family_One_Hp", 100)
            var Temp_Two_Hp = Data_Box!!.getInt("Family_Two_Hp", 100)
            // 21. 배고픔, 목마름 마이너스 시키기 위해 랜덤 함수 호출
            val r = Random()
            val Random_One_Value1 = r.nextInt(20 - 10) + 10 // 배고프면 한 5일 버티려나
            val Random_One_Value2 = r.nextInt(33 - 10) + 10 // 목마르면 한 3일 버티려나
            val Random_Two_Value1 = r.nextInt(20 - 10) + 10
            val Random_Two_Value2 = r.nextInt(33 - 10) + 10
            // 3-1. 배고픔, 목마름 마이너스 시키기
            Temp_One_Hungry = Temp_One_Hungry - Random_One_Value1
            Temp_Two_Hungry = Temp_Two_Hungry - Random_Two_Value1
            Temp_One_Thirst = Temp_One_Thirst - Random_One_Value2
            Temp_Two_Thirst = Temp_Two_Thirst - Random_Two_Value2
            // 3-2. 데미지 마이너스
            Temp_Global_Damage = Temp_Global_Damage - Temp_Day
            Temp_Family_One_Damage = Temp_Family_One_Damage - Temp_Day
            Temp_Family_Two_Damage = Temp_Family_Two_Damage - Temp_Day
            Temp_One_Hp = Temp_One_Hp + Temp_Family_One_Damage
            Temp_Two_Hp = Temp_Two_Hp + Temp_Family_Two_Damage
            // 4. Day +1, 갱신된 배고픔과 목마름, 데미지 숫자 삽입!!
            val editor = Data_Box!!.edit()
            editor.putInt("Day", Temp_Day)
            editor.putInt("Family_One_Hungry", Temp_One_Hungry)
            editor.putInt("Family_Two_Hungry", Temp_Two_Hungry)
            editor.putInt("Family_One_Thirst", Temp_One_Thirst)
            editor.putInt("Family_Two_Thirst", Temp_Two_Thirst)
            editor.putInt("Family_One_Hp", Temp_One_Hp)
            editor.putInt("Family_Two_Hp", Temp_Two_Hp)
            editor.putInt("Global_Damage", Temp_Global_Damage)
            editor.putInt("Family_One_Damage", Temp_Family_One_Damage)
            editor.putInt("Family_Two_Damage", Temp_Family_Two_Damage)
            editor.commit()
            // 화면 전환
            startActivity(Intent(this@Game4_Run, Game1Actvity::class.java))
            finish()
        }
    }

    private fun Put_And_Update() {
        View_Collected_Food = View_Collected_Food + Collected_Food
        View_Collected_Water = View_Collected_Water + Collected_Water
        Steps_View!!.text = Temp_Step_Taken.toString() + ""
        Collected_Food_View!!.text = View_Collected_Food.toString() + ""
        Collected_Water_View!!.text = View_Collected_Water.toString() + ""
        var Food = Data_Box!!.getInt("Food", 1)
        var Water = Data_Box!!.getInt("Water", 1)
        Food = Food + Collected_Food
        Water = Water + Collected_Water
        val editor = Data_Box!!.edit()
        editor.putInt("Food", Food)
        editor.putInt("Water", Water)
        editor.commit()
    }

    private fun Image_Setting() {
        val Day = Data_Box!!.getInt("Day", 0)
        val Images_Array = resources.obtainTypedArray(R.array.Images_Data)
        val Image_Description =
            resources.getStringArray(R.array.Image_Description)
        val Status_Image =
            findViewById<ImageView>(R.id.WalkImage_View_Id)
        val Status_Image_Description = findViewById<TextView>(R.id.WalkImage_Des_View_Id)
        Status_Image.setImageResource(Images_Array.getResourceId(Day, -1))
        Status_Image_Description.text = Image_Description[Day]
        Images_Array.recycle()
    }

    /*
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        startActivity(new Intent(Game4_Run.this, Game2_Main.class));
        finish();
    }
*/
// 뒤로가기 입력을 하는 경우.
    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle("알림")
        builder.setMessage("앱을 종료하시겠습니까?")
        builder.setNegativeButton("취소", null)
        builder.setPositiveButton("종료") { dialog, which ->
            // 다이얼로그의 긍정 이벤트일 경우 종료한다.
            finish()
            Process.killProcess(Process.myPid())
        }
        builder.show()
    }

    companion object {
        private const val SHAKE_THRESHOLD = 800
        private const val DATA_X = SensorManager.DATA_X
        private const val DATA_Y = SensorManager.DATA_Y
        private const val DATA_Z = SensorManager.DATA_Z
    }
}