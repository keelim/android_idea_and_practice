package com.keelim.practice10.model.adapter;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keelim.practice10.view.BasketActivity;
import com.keelim.practice10.model.Clothes;
import com.keelim.practice10.R;
import com.keelim.practice10.model.ServerImg;
import com.keelim.practice10.model.Basket;
import com.keelim.practice10.model.BasketItem;

import java.util.ArrayList;
import java.util.Locale;

public class BasketRecyclerAdapter extends RecyclerView.Adapter<BasketRecyclerAdapter.ViewHolder> {
    private Context context;
    private Basket basket;
    private ArrayList<BasketItem> basketItem;

    public BasketRecyclerAdapter(Context context) {
        this.context = context;
        this.basket = Basket.getInstance();
        this.basketItem = basket.getBasket();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.component_basket_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        BasketItem basketItem = this.basketItem.get(position);
        Clothes clothes = basketItem.getClothes();
        // 옷 이미지 가져옴
        ServerImg.getClothesImageGlide(context, clothes.getCloth_id(), holder.image);

        holder.delBtn.setOnClickListener((android.widget.Button.OnClickListener) v -> showAlert(position));

        holder.name.setText(clothes.getName());
        holder.count.setText("" + basketItem.getCnt());
        holder.layout.setId(clothes.getCloth_id());
    }

    @Override
    public int getItemCount() {
        return this.basket.getClothesCnt();
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

    private void showAlert(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String alertMsg, okBtnMsg, cancelBtnMsg;
        if(Locale.getDefault().getLanguage() == "ko") {
            alertMsg = "이 한복을 장바구니에서 빼시겠습니까?";
            okBtnMsg = "예";
            cancelBtnMsg = "아니오";
        }
        else {
            alertMsg = "Would you like to remove this Hanbok from your basket?";
            okBtnMsg = "Yes";
            cancelBtnMsg = "No";
        }

        builder.setMessage(alertMsg);
        builder.setPositiveButton(okBtnMsg,
                (dialog, which) -> {
                    basket.deleteClothes(position);
                    notifyDataSetChanged();
                    ((BasketActivity)context).setTotalCost();
                    ((BasketActivity) context).setTotalClothesCnt();
                });
        builder.setNegativeButton(cancelBtnMsg,
                (dialog, which) -> {

                });
        builder.show();
    }
}