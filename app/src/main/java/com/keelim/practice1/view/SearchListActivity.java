package com.keelim.practice1.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.keelim.practice1.R;
import com.keelim.practice1.model.SearchData;
import com.keelim.practice1.utils.SearchData_Adapter;
import com.keelim.practice1.utils.SearchParser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
    ListView listview;
    SearchData_Adapter adapter;
    SearchParser searchParser;
    ArrayList<SearchData> arrayList;
    Intent asdf;
    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_list);
        setActionbar(getSupportActionBar());
        asdf = getIntent();
        Log.e("tts", asdf.getStringExtra("type") + " " + asdf.getStringExtra("cate"));
        searchParser = new SearchParser(asdf.getStringExtra("type"), asdf.getStringExtra("cate"), asdf.getStringExtra("value")); //파서가 가져올 거는 이것들이다!
        Log.e("tts", asdf.getStringExtra("type") + " " + asdf.getStringExtra("cate"));
        setDefault();
        setData();
    }

    private void setData() {
        Log.e("T", "cc1");

        arrayList = new ArrayList<>();
        searchParser.initData();  //파서 안의 어레이를 초기화 하겠음
        searchParser.loadData(1, setting.getInt("max", 500));  //1번에서 20번까지 가져올 거임
        Log.e("T", "cc2");
        arrayList = searchParser.getArrayList();  //가져온 데이터를 어레이에 투척
        Log.e("T", "cc3");
        adapter = new SearchData_Adapter(getApplicationContext(), arrayList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            TextView title = view.findViewById(R.id.lostlist_listview_title);
            TextView content = view.findViewById(R.id.lostlist_listview_content);
            TextView type = view.findViewById(R.id.lostlist_listview_type);
            TextView id_ = view.findViewById(R.id.lostlist_listview_id);
            TextView url = view.findViewById(R.id.lostlist_listview_url);
            TextView date = view.findViewById(R.id.lostlist_listview_date);
            TextView take_place = view.findViewById(R.id.lostlist_listview_take_plcae);
            TextView contact = view.findViewById(R.id.lostlist_listview_contact);
            TextView position0 = view.findViewById(R.id.lostlist_listview_position);
            TextView plcae = view.findViewById(R.id.lostlist_listview_place);
            TextView thing = view.findViewById(R.id.lostlist_listview_thing);
            TextView image_url = view.findViewById(R.id.lostlist_listview_image_url);

            Intent goView = new Intent(getApplicationContext(), SearchViewActivity.class);
            goView.putExtra("title", title.getText().toString());
            goView.putExtra("content", content.getText().toString());
            goView.putExtra("type", type.getText().toString());
            goView.putExtra("id", id_.getText().toString());
            goView.putExtra("url", url.getText().toString());
            goView.putExtra("date", date.getText().toString());
            goView.putExtra("take_place", take_place.getText().toString());
            goView.putExtra("contact", contact.getText().toString());
            goView.putExtra("position", position0.getText().toString());
            goView.putExtra("place", plcae.getText().toString());
            goView.putExtra("thing", thing.getText().toString());
            goView.putExtra("image_url", image_url.getText().toString());
            startActivity(goView);
        });
    }

    private void setDefault() {
        listview = findViewById(R.id.listview);
        setting = getSharedPreferences("setting", MODE_PRIVATE);
    }

    public void setActionbar(ActionBar actionbar) {
        asdf = getIntent();
        actionbar.setTitle(asdf.getStringExtra("value") + "에 대한 검색 결과");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lostlist, menu);
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
