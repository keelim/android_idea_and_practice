package com.keelim.practice10.view.fragment;

import android.annotation.SuppressLint;
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
import com.keelim.practice10.model.Order;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.model.adapter.OrderListRecyclerAdapter;
import com.keelim.practice10.utils.JSONTask;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class OrderFragmentManagementFragment2 extends Fragment{
    private ArrayList<Order> originItems, items;
    @Nullable
    private OrderListRecyclerAdapter mAdapter = null;

    private Store store;
    public static boolean changeFlg = false;

    public OrderFragmentManagementFragment2(@NonNull Store store) {
        this.store = store;
        originItems = JSONTask.getInstance().getOrderAdminAll(store.getAdmin_id());
        this.items = new ArrayList<Order>();

        for (Order item:originItems) {
            if(item.getAcceptStatus()==1 || item.getAcceptStatus()==2)
                items.add(item);
        }
        for (Order item:items)
            item.setBasket(JSONTask.getInstance().getBascketCustomerAll(item.getId()));
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    @NonNull
    public static OrderFragmentManagementFragment2 newInstance(int sectionNumber, @NonNull Store store) {
        OrderFragmentManagementFragment2 fragment = new OrderFragmentManagementFragment2(store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_fragment_order, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_order_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new OrderListRecyclerAdapter(getContext(), items, store);
        recyclerView.setAdapter(mAdapter);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            dataUpdate();
            refresh();
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(changeFlg) {
            dataUpdate();
            changeFlg = false;
        }
    }

    public void dataUpdate(){
        originItems = JSONTask.getInstance().getOrderAdminAll(store.getAdmin_id());
        this.items = new ArrayList<Order>();

        for (Order item:originItems) {
            if(item.getAcceptStatus()==1 || item.getAcceptStatus()==2)
                items.add(item);
        }
        for (Order item:items)
            item.setBasket(JSONTask.getInstance().getBascketCustomerAll(item.getId()));

        mAdapter.setOrders(items);
    }

    public void refresh(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
