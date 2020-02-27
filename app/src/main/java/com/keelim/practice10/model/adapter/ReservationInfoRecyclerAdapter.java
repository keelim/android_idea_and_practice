package com.keelim.practice10.model.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keelim.practice10.R;
import com.keelim.practice10.model.ServerImg;
import com.keelim.practice10.model.BasketItem;
import com.keelim.practice10.model.Clothes;

import java.util.ArrayList;

public class ReservationInfoRecyclerAdapter extends RecyclerView.Adapter<ReservationInfoRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BasketItem> clothes;

    public ReservationInfoRecyclerAdapter(Context context, ArrayList<BasketItem> clothes) {
        this.context = context;
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.component_basket_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        BasketItem basketItem = this.clothes.get(position);
        Clothes clothes = basketItem.getClothes();
        // 옷 이미지 가져옴
        ServerImg.getClothesImageGlide(context, clothes.getCloth_id(), holder.image);

        holder.delBtn.setVisibility(View.INVISIBLE);

        holder.name.setText(clothes.getName());
        holder.count.setText("" + basketItem.getCnt());
        holder.layout.setId(clothes.getCloth_id());
    }

    @Override
    public int getItemCount() {
        return this.clothes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, delBtn;
        TextView name, count;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.basket_clothes_image);
            name = (TextView) itemView.findViewById(R.id.basket_clothes_name);
            count = (TextView) itemView.findViewById(R.id.basket_clothes_cnt);
            layout = (LinearLayout) itemView.findViewById(R.id.basket_clothes_layout);
            delBtn = (ImageView) itemView.findViewById(R.id.basket_clothes_delete_btn);
        }
    }
}