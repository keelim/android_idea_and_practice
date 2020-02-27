package com.keelim.practice10.view;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.keelim.practice10.R;
import com.keelim.practice10.utils.CreateQRCode;

public class MyQRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //뒤로가기
        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        int reserveID = intent.getIntExtra("reservationID", -1);

        // error
        if(reserveID == -1){
            finish();
            return;
        }

        CreateQRCode createQRCode = new CreateQRCode();
        Bitmap bitmap = createQRCode.createQRCode(reserveID);
        loadQRCode(bitmap);
    }

    public void loadQRCode(Bitmap bitmap){
        ImageView imageView = findViewById(R.id.qrcode);
        Glide.with(this).load(bitmap)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter())
                .into(imageView);
    }
}
