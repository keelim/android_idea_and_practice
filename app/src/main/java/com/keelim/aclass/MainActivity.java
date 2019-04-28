package com.keelim.aclass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.layout.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pager);



        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Red());
        fragments.add(new Yellow());
        fragments.add(new Green());


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments); //을 받아준다.
        viewPager.setAdapter(adapter);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) { //posiion을 찾아준다.
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }


}
