package com.keelim.practice1.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.keelim.practice1.R;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String type, cate;
    Spinner lostType, lostSpace;
    Toolbar toolbar;
    ImageView search;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    LinearLayout subway_1, subway_2, subway_3, subway_4;
    LinearLayout subway_img_1;
    LinearLayout subway_img_2;
    LinearLayout subway_img_3;
    LinearLayout subway_img_4;
    LinearLayout[] subway_img;
    LinearLayout bus_1, bus_2;
    LinearLayout taxi_1, taxi_2;
    LinearLayout main_tile_subway_1, main_tile_subway_2, main_tile_subway_3, main_tile_subway_4, main_tile_bus_1, main_tile_bus_2, main_tile_taxi_1, main_tile_taxi_2;
    EditText edittext_search;
    SharedPreferences setting;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        setSpinner();
        setDefault();
        if (setting.getBoolean("no_display", true)) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("인터넷에 연결되지 않았거나 속도가 느릴 경우 앱이 작동하지 않을 수 있습니다.\n지나치게 로딩이 느린 경우 유동적으로 설정을 변경해주세요.")
                    .setPositiveButton("확인", null)
                    .setNegativeButton("다시보지 않기", (dialog, which) -> {
                        editor.putBoolean("no_display", false);
                        editor.commit();
                    })
                    .create()
                    .show();
        }

    }

    private void setDefault() {
        navigationView = findViewById(R.id.navigation_view);
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        save = getSharedPreferences("save", MODE_PRIVATE);
        editor = setting.edit();
        editor2 = save.edit();
        search = findViewById(R.id.main_search);
        subway_1 = findViewById(R.id.main_tile_subway_1);
        subway_2 = findViewById(R.id.main_tile_subway_2);
        subway_3 = findViewById(R.id.main_tile_subway_3);
        subway_4 = findViewById(R.id.main_tile_subway_4);
        bus_1 = findViewById(R.id.main_tile_bus_1);
        bus_2 = findViewById(R.id.main_tile_bus_2);
        taxi_1 = findViewById(R.id.main_tile_taxi_1);
        taxi_2 = findViewById(R.id.main_tile_taxi_2);
        subway_img_1 = findViewById(R.id.main_tile_subway_images_1);
        subway_img_2 = findViewById(R.id.main_tile_subway_images_2);
        subway_img_3 = findViewById(R.id.main_tile_subway_images_3);
        subway_img_4 = findViewById(R.id.main_tile_subway_images_4);
        subway_img = new LinearLayout[]{subway_img_1, subway_img_2, subway_img_3, subway_img_4};
        main_tile_subway_1 = findViewById(R.id.main_tile_subway_1);
        main_tile_subway_2 = findViewById(R.id.main_tile_subway_2);
        main_tile_subway_3 = findViewById(R.id.main_tile_subway_3);
        main_tile_subway_4 = findViewById(R.id.main_tile_subway_4);
        main_tile_bus_1 = findViewById(R.id.main_tile_bus_1);
        main_tile_bus_2 = findViewById(R.id.main_tile_bus_2);
        main_tile_taxi_1 = findViewById(R.id.main_tile_taxi_1);
        main_tile_taxi_2 = findViewById(R.id.main_tile_taxi_2);
        edittext_search = findViewById(R.id.edittext_search);


        search.setOnClickListener(v -> {
            Log.e("tt", type + " " + cate);
            Intent goSearch = new Intent(getApplicationContext(), SearchListActivity.class);
            goSearch.putExtra("type", type);
            goSearch.putExtra("cate", cate);
            goSearch.putExtra("value", edittext_search.getText().toString().trim());
            startActivity(goSearch);
        });
        if (getResources().getDisplayMetrics().density <= 1.5) {
            for (LinearLayout linearLayout : subway_img) linearLayout.setVisibility(View.GONE);
        }
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.drawer_view_save:
                    startActivity(new Intent(getApplicationContext(), SaveListActivity.class));
                    break;
                case R.id.drawer_info_1:
                    startActivity(new Intent(getApplicationContext(), CenterInfoActivity.class));
                    break;
                case R.id.drawer_info_2:
                    startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                    break;
                case R.id.drawer_find_webview:
                    startActivity(new Intent(getApplicationContext(), FindInfoActivitiy.class));
                    break;
                case R.id.drawer_setting:
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                    break;
                case R.id.reset:
                    editor.clear();
                    editor.commit();
                    editor2.clear();
                    editor2.commit();
                    Toast.makeText(getApplicationContext(), "앱이 종료되면 다시 실행해주세요", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
            return false;
        });
        main_tile_subway_1.setOnClickListener(this);
        main_tile_subway_2.setOnClickListener(this);
        main_tile_subway_3.setOnClickListener(this);
        main_tile_subway_4.setOnClickListener(this);
        main_tile_bus_1.setOnClickListener(this);
        main_tile_bus_2.setOnClickListener(this);
        main_tile_taxi_1.setOnClickListener(this);
        main_tile_taxi_2.setOnClickListener(this);
    }

    private void setSpinner() {
        String[] lostTypeData = new String[]{"가방", "기타", "베낭", "서류봉투", "쇼핑백", "옷", "지갑", "책", "파일", "핸드폰"};
        String[] lostSpaceData = new String[]{"버스", "마을버스", "1~4호선", "5~8호선", "코레일", "9호선", "법인택시", "개인택시"};
        ArrayAdapter<String> lostTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_textstyle, lostTypeData);
        ArrayAdapter<String> lostSpaceAdapter = new ArrayAdapter<>(this, R.layout.spinner_textstyle, lostSpaceData);
        lostType = findViewById(R.id.lost_type);
        lostSpace = findViewById(R.id.lost_space);
        lostSpaceAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        lostTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        lostType.setAdapter(lostTypeAdapter);
        lostSpace.setAdapter(lostSpaceAdapter);
        lostType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) lostType.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lostSpace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((String) lostSpace.getSelectedItem()) {
                    case "버스":
                        cate = "b1";
                        break;
                    case "마을버스":
                        cate = "b2";
                        break;
                    case "1~4호선":
                        cate = "s1";
                        break;
                    case "5~8호선":
                        cate = "s2";
                        break;
                    case "코레일":
                        cate = "s3";
                        break;
                    case "9호선":
                        cate = "s4";
                        break;
                    case "법인택시":
                        cate = "t1";
                        break;
                    case "개인택시":
                        cate = "t2";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setActionBar() {
        toolbar = findViewById(R.id.toolbar);
        dlDrawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //            case R.id.drawer_view_save:
        //                Intent goSave = new Intent(getApplicationContext(), SaveListActivity.class);
        //                startActivity(goSave);
        //                break;
        //            case R.id.drawer_info_1:
        //                Intent goContact = new Intent(getApplicationContext(), CenterInfoActivity.class);
        //                startActivity(goContact);
        //                break;
        //            case R.id.drawer_info_2: {
        //                Intent goInfo = new Intent(getApplicationContext(), InfoActivity.class);
        //                startActivity(goInfo);
        //                break;
        //            }
        //            case R.id.drawer_find_webview: {
        //                Intent goFindInfo = new Intent(getApplicationContext(), FindInfoActivitiy.class);
        //                startActivity(goFindInfo);
        //                break;
        //            }
        //            case R.id.drawer_setting: {
        //                Intent goSetting = new Intent(getApplicationContext(), SettingActivity.class);
        //                startActivity(goSetting);
        //                break;
        //            }
        if (item.getItemId() == android.R.id.home) {
            dlDrawer.openDrawer(GravityCompat.START);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tile_subway_1:
                type = "s1";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_subway_2:
                type = "s2";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_subway_3:
                type = "s3";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_subway_4:
                type = "s4";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_bus_1:
                type = "b1";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_bus_2:
                type = "b2";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_taxi_1:
                type = "t2";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
            case R.id.main_tile_taxi_2:
                type = "t1";
                startActivity(new Intent(getApplicationContext(), LostListActivity.class).putExtra("type", type));
                break;
        }
    }
}
