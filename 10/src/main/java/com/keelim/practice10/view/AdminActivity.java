package com.keelim.practice10.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.utils.JSONTask;
import com.keelim.practice10.view.fragment.ItemManagementFragment;
import com.keelim.practice10.view.fragment.OrderManagementFragment;
import com.keelim.practice10.view.fragment.StoreManagementFragment;
import com.google.android.material.tabs.TabLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
     private StoreManagementFragment storeManagementFragment;
     private Store store;
     private TextView textView;

     private final long FINISH_INTERVAL_TIME = 2000;
     private long backPressedTime = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_adminpage);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        LinearLayout myPageBtn = (LinearLayout)findViewById(R.id.myPage);
        myPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AdminMyPageActivity.class);
            intent.putExtra("store", store);
            startActivity(intent);
        });

        LinearLayout qrScan = (LinearLayout)findViewById(R.id.adminpage_qr);
        qrScan.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, QRScanActivity.class);
            startActivity(intent);
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    //storeManagementFragment.refresh();
                }
                else if (position == 1) {
                    //itemManagementFragment.refresh();
                }
                else if(position == 2) {
                    //orderManagementFragment.refresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        // 로그인된 매장 주인의 매장정보를 가져옴
        this.store = JSONTask.getInstance().getAdminStoreAll(JSONTask.getInstance().getLoginID()).get(0);

        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(store.getName());
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
            finish();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 카메라 전환시 변경된 방향을 원래대로 바꿈
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            newConfig.orientation = Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_example_swipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return storeManagementFragment = StoreManagementFragment.newInstance(0, store);
                case 1:
                    return ItemManagementFragment.newInstance(1, store);
                case 2:
                    return OrderManagementFragment.newInstance(2, store);
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            // 탭 개수
            return 3;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (storeManagementFragment != null)
            ((StoreManagementFragment) storeManagementFragment).onActivityResult(requestCode, resultCode, data);
    }

    public void setToolbarTitle(String name){
        textView.setText(name);
    }
}
