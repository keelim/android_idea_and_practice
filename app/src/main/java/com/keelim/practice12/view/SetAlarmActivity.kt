package com.keelim.practice12.view

import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.keelim.practice12.R
import com.keelim.practice12.model.room.DBManagerHandler
import com.keelim.practice12.view.SetAlarmActivity

class SetAlarmActivity : AppCompatActivity() {
    var select_sound_button: Button? = null
    var select_sound_time_button: Button? = null
    var select_volume_button: Button? = null
    var radio_song1: RadioButton? = null
    var radio_song2: RadioButton? = null
    var radio_song3: RadioButton? = null
    var radio_time1: RadioButton? = null
    var radio_time2: RadioButton? = null
    var radio_time3: RadioButton? = null
    var mp: MediaPlayer? = null
    var am: AudioManager? = null
    var seekbar: SeekBar? = null
    var selected_volume = 0
    var selected_siren: String? = null
    var selected_time = 0
    var handler: DBManagerHandler? = null
    var max_voulume = 0
    var aDialog_siren: AlertDialog.Builder? = null
    var aDialog_volume: AlertDialog.Builder? = null
    var aDialog_time: AlertDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
        handler = DBManagerHandler(applicationContext)
        if (handler!!.getCountByTableName("siren") < 1) {
            selected_siren = "aaa"
            selected_volume = 30
            selected_time = 1
            handler!!.insert4("siren", selected_siren!!, selected_volume, selected_time)
        }
        val cursor: Cursor
        cursor = handler!!.select_quick("siren")
        cursor.moveToFirst()
        selected_siren = cursor.getString(cursor.getColumnIndex("siren"))
        selected_time = cursor.getInt(cursor.getColumnIndex("time"))
        selected_volume = cursor.getInt(cursor.getColumnIndex("size"))
        am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        max_voulume = am!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        mp = if (selected_siren == "1") {
            MediaPlayer.create(this@SetAlarmActivity, R.raw.aa)
        } else if (selected_siren == "2") {
            MediaPlayer.create(this@SetAlarmActivity, R.raw.bb)
        } else {
            MediaPlayer.create(this@SetAlarmActivity, R.raw.cc)
        }
        select_sound_button = findViewById<View>(R.id.select_sound) as Button
        select_sound_time_button = findViewById<View>(R.id.select_sound_time) as Button
        select_volume_button = findViewById<View>(R.id.alarm_volume) as Button
        select_sound_button!!.setOnClickListener(View.OnClickListener { view: View? ->
            val mContext = applicationContext
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
            val layout = inflater.inflate(R.layout.pop_siren, findViewById<View>(R.id.popupid_siren) as ViewGroup)
            aDialog_siren = AlertDialog.Builder(this@SetAlarmActivity)
            aDialog_siren!!.setTitle("Siren") //타이틀바 제목
            aDialog_siren!!.setView(layout) //dialog.xml 파일을 뷰로 셋팅
            //팝업창 생성
            val ad_siren = aDialog_siren!!.create()
            ad_siren.show() //보여줌!
            val send_but = ad_siren.findViewById<View>(R.id.pop_siren_send) as Button
            val cancel_but = ad_siren.findViewById<View>(R.id.pop_siren_close) as Button
            send_but.setOnClickListener { view112: View? ->
                //
                handler!!.modify("siren", selected_siren, selected_time, selected_volume)
                if (mp != null) {
                    mp!!.release()
                }
                //am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND);
                ad_siren.dismiss()
            }
            //닫기
            cancel_but.setOnClickListener { view111: View? ->
                if (mp != null) {
                    mp!!.release()
                }
                //am.setStreamVolume(AudioManager.STREAM_MUSIC, 0,AudioManager.FLAG_PLAY_SOUND);
                ad_siren.dismiss()
            }
            radio_song1 = ad_siren.findViewById<View>(R.id.song1) as RadioButton
            radio_song2 = ad_siren.findViewById<View>(R.id.song2) as RadioButton
            radio_song3 = ad_siren.findViewById<View>(R.id.song3) as RadioButton
            am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            // 첫번째 버튼이 눌렸을 경우
            radio_song1!!.setOnClickListener { view110: View? ->
                if (mp != null) {
                    mp!!.release()
                }
                radio_song2!!.isChecked = false
                radio_song3!!.isChecked = false
                selected_siren = "1"
                mp = MediaPlayer.create(this@SetAlarmActivity, R.raw.aa)
                am!!.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND)
                mp.start()
            }
            radio_song2!!.setOnClickListener { view19: View? ->
                selected_siren = "2"
                if (mp != null) {
                    mp!!.release()
                }
                radio_song1!!.isChecked = false
                radio_song3!!.isChecked = false
                mp = MediaPlayer.create(this@SetAlarmActivity, R.raw.bb)
                am!!.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND)
                mp.start()
            }
            radio_song3!!.setOnClickListener { view18: View? ->
                selected_siren = "3"
                if (mp != null) {
                    mp!!.release()
                }
                radio_song2!!.isChecked = false
                radio_song1!!.isChecked = false
                mp = MediaPlayer.create(this@SetAlarmActivity, R.raw.cc)
                am!!.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND)
                mp.start()
            }
        })
        select_sound_time_button!!.setOnClickListener(View.OnClickListener { view: View? ->
            val mContext = applicationContext
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
            val layout = inflater.inflate(R.layout.pop_time, findViewById<View>(R.id.popupid_time) as ViewGroup)
            aDialog_time = AlertDialog.Builder(this@SetAlarmActivity)
            aDialog_time!!.setTitle("Time") //타이틀바 제목
            aDialog_time!!.setView(layout) //dialog.xml 파일을 뷰로 셋팅
            //팝업창 생성
            val ad_time = aDialog_time!!.create()
            ad_time.show() //보여줌!
            val send_but = ad_time.findViewById<View>(R.id.pop_time_send) as Button
            val cancel_but = ad_time.findViewById<View>(R.id.pop_time_close) as Button
            // SUBMIT
            send_but.setOnClickListener { view17: View? ->
                handler!!.modify("siren", selected_siren, selected_time, selected_volume)
                ad_time.dismiss()
            }
            // CLOSE
            cancel_but.setOnClickListener { view16: View? -> ad_time.dismiss() }
            radio_time1 = ad_time.findViewById<View>(R.id.time1) as RadioButton
            radio_time2 = ad_time.findViewById<View>(R.id.time2) as RadioButton
            radio_time3 = ad_time.findViewById<View>(R.id.time3) as RadioButton
            // 첫번째 버튼이 눌렸을 경우
            radio_time1!!.setOnClickListener { view15: View? ->
                selected_time = 5
                radio_time2!!.isChecked = false
                radio_time3!!.isChecked = false
            }
            radio_time2!!.setOnClickListener { view14: View? ->
                selected_time = 10
                radio_time1!!.isChecked = false
                radio_time3!!.isChecked = false
            }
            radio_time3!!.setOnClickListener { view13: View? ->
                selected_time = 15
                radio_time1!!.isChecked = false
                radio_time2!!.isChecked = false
            }
        })
        select_volume_button!!.setOnClickListener(View.OnClickListener { view: View? ->
            val mContext = applicationContext
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
            val layout = inflater.inflate(R.layout.volume_popup, findViewById<View>(R.id.volume_popup_id) as ViewGroup)
            aDialog_volume = AlertDialog.Builder(this@SetAlarmActivity)
            aDialog_volume!!.setTitle("Volume") //타이틀바 제목
            aDialog_volume!!.setView(layout) //dialog.xml 파일을 뷰로 셋팅
            //팝업창 생성
            val ad_volume = aDialog_volume!!.create()
            ad_volume.show() //보여줌!
            val send_but = ad_volume.findViewById<View>(R.id.send_volume) as Button
            val cancel_but = ad_volume.findViewById<View>(R.id.close_volume) as Button
            send_but.setOnClickListener { view12: View? ->
                handler!!.modify("siren", selected_siren, selected_time, selected_volume)
                if (mp != null) {
                    mp!!.release()
                }
                // debug용
//                        Cursor cursor;
//                        cursor=handler.select_quick("siren");
//                        cursor.moveToFirst();
//                        Log.d("db", cursor.getString(cursor.getColumnIndex("size")));
//                        Log.d("db2",String.valueOf(selected_volume));
                ad_volume.dismiss()
            }
            cancel_but.setOnClickListener { view1: View? ->
                if (mp != null) {
                    mp!!.release()
                }
                ad_volume.dismiss()
            }
            seekbar = ad_volume.findViewById<View>(R.id.seekBar) as SeekBar
            seekbar!!.max = 100
            seekbar!!.incrementProgressBy(1)
            val cursor1: Cursor
            cursor1 = handler!!.select_quick("siren")
            cursor1.moveToFirst()
            val progressed = cursor1.getInt(cursor1.getColumnIndex("size"))
            seekbar!!.progress = progressed
            seekbar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    selected_volume = progress
                    am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    am!!.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND)
                    if (mp != null) {
                        mp!!.release()
                    }
                    mp = if (selected_siren == "1") {
                        MediaPlayer.create(this@SetAlarmActivity, R.raw.aa)
                    } else if (selected_siren == "2") {
                        MediaPlayer.create(this@SetAlarmActivity, R.raw.bb)
                    } else {
                        MediaPlayer.create(this@SetAlarmActivity, R.raw.cc)
                    }
                    mp.start()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mp != null) {
            mp!!.release()
        }
    }
}