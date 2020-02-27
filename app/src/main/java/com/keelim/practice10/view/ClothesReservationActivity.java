package com.keelim.practice10.view;

import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keelim.practice10.R;
import com.keelim.practice10.model.Basket;
import com.keelim.practice10.model.BasketItem;
import com.keelim.practice10.model.Clothes;
import com.keelim.practice10.model.ServerImg;
import com.keelim.practice10.model.Store;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

public class ClothesReservationActivity extends AppCompatActivity {
    private DecimalFormat dc;
    @Nullable
    private Clothes item;
    private LinearLayout btnReduce, btnAdd, btnBottom;
    private TextView totalPrice, selectCnt;
    @Nullable
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_reservation);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //뒤로가기
        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        item = (Clothes) intent.getSerializableExtra("clothes");
        store = (Store) intent.getSerializableExtra("store");

        TextView titleName = (TextView)findViewById(R.id.toolbar_title);
        titleName.setText(store.getName());

        // 이미지
        ImageView clotheImg = (ImageView)findViewById(R.id.reserve_clothes_img);
        ServerImg.getClothesImageGlide(getApplicationContext(), item.getCloth_id(), clotheImg);

        // 옷 이름
        TextView name = findViewById(R.id.reserve_clothes_name);
        name.setText(item.getName());

        // 옷 설명
        TextView description = findViewById(R.id.reserve_clothes_introduction);
        description.setText(item.getIntro());

        // 옷 가격
        dc = new DecimalFormat("###,###,###,###");
        final TextView price = findViewById(R.id.reserve_clothes_price);
        String str;
        if(Locale.getDefault().getLanguage()=="ko")
            str = dc.format(item.getPrice()) + " 원";
        else
            str = dc.format(item.getPrice()) + " won";
        price.setText(str);

        // 총 가격
        setTotalPrice(0);
        btnReduce = findViewById(R.id.counting_btn_reduce);
        btnAdd = findViewById(R.id.counting_btn_add);
        selectCnt = findViewById(R.id.reserve_clothes_cnt);

        // 수량 추가, 감소 버튼 이벤트
        btnReduce.setOnClickListener(v -> {
            int cnt = getSelectClothesCnt();
            if(0 < cnt && cnt <= item.getCount()){
                --cnt;
                selectCnt.setText( "" + cnt);
                setTotalPrice(cnt);
            }
        });

        btnAdd.setOnClickListener(v -> {
            int cnt = getSelectClothesCnt();
            if(0 <= cnt && cnt < item.getCount()){
                ++cnt;
                selectCnt.setText("" + cnt);
                setTotalPrice(cnt);
            }
        });

        // 옷 재고 여부에 따른 하단 메뉴바 설정
        final int clothesCnt = this.item.getCount();
        LinearLayout soldoutMenu = findViewById(R.id.reserve_soldout);
        LinearLayout reserveMenu = findViewById(R.id.reserve_bottom_menu);
        if(clothesCnt == 0){
            soldoutMenu.setVisibility(View.VISIBLE);
            reserveMenu.setVisibility(View.INVISIBLE);
        }
        else{
            soldoutMenu.setVisibility(View.INVISIBLE);
            reserveMenu.setVisibility(View.VISIBLE);

            LinearLayout gotoBasket = (LinearLayout) findViewById(R.id.reserve_clothes_basket);
            gotoBasket.setOnClickListener(v -> {
                if(getSelectClothesCnt() > 0) {
                    Basket basket = Basket.getInstance();
                    basket.addClothes(v.getContext(), new BasketItem(item, Integer.parseInt((String) selectCnt.getText())), 0);
                }
                else{
                    String toastMsg;
                    if(Locale.getDefault().getLanguage() == "ko")
                        toastMsg = "한벌 이상 고르셔야 합니다.";
                    else
                        toastMsg = "Please, choose at least one";
                    Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                }
            });

            LinearLayout gotoReserve = (LinearLayout) findViewById(R.id.reserve_clothes_reserve);
            gotoReserve.setOnClickListener(v -> {
                if(getSelectClothesCnt() > 0) {
                    Basket basket = Basket.getInstance();
                    basket.addClothes(v.getContext(), new BasketItem(item, Integer.parseInt((String) selectCnt.getText())), 1);
                }
                else{
                    String toastMsg;
                    if(Locale.getDefault().getLanguage() == "ko")
                        toastMsg = "한벌 이상 고르셔야 합니다.";
                    else
                        toastMsg = "Please, choose at least one";
                    Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public int getSelectClothesCnt(){
        return Integer.parseInt((String)selectCnt.getText());
    }

    public void setTotalPrice(int cnt){
        totalPrice = findViewById(R.id.reserve_clothes_total_price);
        String total;
        if(Locale.getDefault().getLanguage()=="ko")
            total = dc.format(item.getPrice() * cnt) + " 원";
        else
            total = dc.format(item.getPrice() * cnt) + " won";
        totalPrice.setText(total);
    }
}