package com.example.bookfood_sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  ListView listView;
  static ItemsList[] items;
  static List<ItemsList> selectedItem;
  CustomListItemAdapter adapter;
  Button btnXemMon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    prepareData();
    listView = (ListView) findViewById(R.id.lvMain);
    btnXemMon = (Button) findViewById(R.id.btnXemMon);
    adapter = new CustomListItemAdapter(this, R.layout.list_item_lnk_img, items);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Intent intent1 = new Intent(view.getContext(), ActivityMon1.class);
          intent1.putExtra("INDEX", position);
          startActivity(intent1);
      }
    });
    btnXemMon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intentXem = new Intent(MainActivity.this, ActivityOrder.class);
        startActivity(intentXem);
      }
    });
  }

  private void prepareData() {
    items = new ItemsList[10];
    items[0] = new ItemsList(1, R.drawable.food, "http://www.google.com", "Bánh Mì Nướng Ca Dé (Kaya Toast)", "geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé", 500.000, getString(R.string.mon1));
    items[1] = new ItemsList(2, R.drawable.food2, "http://www.quananngon.com/", "Mực nướng đá", "geo:0,0?q=Mùa+Vàng+The+Warehouse,+268+Tô+Hiến+Thành,+Phường+15,+Quận+10,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 300.000, getString(R.string.mon2));
    items[2] = new ItemsList(3, R.drawable.mon3, "https://www.nhahangquangon.com/", "Bánh mì bò", "geo:0,0?q=The+Garlik,+216+Đề+Thám,+Phường+Phạm+Ngũ+Lão,+Quận+1,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 150.000, getString(R.string.mon3));
    items[3] = new ItemsList(4, R.drawable.mon4, "https://www.nhahangquangon.com/", "Cá Hồi Nauy", "geo:0,0?q=Phường+1,+Tan+Binh,+Ho+Chi+Minh+City,+Vietnam", 200.000, getString(R.string.mon4));
    items[4] = new ItemsList(5, R.drawable.mon5, "http://123-zo.vn", "Gà chiên xuxu", "geo:0,0?q=Nhà+Hàng+Món+Ngon,+257A+Nguyễn+Trãi,+Phường+Nguyễn+Cư+Trinh,+Quận+1,+Thành+phố+Hồ+Chí+Minh,+Vietnam", 800.000, getString(R.string.mon5));
    items[5] = new ItemsList(6, R.drawable.mon6, "https://www.nhahangquangon.com/", "Lẩu hải sản", "geo:0,0?q=Món+Việt,+148+Lê+Thánh+Tôn,+Phường+Bến+Thành,+Quận+1,+Thành+phố+Hồ+Chí+Minh+710220,+Vietnam", 600.000, getString(R.string.mon6));
    items[6] = new ItemsList(7, R.drawable.mon7, "https://muavangwarehouse.com/", "Samara ", "geo:0,0?q=Mùa+Vàng+The+Warehouse,+268+Tô+Hiến+Thành,+Phường+15,+Quận+10,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 230.000, getString(R.string.mon7));
    items[7] = new ItemsList(8, R.drawable.mon8, "https://www.nhahangquangon.com/", "Sanxi Gà", "geo:0,0?q=Nhā+Hāng+Viet,+9-11+Phan+Chu+Trinh,+Phường+Bến+Thành,+Quận+1,+Thành+phố+Hồ+Chí+Minh,+Vietnam", 400.000, getString(R.string.mon8));
    items[8] = new ItemsList(9, R.drawable.mon9, "https://nhahanghoangtam.vn/", "Cơm cuộn Hàn Quốc", "geo:0,0?q=The+Garlik,+216+Đề+Thám,+Phường+Phạm+Ngũ+Lão,+Quận+1,+Thành+phố+Hồ+Chí+Minh+700000,+Vietnam", 800.000, getString(R.string.mon9));
    items[9] = new ItemsList(10, R.drawable.mon10, "https://amthucquenha.vn/", "Suha Samaco", "geo:0,0?q=Nhà+hàng+Hoàng+Tâm,+TK,+22%2F1+Nguyễn+Cảnh+Chân,+Cầu+Kho,+Quận+1,+Thành+phố+Hồ+Chí+Minh+70000,+Vietnam", 100.000, getString(R.string.mon10));
  }
}