package com.keelim.practice10.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Constant;
import com.keelim.practice10.model.ServerImg;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.view.fragment.StoreClothesFragment;
import com.keelim.practice10.view.fragment.StoreInfoFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class StoreActivity extends AppCompatActivity {
    @Nullable
    private Store store;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView manTextView, womanTextView;
    private int sex;

    private StoreClothesFragment storeClothesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_store);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // 옷 성별 선택 메뉴
        final LinearLayout sexMenu = (LinearLayout)findViewById(R.id.store_select_sex_menu);
        LinearLayout man = toolbar.findViewById(R.id.store_clothes_sex_man);
        manTextView = toolbar.findViewById(R.id.store_clothes_sex_man_text);

        LinearLayout woman = toolbar.findViewById(R.id.store_clothes_sex_woman);
        womanTextView = toolbar.findViewById(R.id.store_clothes_sex_woman_text);

        man.setOnClickListener(v -> {
            sex = Constant.MAN;
            manTextView.setTextColor(v.getResources().getColor(R.color.blue));
            manTextView.setBackground(v.getResources().getDrawable(R.drawable.border_bottom_layout_item_thick_blue));

            womanTextView.setTextColor(Color.parseColor("#aaaaaa"));
            womanTextView.setBackground(null);
            refresh();
        });

        woman.setOnClickListener(v -> {
            sex = Constant.WOMAN;
            womanTextView.setTextColor(v.getResources().getColor(R.color.red));
            womanTextView.setBackground(v.getResources().getDrawable(R.drawable.border_bottom_layout_item_thick_red));

            manTextView.setTextColor(Color.parseColor("#aaaaaa"));
            manTextView.setBackground(null);
            refresh();
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_clothes);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // 탭 페이지 전환 설정
        TabLayout tabLayout = (TabLayout) findViewById(R.id.store_tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                mViewPager.setCurrentItem(tabPosition);
                if(tabPosition == 0){
                    sexMenu.setVisibility(View.INVISIBLE);
                }
                else if(tabPosition == 1){
                    sexMenu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayout gotoBasket = (LinearLayout) findViewById(R.id.store_basket);
        gotoBasket.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, BasketActivity.class);
            startActivity(intent);
        });

        // 상점정보 가져옴
        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");

        // 툴바 타이틀 이름 상점이름으로 변경
        TextView titleName = (TextView) findViewById(R.id.toolbar_title);
        titleName.setText(store.getName());

        // 상점 메인 사진 변경
        ImageView storeImg = (ImageView)findViewById(R.id.store_img);
        ServerImg.getStoreImageGlide(getApplicationContext(), store.getId(), storeImg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_store, menu);
        return false;
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return StoreInfoFragment.newInstance(0, store);
                case 1:
                    return storeClothesFragment = StoreClothesFragment.newInstance(1, store);
            }
            return null;
        }


        public int getCount () {
            return 2;
        }
    }

    public int getSelectedSex() {
        return sex;
    }

    private void refresh(){
        storeClothesFragment.refresh();
    }


}



