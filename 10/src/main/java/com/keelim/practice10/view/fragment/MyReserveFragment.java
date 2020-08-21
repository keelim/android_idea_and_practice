package com.keelim.practice10.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Account;
import com.keelim.practice10.model.Reserve;
import com.keelim.practice10.model.adapter.ReserveListRecyclerAdapter;
import com.keelim.practice10.utils.JSONTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyReserveFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    @Nullable
    private ArrayList<Reserve> reserves = null;
    @Nullable
    private ReserveListRecyclerAdapter mAdapter = null;
    public static boolean changeFlg = false;
    private View rootView;
    private RecyclerView recyclerView;
    private boolean isAlreadyLoad;

    public MyReserveFragment() {
        isAlreadyLoad = false;
    }

    @NonNull
    public static MyReserveFragment newInstance(int sectionNumber) {
        MyReserveFragment fragment = new MyReserveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_reserve, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_reserve_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if(reserves == null)
            reserves = new ArrayList<>();
        mAdapter = new ReserveListRecyclerAdapter(getContext(), reserves);
        recyclerView.setAdapter(mAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout_reserve_list);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            reserves = getReserves();
            swipeRefreshLayout.setRefreshing(false);
            refresh();
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 현재 탭 일경우 로딩
        if (isVisibleToUser && !isAlreadyLoad) {
            isAlreadyLoad = true;

            reserves = getReserves();
            mAdapter.setReserves(reserves);
        }
    }

    private ArrayList<Reserve> getReserves() {
        ArrayList<Reserve> reserves = JSONTask.getInstance().getCustomerReservationList(Account.getInstance().getId());
        Descending descending = new Descending();
        Collections.sort(reserves, descending);
        return reserves;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (changeFlg) {
            refresh();
            changeFlg = false;
        }
    }

    private void refresh() {
        reserves = getReserves();
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    private class Descending implements Comparator<Reserve> {
        @Override
        public int compare(@NonNull Reserve o1, @NonNull Reserve o2) {
            return o2.compareTo(o1);
        }
    }
}
