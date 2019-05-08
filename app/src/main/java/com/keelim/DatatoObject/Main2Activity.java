package com.keelim.DatatoObject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.layout.R;
import com.keelim.DatatoObject.model.PostItem;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<PostItem> listItem = new ArrayList<>();
        LinearLayout llscrollParent = findViewById(R.id.scroll);

        for (int i = 0; i < 5; i++) {
            PostItem item = new PostItem("keelim", false, 120, "", "wow!");
            listItem.add(item);
        }

        for (PostItem item : listItem) {
            View v = View.inflate(this, R.layout.post_item, null);
            TextView user = v.findViewById(R.id.tv_username);
            TextView postText = v.findViewById(R.id.tv_posttext);

            user.setText(item.getUserName());
            postText.setText(item.getPostText());

            llscrollParent.addView(v);

        }


    }


}
