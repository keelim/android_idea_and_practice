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
import com.keelim.practice1.model.LostViewData;
import com.keelim.practice1.utils.DataSaver;
import com.keelim.practice1.utils.LostView_Adapter;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class LostViewActivity extends AppCompatActivity implements View.OnClickListener {
    DataSaver saver;
    TextView view_webview, tel;
    ListView listview;
    LostView_Adapter adapter;
    ArrayList<LostViewData> arrayList;

    TextView lost_view_item_name;
    TextView lost_view_item_id;
    TextView lost_view_item_status;
    TextView lostview_webview;
    TextView lostview_tel;
    String lostview_webview_url;
    String lostview_tel_num;
    ImageView imageView;
    RelativeLayout noimage;
    Thread thread;
    Handler confirmHandler;
    ProgressDialog dialog;

    Intent goView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_view);
        lost_view_item_name = findViewById(R.id.lost_view_item_name);
        lost_view_item_id = findViewById(R.id.lost_view_item_id);
        lost_view_item_status = findViewById(R.id.lost_view_item_status);
        lostview_webview = findViewById(R.id.lostview_webview);
        lostview_tel = findViewById(R.id.lostview_tel);
        saver = new DataSaver(getApplicationContext());
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
        lostview_tel_num = goView.getStringExtra("contact").replace(": ", "");
        lostview_tel.setText(lostview_tel_num);
        //여기에는 리스트 액티비티에서 주는 값 받아서 넣어주렴
        if (!goView.getStringExtra("thing").trim().equals(""))
            arrayList.add(new LostViewData("내용", goView.getStringExtra("thing")));
        if (!goView.getStringExtra("take_place").trim().equals(""))
            arrayList.add(new LostViewData("분실물 수령 가능한 곳", goView.getStringExtra("take_place")));
        if (!goView.getStringExtra("date").trim().equals(""))
            arrayList.add(new LostViewData("분실물을 습득한 날짜", goView.getStringExtra("date")));
        if (!goView.getStringExtra("place").trim().equals(""))
            arrayList.add(new LostViewData("분실물을 습득한 곳", goView.getStringExtra("place")));
        if (!goView.getStringExtra("position").trim().equals(""))
            arrayList.add(new LostViewData("분실물을 습득한 곳의 회사명", goView.getStringExtra("position")));
        adapter = new LostView_Adapter(getApplicationContext(), arrayList);
        listview.setAdapter(adapter);
        if ((!goView.getStringExtra("image_url").equals("aaa.jpg")) && (!goView.getStringExtra("image_url").equals(""))) {
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

    private void setDefault() {
        listview = findViewById(R.id.lost_view_listview);
        tel = findViewById(R.id.lostview_tel);
        tel.setOnClickListener(this);
        lostview_webview.setOnClickListener(this);
        view_webview = findViewById(R.id.lostview_webview);
        imageView = findViewById(R.id.imageview);
        noimage = findViewById(R.id.noimage);
        confirmHandler = new Handler() {
            @Override
            public void handleMessage(@NotNull Message msg) {
            }
        };
        dialog = new ProgressDialog(LostViewActivity.this);
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
                if (saver.checkData(goView.getStringExtra("id"))) {
                    Toast.makeText(getApplicationContext(), "이미 저장하였습니다.", Toast.LENGTH_SHORT).show();
                } else {
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
        switch (v.getId()) {
            case R.id.lostview_webview: {
                Intent goWeb = new Intent(getApplicationContext(), WebViewActivity.class);
                goWeb.putExtra("url", goView.getStringExtra("url"));
                startActivity(goWeb);
                break;
            }

            case R.id.lostview_tel: {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("연락처로전화")
                        .setPositiveButton("확인", (dialog1, which) -> {
                            Intent goTell = new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + lostview_tel_num));
                            startActivity(goTell);
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show();

                break;
            }
        }
    }

    private void setImageView(final String uri) {
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
}
