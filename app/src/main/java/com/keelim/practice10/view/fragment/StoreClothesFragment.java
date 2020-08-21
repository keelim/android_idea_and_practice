package com.keelim.practice10.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Clothes;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.model.adapter.ClothesCategoryListRecyclerAdapter;
import com.keelim.practice10.model.adapter.ClothesListRecyclerAdapter;
import com.keelim.practice10.utils.JSONTask;
import com.keelim.practice10.view.StoreActivity;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class StoreClothesFragment extends Fragment {
    private ArrayList<Clothes> originItems;
    private ClothesListRecyclerAdapter mAdapter;
    private ClothesCategoryListRecyclerAdapter cAdapter;
    private Store store;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public StoreClothesFragment(@NonNull Store store) {
        this.store = store;
        //판매중인 옷 목록 가져옴
        this.originItems = JSONTask.getInstance().getClothesAll(store.getAdmin_id());
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @NonNull
    public static StoreClothesFragment newInstance(int sectionNumber, @NonNull Store store) {
        StoreClothesFragment fragment = new StoreClothesFragment(store);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_store_clothes_list, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_clothes);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //옷추가

        // 성별로 분류
        ArrayList<Clothes> tmp = new ArrayList<>();
        int sex = ((StoreActivity)getContext()).getSelectedSex();
        for(Clothes item : originItems){
            if(item.getSex() == sex)
                tmp.add(item);
        }

        mAdapter = new ClothesListRecyclerAdapter(getContext(), tmp, store, R.layout.fragment_store_clothes_list);
        recyclerView.setAdapter(mAdapter);

        // clothes category 설정
        RecyclerView recyclerViewCategory = (RecyclerView) rootView.findViewById(R.id.clothes_category);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(layoutManagerCategory);

        cAdapter = new ClothesCategoryListRecyclerAdapter(getContext(), originItems, mAdapter);
        recyclerViewCategory.setAdapter(cAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.refresh_layout_clothes_list);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            //mAdapter.setClothes(JSONTask.getInstance().getClothesAll(store.getAdmin_id()));
            originItems = JSONTask.getInstance().getClothesAll(store.getAdmin_id());
            swipeRefreshLayout.setRefreshing(false);
            refresh();
        });

        return rootView;
    }

    public void refresh(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}