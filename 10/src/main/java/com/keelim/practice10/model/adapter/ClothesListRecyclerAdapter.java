package com.keelim.practice10.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keelim.practice10.view.ClothesReservationActivity;
import com.keelim.practice10.R;
import com.keelim.practice10.model.ServerImg;
import com.keelim.practice10.model.Store;
import com.keelim.practice10.model.Clothes;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class ClothesListRecyclerAdapter extends RecyclerView.Adapter<ClothesListRecyclerAdapter.ViewHolder> {
    Context context;
    List<Clothes> clothes;
    int item_layout;
    Store store;

    public ClothesListRecyclerAdapter(Context context, List<Clothes> items, Store store, int item_layout) {
        this.context = context;
        this.clothes = items;
        this.store = store;
        this.item_layout = item_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_item_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Clothes item = clothes.get(position);

        // 옷 이미지 가져옴
        ServerImg.getClothesImageGlide(context, item.getCloth_id(), holder.image);

        holder.name.setText(item.getName());
        DecimalFormat dc = new DecimalFormat("###,###,###,###");
        if(Locale.getDefault().getLanguage()=="ko")
            holder.price.setText(dc.format(item.getPrice()) + " 원");
        else
            holder.price.setText(dc.format(item.getPrice()) + " won");
        int clothesCnt = item.getCount();
        Drawable itemForegroundColor;
        if(clothesCnt == 0) {
            int color = 0x7f000000;
            itemForegroundColor = new ColorDrawable(color);
        }
        else{
            int color = 0x00000000;
            itemForegroundColor = new ColorDrawable(color);
        }
        holder.cardview.setForeground(itemForegroundColor);

        holder.cardview.setId(item.getCloth_id());
        holder.cardview.setOnClickListener(v -> {
            Intent intent = new Intent(context, ClothesReservationActivity.class);
            intent.putExtra("clothes", item);
            intent.putExtra("store", store);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.clothes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.clothes_name);
            price = (TextView) itemView.findViewById(R.id.clothes_price);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public void setClothes(List<Clothes> clothes) {
        this.clothes = clothes;
        this.notifyDataSetChanged();
    }
}