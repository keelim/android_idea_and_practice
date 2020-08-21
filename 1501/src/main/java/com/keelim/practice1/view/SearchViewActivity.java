package com.keelim.practice1.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.keelim.practice1.R;
import com.keelim.practice1.model.SearchViewData;
import com.keelim.practice1.utils.DataSaver;
import com.keelim.practice1.utils.SearchView_Adapter;

import org.jetbrains.annotations.NotNull;

public class SearchViewActivity extends AppCompatActivity implements View.OnClickListener{
    DataSaver saver;
    TextView tel;
    LinearLayout contact;
    ListView listview;
    SearchView_Adapter adapter;
    ArrayList<SearchViewData> arrayList;

    TextView lost_view_item_name;
    TextView lost_view_item_id;
    TextView lost_view_item_status;

    Intent goView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        lost_view_item_name = findViewById(R.id.lost_view_item_name);
        lost_view_item_id = findViewById(R.id.lost_view_item_id);
        lost_view_item_status = findViewById(R.id.lost_view_item_status);
        goView = getIntent();
        saver = new DataSaver(getApplicationContext());
        setActionbar(getSupportActionBar());
        setDefault();
        setData();
    }

    private void setData() {
        arrayList = new ArrayList<>();
        lost_view_item_name.setText(goView.getStringExtra("title"));
        lost_view_item_id.setText(goView.getStringExtra("id"));
        if(!goView.getStringExtra("type").trim().equals("")) {
            lost_view_item_status.setText(goView.getStringExtra("type"));
        }else{
            lost_view_item_status.setText("분실");
        }
        //여기에는 리스트 액티비티에서 주는 값 받아서 넣어주렴
        if (!goView.getStringExtra("thing").trim().equals(""))
            arrayList.add(new SearchViewData("내용", goView.getStringExtra("thing")));
        if (!goView.getStringExtra("take_place").trim().equals(""))
            arrayList.add(new SearchViewData("분실물 수령 가능한 곳", goView.getStringExtra("take_place")));
        if (!goView.getStringExtra("date").trim().equals(""))
            arrayList.add(new SearchViewData("분실물을 습득한 날짜", goView.getStringExtra("date")));
        if (!goView.getStringExtra("place").trim().equals(""))
            arrayList.add(new SearchViewData("분실물을 습득한 곳", goView.getStringExtra("place")));
        if (!goView.getStringExtra("position").trim().equals(""))
            arrayList.add(new SearchViewData("분실물을 습득한 곳의 회사명", goView.getStringExtra("position")));
        adapter = new SearchView_Adapter(getApplicationContext(), arrayList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener((parent, view, position, id) -> {
            TextView asdf = view.findViewById(R.id.lostview_listview_title);
            switch (asdf.getText().toString().trim()) {
                case "분실물 수령 가능한 곳": {
                    Intent goWeb = new Intent(getApplicationContext(), WebViewActivity.class);
                    goWeb.putExtra("url", "http://maps.google.com/maps?f=d&saddr=&daddr=" + goView.getStringExtra("take_place") + "&hl=ko");
                    startActivity(goWeb);
                }
            }
        });
    }

    private void setDefault() {
        listview = findViewById(R.id.lost_view_listview);
        contact = findViewById(R.id.contact);
        contact.setOnClickListener(v -> {
            Intent goContact = new Intent(getApplicationContext(),FindInfoActivitiy.class);
            startActivity(goContact);
        });
        tel = findViewById(R.id.lostview_tel);
        tel.setOnClickListener(this);
    }


    public void setActionbar(ActionBar actionbar) {
        actionbar.setTitle("잃어버린 물품");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lostview, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                if (saver.checkData(goView.getStringExtra("id"))){
                    Toast.makeText(getApplicationContext(),"이미 저장하였습니다.",Toast.LENGTH_SHORT).show();
                } else{
                    saver.SetData(
                            goView.getStringExtra("title"),
                            goView.getStringExtra("content"),
                            goView.getStringExtra("type"),
                            goView.getStringExtra("id"),
                            goView.getStringExtra("url"),
                            goView.getStringExtra("date"),
                            goView.getStringExtra("take_place"),
                            goView.getStringExtra("contact"),
                            goView.getStringExtra("position"),
                            goView.getStringExtra("place"),
                            goView.getStringExtra("thing"),
                            goView.getStringExtra("image_url")
                    );
                    Toast.makeText(getApplicationContext(), "저장됨", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lostview_tel) {
            Intent goGuide = new Intent(getApplicationContext(), CenterInfoActivity.class);
            startActivity(goGuide);
        }
    }
}
