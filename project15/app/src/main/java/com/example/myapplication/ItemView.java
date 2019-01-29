package com.example.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;

    public ItemView(Context context) {
        super(context);

        init(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context){
        //인플레이션을 통하여 시스템 서비스를 참고한다.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item, this, true);

        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

    }

    public void setName(String name){
        textView1.setText(name);
    }

    public void setMobile(String mobile){
        textView2.setText(mobile);
    }
}
