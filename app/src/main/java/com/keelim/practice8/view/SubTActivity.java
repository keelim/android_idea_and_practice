package com.keelim.practice8.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.keelim.practice8.R;
import com.keelim.practice8.model.CustomChoiceListViewAdapter;
import com.keelim.practice8.model.db.InquryDBCtrct;
import com.keelim.practice8.model.db.InquryDBHelper;


public class SubTActivity extends AppCompatActivity {

    Menu mMenu;
    TextView textView;
    ListView listview;
    CustomChoiceListViewAdapter adapter;

    String[] Sid;

    @Nullable
    String[] LIST_MENU;

    @Nullable
    InquryDBHelper dbHelper = null;

    private void delete_values() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(InquryDBCtrct.SQL_DELETE);
        LIST_MENU = null;
        adapter.notifyDataSetChanged();
        onResume();
    }

    private void load_values() {

        adapter = new CustomChoiceListViewAdapter();
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(InquryDBCtrct.SQL_SELECT, null);
        int count = cursor.getCount();
        Sid = new String[count];
        //LIST_MENU = new String[count];

        for (int i = 0; i < count; i++) {
            cursor.moveToNext();
            Sid[i] = cursor.getString(0);
            adapter.add_Item(cursor.getString(2), cursor.getString(1), cursor.getString(4), cursor.getString(3));

        }
        //textView.setText(" 조회한 예약 정보 수 : " + count);

        if (count == 0) {
            TextView textView2 = (TextView) findViewById(R.id.textView2);
            //textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
        }
    }

    private void init_tables() {
        dbHelper = new InquryDBHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // textView = (TextView) findViewById(R.id.textView);
        init_tables();
        load_values();


        listview.setOnItemClickListener((parent, v, position, id) -> {

            //Toast.makeText(getApplicationContext(), LIST_MENU[position], Toast.LENGTH_SHORT).show();
            // get TextView's Text.
            new AlertDialog.Builder(SubTActivity.this)
                    .setTitle("예약")
                    // .setIcon(R.drawable.order)
                    .setMessage("예약 창으로 이동하시겠습니까?")
                    .setPositiveButton("예약하러가기", (dialog, which) -> {

                        String url = "http://yeyak.seoul.go.kr/mobile/detailView.web?rsvsvcid=" + Sid[position];
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);

                    })
                    .setNegativeButton("취소", null)
                    .show();


        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inqury_list);

        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F39700")));
        getSupportActionBar().setTitle("최근 본 예약");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == android.R.id.home) {
            //Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }


        if (id == R.id.action_delete) {
            //Toast.makeText(this, "delete클릭", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(SubTActivity.this)
                    .setTitle("조회 정보 삭제")
                    // .setIcon(R.drawable.order)
                    .setMessage("조회 정보를 삭제하시겠습니까?")
                    .setPositiveButton("삭제하기", (dialog, which) -> delete_values())
                    .setNegativeButton("취소", null)
                    .show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
