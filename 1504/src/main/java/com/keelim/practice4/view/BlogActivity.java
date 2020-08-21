package com.keelim.practice4.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.keelim.practice4.R;

/**
 * Created by MinJae on 2015-10-27.
 */
public class BlogActivity extends AppCompatActivity {

    WebView webView;
    ImageView imageView_back;
    TextView textView_title;
    String url;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
        title = intent.getStringExtra("TITLE");

        setView();
        setListener();

        textView_title.setText(title);

        webView.getSettings().setJavaScriptEnabled(true);      // 웹뷰에서 자바 스크립트 사용
        webView.loadUrl(url);            // 웹뷰에서 불러올 URL 입력
        webView.setWebViewClient(new WebViewClientClass());    // client 연결
    }

    void setView() {
        webView = (WebView) findViewById(R.id.blogActivity_webView);
        imageView_back = (ImageView) findViewById(R.id.blogActivity_backBtn);
        textView_title = (TextView) findViewById(R.id.blogActivity_title);
    }

    void setListener() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}