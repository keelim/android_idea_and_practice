package com.keelim.practice4.view.fragmnets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.keelim.practice4.R;
import com.keelim.practice4.view.StoreAcitvity;
import com.keelim.practice4.model.adapter.SearchStoreAdapter;
import com.keelim.practice4.model.StoreForm;
import com.keelim.practice4.network.ReceiveSearchStore;


/**
 * Created by HyunWoo on 2015-10-28.
 */
public class Fragment2_fragment2 extends Fragment {

    View view;
    ListView listView_searchStore;
    SearchStoreAdapter searchStoreAdapter;
    String tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2_fragment2, container, false);

        searchStoreAdapter = new SearchStoreAdapter(view.getContext());

        Bundle extra = getArguments();
        tag = extra.getString("TAG");

        setView();

        ReceiveSearchStore receiveSearchStore = new ReceiveSearchStore(view.getContext(), listView_searchStore, tag);
        receiveSearchStore.execute();

        setListener();

        return view;
    }

    void setView() {
        listView_searchStore = (ListView)view.findViewById(R.id.fragment2_fragment2_listView);
    }

    void setListener() {
        listView_searchStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreForm storeForm = (StoreForm) parent.getAdapter().getItem(position);

                Intent intent = new Intent(view.getContext(), StoreAcitvity.class);
                intent.putExtra("STORE", storeForm);
                startActivity(intent);
            }
        });
    }
}