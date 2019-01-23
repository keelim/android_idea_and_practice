package com.example.myapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar abar = getSupportActionBar();
        abar.hide();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //안드로이드의 기본 메뉴
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //menu   item클릭이 되었을 때
        int curlid = item.getItemId();
        switch (curlid) {
            case R.id.menu_refresh:
                Toast.makeText(this, "새로고침 메뉴 클릭됨", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_search:
                Toast.makeText(this, "검색메뉴 클릭됨", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_settings:
                Toast.makeText(this, "셋팅메뉴 클릭됨", Toast.LENGTH_LONG).show();
                break;
                default:
                    break;
        }

        return super.onOptionsItemSelected(item);
    }
}
