package com.keelim.practice1.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.rey.material.widget.Slider;

import java.util.ArrayList;

import com.keelim.practice1.R;
import com.keelim.practice1.model.SettingData;
import com.keelim.practice1.utils.Setting_Adapter;

import org.jetbrains.annotations.NotNull;

public class SettingActivity extends AppCompatActivity {
    private TextView setting_title;
    private TextView comment;
    private ListView listview;
    private Setting_Adapter adapter;
    private ArrayList<SettingData> arrayList;
    private Intent goSetting;
    private Slider sl_discrete;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        goSetting = getIntent();

        setActionbar(getSupportActionBar());
        setDefault();
        setData();
    }

    private void setData() {
        arrayList = new ArrayList<>();
        //여기에는 리스트 액티비티에서 주는 값 받아서 넣어주렴

        adapter = new Setting_Adapter(getApplicationContext(), arrayList);
        listview.setAdapter(adapter);
        arrayList.add(new SettingData("대표/기획/개발", "김수한", "으아아아앙"));
        arrayList.add(new SettingData("기획", "김영환", "nullnull"));
        arrayList.add(new SettingData("개발", "오준석", "으아아아앙"));
        arrayList.add(new SettingData("개발", "안준혁", "으아아아앙"));
        arrayList.add(new SettingData("디자인", "구창림", "구창구창"));

        listview.setOnItemClickListener((parent, view, position, id) -> comment.setText(arrayList.get(position).getComment()));
    }

    private void setDefault() {
        listview = findViewById(R.id.listview);
        comment = findViewById(R.id.comment);
        setting_title = findViewById(R.id.setting_title);
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();
        sl_discrete = findViewById(R.id.slider_sl_discrete);
        sl_discrete.setValue(setting.getInt("max",500)/10,true);
        sl_discrete.setOnPositionChangeListener((view, fromUser, oldPos, newPos, oldValue, newValue) -> {
            setting_title.setText("한번에 불러오는 글 개수 설정( " + newValue * 10 + "개 )");
            if(newValue!=0) {
                editor.putInt("max", newValue * 10);
                editor.commit();
            }else{
                Toast.makeText(getApplicationContext(),"0으로 설정할 수 없습니다.",Toast.LENGTH_SHORT).show();
                editor.putInt("max", 500);
                editor.commit();
            }
            Log.e("tts", String.format("pos=%.1f value=%d", newPos, newValue));
        });
    }

    public void setActionbar(ActionBar actionbar) {
        actionbar.setTitle("설정");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}