package com.keelim.DatatoObject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.layout.R;
import com.keelim.DatatoObject.RecyclerView.PostViewHolder;
import com.keelim.DatatoObject.model.PostItem;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context mContext;
    private ArrayList<PostItem> postItems;

    public PostAdapter(Context context) {
        mContext = context;

    }

    public PostAdapter(Context context, ArrayList<PostItem> listItem) {
        mContext =context;
        postItems = listItem;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View baseView = View.inflate(mContext,R.layout.post_item, null);
        PostViewHolder postViewHolder = new PostViewHolder(baseView, this);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {
        PostItem item = postItems.get(position);
        postViewHolder.tvUserName.setText(item.getUserName());
        postViewHolder.tvPost.setText(item.getPostText());
        postViewHolder.tvLike.setText(String.valueOf(item.getPostLikeCount()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void onLikeClicked(int position) {
        PostItem item = postItems.get(position);
        Toast.makeText(mContext, position + " " + item.getPostText(), Toast.LENGTH_SHORT).show();
    }
}
