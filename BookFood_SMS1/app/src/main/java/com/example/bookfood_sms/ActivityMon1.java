package com.example.bookfood_sms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ActivityMon1 extends AppCompatActivity {
    ImageView iconback;
    ImageView imgFood;
    TextView txtFoodName;
    TextView txtFoodPrice;
    TextView txtFoodDescription;
    ImageView btnLocation;
    TextView txtWebsite;
    WebView webView;
    ItemsList item;
    ItemsList[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon1);
        prepareData();
        imgFood = findViewById(R.id.imgFood);
        txtFoodName = findViewById(R.id.txtFoodName);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        txtWebsite = findViewById(R.id.txtDetailWebsite);
        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        int position = intent.getIntExtra("INDEX", 0);
         item = items[position];

         imgFood.setImageResource(item.getFoodImage());
         txtFoodName.setText(item.getFoodName());
         txtFoodPrice.setText("Giá: " + item.getFoodGia() + "đ");
         txtWebsite.setText(item.getFoodLink());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(item.getFoodLink());

        btnLocation = findViewById(R.id.btnDetailLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tiktzuki", "showView: "+"clicked");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getFoodLocation()) );
                mapIntent.setPackage("com.google.android.apps.maps");
//                Intent choserIntent = Intent.createChooser(mapIntent, "chọn app để goi");
//                if (mapIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(mapIntent);
            }
        });

        iconback =(ImageView) findViewById(R.id.imgBack) ;
        iconback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Log.d("tiktzuki", "onClick: "+"clicked back");
                Intent intent = new Intent(ActivityMon1.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void prepareData() {
        items = new ItemsList[10];
        items[0] = new ItemsList(1, R.drawable.food, "https://rasamalaysia.com/kaya-toast/", "Bánh Mì Nướng Ca Dé (Kaya Toast)", "geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé", 500.000, getString(R.string.mon1));
        items[1] = new ItemsList(2, R.drawable.food2, "https://www.foody.vn/ho-chi-minh/muc-nuong-da", "Mực Nướng Đá", "geo:0,0?q=Mùa+Vàng+The+Warehouse,+268+Tô+Hiến+Thành,+Phường+15,+Quận+10,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 300.000, getString(R.string.mon2));
        items[2] = new ItemsList(3, R.drawable.mon3, "https://cookpad.com/vn/cong-thuc/12377175-banh-mi-bo-n%C6%B0%E1%BB%9Bng", "Bánh mì bò", "geo:0,0?q=The+Garlik,+216+Đề+Thám,+Phường+Phạm+Ngũ+Lão,+Quận+1,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 150.000, getString(R.string.mon3));
        items[3] = new ItemsList(4, R.drawable.mon4, "https://homefarm.vn/products/ca-hoi-tuoi-nauy-fillet", "Cá Hồi Nauy", "geo:0,0?q=Phường+1,+Tan+Binh,+Ho+Chi+Minh+City,+Vietnam", 200.000, getString(R.string.mon4));
        items[4] = new ItemsList(5, R.drawable.mon5, "https://www.foody.vn/ho-chi-minh/chicken-story-ga-chien-han-quoc", "Gà chiên xuxu", "geo:0,0?q=Nhà+Hàng+Món+Ngon,+257A+Nguyễn+Trãi,+Phường+Nguyễn+Cư+Trinh,+Quận+1,+Thành+phố+Hồ+Chí+Minh,+Vietnam", 800.000, getString(R.string.mon5));
        items[5] = new ItemsList(6, R.drawable.mon6, "https://www.foody.vn/ho-chi-minh/quan-76-nuong-lau-hai-san", "Lẩu hải sản", "geo:0,0?q=Món+Việt,+148+Lê+Thánh+Tôn,+Phường+Bến+Thành,+Quận+1,+Thành+phố+Hồ+Chí+Minh+710220,+Vietnam", 600.000, getString(R.string.mon6));
        items[6] = new ItemsList(7, R.drawable.mon7, "https://www.foody.vn/ho-chi-minh/pho-270", "Phở", "geo:0,0?q=Mùa+Vàng+The+Warehouse,+268+Tô+Hiến+Thành,+Phường+15,+Quận+10,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 230.000, getString(R.string.mon7));
        items[7] = new ItemsList(8, R.drawable.mon8, "https://www.nhahangquangon.com/", "Sanxi Gà", "geo:0,0?q=Nhā+Hāng+Viet,+9-11+Phan+Chu+Trinh,+Phường+Bến+Thành,+Quận+1,+Thành+phố+Hồ+Chí+Minh,+Vietnam", 400.000, getString(R.string.mon8));
        items[8] = new ItemsList(9, R.drawable.mon9, "https://nhahanghoangtam.vn/", "Cơm cuộn Hàn Quốc", "geo:0,0?q=The+Garlik,+216+Đề+Thám,+Phường+Phạm+Ngũ+Lão,+Quận+1,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 800.000, getString(R.string.mon9));
        items[9] = new ItemsList(10, R.drawable.mon10, "https://amthucquenha.vn/", "Suha Samaco", "geo:0,0?q=Nhà+hàng+Hoàng+Tâm,+TK,+22%2F1+Nguyễn+Cảnh+Chân,+Cầu+Kho,+Quận+1,+Thành+phố+Hồ+Chí+Minh+70000,+Vietnam", 100.000, getString(R.string.mon10));
    }
}