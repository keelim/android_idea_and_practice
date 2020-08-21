package com.keelim.practice1.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.keelim.practice1.R;
import com.keelim.practice1.model.SaveViewData;
import com.keelim.practice1.utils.SaveViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SaveViewActivity extends AppCompatActivity implements View.OnClickListener {
    TextView view_webview, tel;
    ListView listview;
    SaveViewAdapter adapter;
    ArrayList<SaveViewData> arrayList;
    Handler confirmHandler;
    ProgressDialog dialog;
    Thread thread;

    TextView lost_view_item_name;
    TextView lost_view_item_id;
    TextView lost_view_item_status;
    TextView lostview_webview;
    TextView lostview_tel;
    String lostview_webview_url;
    String lostview_tel_num;
    RelativeLayout noimage;
    ImageView imageView;

    Intent goView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_view);
        lost_view_item_name = findViewById(R.id.lost_view_item_name);
        lost_view_item_id = findViewById(R.id.lost_view_item_id);
        lost_view_item_status = findViewById(R.id.lost_view_item_status);
        lostview_webview = findViewById(R.id.lostview_webview);
        lostview_tel = findViewById(R.id.lostview_tel);
        goView = getIntent();

        setActionbar(getSupportActionBar());
        setDefault();
        setData();
    }

    private void setData() {
        arrayList = new ArrayList<>();
        lost_view_item_name.setText(goView.getStringExtra("title"));
        lost_view_item_id.setText(goView.getStringExtra("id"));
        lost_view_item_status.setText(goView.getStringExtra("type"));
        lostview_webview_url = goView.getStringExtra("url");
        if (!goView.getStringExtra("contact").replace(": ", "").trim().equals("")) {
            lostview_tel_num = goView.getStringExtra("contact").replace(": ", "");
        } else {
            lostview_tel_num = "전화 번호 정보 없음";
        }
        lostview_tel.setText(lostview_tel_num);
        //여기에는 리스트 액티비티에서 주는 값 받아서 넣어주렴
        if (!goView.getStringExtra("thing").trim().equals(""))
            arrayList.add(new SaveViewData("내용", goView.getStringExtra("thing")));
        if (!goView.getStringExtra("take_place").trim().equals(""))
            arrayList.add(new SaveViewData("분실물 수령 가능한 곳", goView.getStringExtra("take_place")));
        if (!goView.getStringExtra("date").trim().equals(""))
            arrayList.add(new SaveViewData("분실물을 습득한 날짜", goView.getStringExtra("date")));
        if (!goView.getStringExtra("place").trim().equals(""))
            arrayList.add(new SaveViewData("분실물을 습득한 곳", goView.getStringExtra("place")));
        if (!goView.getStringExtra("position").trim().equals(""))
            arrayList.add(new SaveViewData("분실물을 습득한 곳의 회사명", goView.getStringExtra("position")));
        adapter = new SaveViewAdapter(getApplicationContext(), arrayList);
        listview.setAdapter(adapter);
        if ((!goView.getStringExtra("image_url").equals("")) && (!goView.getStringExtra("image_url").equals("aaa.jpg"))) {
            noimage.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            setImageView(goView.getStringExtra("image_url"));
        }

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

    private void setImageView(final String uri) {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("...");
        dialog.show();
        Runnable task = () -> {
            try {
                URL url = new URL(uri.trim());
                URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                bis.close();
                imageView.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
            confirmHandler.sendEmptyMessage(0);
            dialog.dismiss();
        };
        thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            //TODO
        }
    }

    private void setDefault() {
        listview = findViewById(R.id.lost_view_listview);
        tel = findViewById(R.id.lostview_tel);
        tel.setOnClickListener(this);
        lostview_webview.setOnClickListener(this);
        view_webview = findViewById(R.id.lostview_webview);
        noimage = findViewById(R.id.noimage);
        imageView = findViewById(R.id.imageview);
        confirmHandler = new Handler() {
            @Override
            public void handleMessage(@NotNull Message msg) {
            }
        };
        dialog = new ProgressDialog(SaveViewActivity.this);
    }


    public void setActionbar(ActionBar actionbar) {
        actionbar.setTitle("저장된 물건");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lostview_webview: {
                if (!goView.getStringExtra("url").trim().equals("")) {
                    Intent goWeb = new Intent(getApplicationContext(), WebViewActivity.class);
                    goWeb.putExtra("url", goView.getStringExtra("url"));
                    startActivity(goWeb);
                } else {
                    Toast.makeText(getApplicationContext(), "링크 정보가 없습니다. 여기에서 찾아보세요", Toast.LENGTH_SHORT).show();
                    Intent goInfo = new Intent(getApplicationContext(), FindInfoActivitiy.class);
                    startActivity(goInfo);
                }
                break;
            }

            case R.id.lostview_tel: {
                if (lostview_tel_num.equals("전화 번호 정보 없음")) {
                    Toast.makeText(getApplicationContext(), "전화 번호가 없습니다. 여기에서 찾아보세요", Toast.LENGTH_SHORT).show();
                    Intent goInfo = new Intent(getApplicationContext(), CenterInfoActivity.class);
                    startActivity(goInfo);
                } else {

                    new MaterialAlertDialogBuilder(this)
                            .setTitle("연락처로 전화")
                            .setPositiveButton("확인", (dialog1, which) -> {
                                Intent goTell = new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + lostview_tel_num));
                                startActivity(goTell);
                            })
                            .create()
                            .show();

                    break;
                }
            }
        }
    }
}
