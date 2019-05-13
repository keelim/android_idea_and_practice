package com.keelim.DatatoObject.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.layout.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivimg, ivLike, ivShare;
    public TextView tvLike, tvUserName, tvPost;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        ivimg = itemView.findViewById(R.id.iv_img);
        ivLike = itemView.findViewById(R.id.iv_like);
        ivShare = itemView.findViewById(R.id.iv_share);

        tvLike = itemView.findViewById(R.id.iv_likecount);
        tvUserName = itemView.findViewById(R.id.tv_username);
        tvPost = itemView.findViewById(R.id.tv_posttext);
    }
}
