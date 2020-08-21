package com.keelim.practice1.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.keelim.practice1.R;
import com.keelim.practice1.model.LostListData;
import com.keelim.practice1.utils.ListParser;
import com.keelim.practice1.utils.LostList_Adapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LostListActivity extends AppCompatActivity {

    ListView listview;
    LostList_Adapter adapter;
    ListParser listParser;
    ArrayList<LostListData> arrayList;
    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_list);
        setActionbar(getSupportActionBar());
        Intent asdf = getIntent();
        listParser = new ListParser(asdf.getStringExtra("type")); //파서가 가져올 거는 s1임
        setDefault();
        setData();
    }

    private void setData() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("데이터를 로드 합니다.")
                .create()
                .show();

        arrayList = new ArrayList<>();
        listParser.initData();  //파서 안의 어레이를 초기화 하겠음
        listParser.loadData(1, setting.getInt("max", 500));  //1번에서 20번까지 가져올 거임
        arrayList = listParser.getArrayList();  //가져온 데이터를 어레이에 투척
        adapter = new LostList_Adapter(getApplicationContext(), arrayList);
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

            Intent goView = new Intent(getApplicationContext(), LostViewActivity.class);
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
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        listview = findViewById(R.id.listview);
    }

    public void setActionbar(ActionBar actionbar) {
        actionbar.setTitle("잃어버린 물품");
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
