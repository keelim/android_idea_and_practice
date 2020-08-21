package com.keelim.DatatoObject.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.layout.R;
import com.keelim.DatatoObject.PostAdapter;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView ivimg, ivLike, ivShare;
    public TextView tvLike, tvUserName, tvPost;
    private PostAdapter postAdapter;

    public PostViewHolder(@NonNull View itemView, PostAdapter postAdapter) {
        super(itemView);
        this.postAdapter = postAdapter;
        ivimg = itemView.findViewById(R.id.iv_img);
        ivLike = itemView.findViewById(R.id.iv_like);
        ivShare = itemView.findViewById(R.id.iv_share);
        tvLike = itemView.findViewById(R.id.iv_likecount);
        tvUserName = itemView.findViewById(R.id.tv_username);
        tvPost = itemView.findViewById(R.id.tv_posttext);

        ivLike.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        switch (v.getId()){
            case R.id.iv_like:
                postAdapter.onLikeClicked(position);
                break;

            case R.id.iv_share:

                break;
        }

    }
}
