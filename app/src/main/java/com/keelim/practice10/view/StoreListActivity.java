package com.keelim.practice10.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.model.adapter.StoreListRecyclerAdapter;
import com.keelim.practice10.utils.JSONTask;

import java.util.ArrayList;
import java.util.Objects;

public class StoreListActivity extends AppCompatActivity {
    ArrayList<Store> stores;
    StoreListRecyclerAdapter mAdapter;
    int sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        LinearLayout basket = (LinearLayout)findViewById(R.id.store_basket);
        basket.setOnClickListener(v -> {
            Intent intent = new Intent(StoreListActivity.this, BasketActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_store_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        // 구역에 따른 store 목록 가져오기
        Intent intent = getIntent();
        sector = (int)intent.getIntExtra("sector", 1);
        stores = JSONTask.getInstance().getStoreBySector(sector);

        mAdapter = new StoreListRecyclerAdapter(this, stores);
        recyclerView.setAdapter(mAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout_store_list);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mAdapter.setStores(JSONTask.getInstance().getStoreBySector(sector));
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
