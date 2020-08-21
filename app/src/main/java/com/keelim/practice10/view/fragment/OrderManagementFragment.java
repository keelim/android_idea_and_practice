package com.keelim.practice10.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;



import com.keelim.practice10.R;
import com.keelim.practice10.model.Store;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class OrderManagementFragment extends Fragment {
    private OrderFragmentManagementFragment fm1;
    private OrderFragmentManagementFragment2 fm2;

    private Store store;

    public OrderManagementFragment(Store store) {
        this.store = store;
    }


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private static final String ARG_SECTION_NUMBER = "section_number";
    @NonNull
    public static OrderManagementFragment newInstance(int sectionNumber, Store store) {
        OrderManagementFragment fragment = new OrderManagementFragment(store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_order, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                }
                else if(position == 1) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

            switch(position) {
                case 0:
                    return fm1 = OrderFragmentManagementFragment.newInstance(0, store);
                case 1:
                    return fm2 = OrderFragmentManagementFragment2.newInstance(1, store);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // 탭 개수
            return 2;
        }
    }

    public void refresh(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}