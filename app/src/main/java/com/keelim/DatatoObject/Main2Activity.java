package com.keelim.DatatoObject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.layout.R;
import com.keelim.DatatoObject.model.PostItem;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ImageView images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ArrayList<PostItem> listItem = new ArrayList<>();
        RecyclerView rvList =  findViewById(R.id.rv_list);
        for (int i = 0; i < 5; i++) {
            PostItem item = new PostItem("keelim", true, 120, "", "wow!");
            listItem.add(item);
        }

        PostAdapter adapter = new PostAdapter(this, listItem);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        rvList.setAdapter(adapter);



        startLoadingimage();
    }

    private void startLoadingimage() {
        String url = "sdsd";
    }


}
