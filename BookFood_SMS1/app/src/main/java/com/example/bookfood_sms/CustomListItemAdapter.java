package com.example.bookfood_sms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomListItemAdapter extends ArrayAdapter<ItemsList> {

  public static List<ItemsList> arr = new ArrayList<>();
  Context context;
  ItemsList[] items;
  public static double tongTien = 0;

  public CustomListItemAdapter(Context context, int layoutTobeInflated, ItemsList[] items) {
    super(context, R.layout.list_item_lnk_img, items);
    this.context = context;
    this.items = items;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    View row = inflater.inflate(R.layout.list_item_lnk_img, null);

    TextView foodName = (TextView) row.findViewById(R.id.txtFoodName);
    ImageView foodImage = (ImageView) row.findViewById(R.id.imgFoodImage);
    CheckBox checkBox = (CheckBox) row.findViewById(R.id.cbcheckbox);
    TextView foodGia = (TextView) row.findViewById(R.id.txtGia);

    foodName.setText(items[position].getFoodName().toString());
    foodImage.setImageResource(items[position].getFoodImage());
    foodGia.setText("Giá: " + String.valueOf(items[position].getFoodGia()) + " đ");

    for (int i = 0; i < arr.size(); i++) {
      if (items[position].getId() == arr.get(i).getId()) {
        checkBox.setChecked(true);
      }
    }

    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          arr.add(items[position]);
          tongTien = calPrice(arr);
        } else {
          for (ItemsList item : arr) {
            if (item.getId() == items[position].getId())
              arr.remove(item);
          }
          tongTien = calPrice(arr);
        }
      }
    });
    return row;
  }

  private double calPrice(List<ItemsList> items) {
    double result = 0;
    for (ItemsList item : items) {
      result += item.getFoodGia();
    }
    return result;
  }

}
