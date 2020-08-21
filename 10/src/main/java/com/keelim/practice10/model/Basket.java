package com.keelim.practice10.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.keelim.practice10.view.BasketActivity;
import com.keelim.practice10.view.ClothesReservationActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Basket {
    @NonNull
    private static Basket instance = new Basket();
    private ArrayList<BasketItem> basket;
    private int selectedStoreID;
    private String rentalDate;  // 형태  yyyy-mm-dd hh:mm:ss

    private Basket(){
        basket = new ArrayList<>();
        selectedStoreID = -1;
        rentalDate = "2018-08-13";
    }

    @NonNull
    public static synchronized Basket getInstance(){
        return instance;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public int getClothesCnt(){
        return basket.size();
    }

    public int getSelectedStoreID() {
        return selectedStoreID;
    }

    public ArrayList<BasketItem> getBasket() {
        return basket;
    }

    public void clearBasket(){
        basket.clear();
        selectedStoreID = -1;
    }

    public void addClothes(@NonNull Context context, @NonNull BasketItem item) {
        addClothes(context, item, 0);
    }

    // btn 0: 장바구니 담기, 1: 대여하기
    public void addClothes(@NonNull final Context context, @NonNull BasketItem item, int btn){
        /*
         * 1. 선택된 가게가 없을때
         *   - 담기
         * 2. 선택된 가게가 있을때
         *   - 담을려고 하는 옷의 가게와 동일한경우
         *     - 담기
         *   - 다른 경우
         *     - client에게 기존 목록 지울껀지 묻고 담기
         *
         */
        if(basket.size() == 0){
            basket.add(item);
            selectedStoreID = item.getClothes().getStore_id();
            closeReservationActivity(context);
            openBasketActivity(context, btn);
        }
        else {
            if(selectedStoreID == item.getClothes().getStore_id()){
                basket.add(item);
                closeReservationActivity(context);
                openBasketActivity(context, btn);
            }
            else {
                showAlert(context, item, btn);
            }
        }
    }

    private void showAlert(@NonNull final Context context, @NonNull final BasketItem item, final int btn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String alertMsg, okBtnMsg, cancelBtnMsg;
        if(Locale.getDefault().getLanguage() == "ko"){
            alertMsg = "장바구니에는 한 매장의 한복만 담을수 있습니다\n" +
                    "장바구니를 비우고 이 한복을 담으시겠습니까?";
            okBtnMsg = "예";
            cancelBtnMsg = "아니오";
        }
        else{
            alertMsg = "Only one shop's Hanbok can be put in the basket\n" +
                    "Would you like to empty your basket and put this Hanbok?";
            okBtnMsg = "Yes";
            cancelBtnMsg = "No";
        }

        builder.setMessage(alertMsg);
        builder.setPositiveButton(okBtnMsg,
                (dialog, which) -> {
                    clearBasket();
                    basket.add(item);
                    selectedStoreID = item.getClothes().getStore_id();
                    closeReservationActivity(context);
                    openBasketActivity(context, btn);
                });
        builder.setNegativeButton(cancelBtnMsg,
                (dialog, which) -> {

                });
        builder.show();
    }

    private void openBasketActivity(@NonNull Context context, int btn){
        if(btn == 1){
            Intent intent = new Intent((ClothesReservationActivity)context, BasketActivity.class);
            context.startActivity(intent);
        }
        String toastMsg;
        if(Locale.getDefault().getLanguage() == "ko")
            toastMsg = "장바구니에 담겼습니다";
        else
            toastMsg = "Add to basket";
        Toast toast = Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void closeReservationActivity(@NonNull Context context){
        ((ClothesReservationActivity)context).finish();
    }

    public void deleteClothes(int position)
    {
        basket.remove(position);
    }

    public int getTotalPrice()
    {
        int price = 0;
        for(BasketItem b : basket){
            price += b.getClothes().getPrice() * b.getCnt();
        }
        return price;
    }

    public int getTotalClothesCnt()
    {
        int cnt = 0;
        for(BasketItem b : basket)
            cnt += b.getCnt();
        return cnt;
    }
}