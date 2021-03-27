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


  //    public static ArrayList<String> arr  = new ArrayList<String>(20);
  public static List<ItemsList> arr = new ArrayList<>();
  Context context;
  ItemsList[] items;
  public static double tongTien = 0;

  public CustomListItemAdapter(Context context, int layoutTobeInflated, ItemsList[] items) {
    super(context, R.layout.list_item_lnk_img, items);
    this.context = context;
    this.items = items;
  }

  private View.OnClickListener itemClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.txtFoodName:
          TextView fn = (TextView) v;
          String fl = (String) v.getTag();
          Toast.makeText(context, fl.toString(), Toast.LENGTH_SHORT).show();
          showView(fl.toString());
          return;
        case R.id.imgFoodImage:
          ImageView fi = (ImageView) v;
          String floc = (String) fi.getTag();
          Toast.makeText(context, floc.toString(), Toast.LENGTH_SHORT).show();
          showView(floc.toString());
          return;
      }
    }
  };

  public static class ViewHolder {
    TextView foodName;
    ImageView foodImage;
    TextView foodLink;
    TextView foodLocation;
    CheckBox checkBox;
    TextView foodGia;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    View row = inflater.inflate(R.layout.list_item_lnk_img, null);

    TextView foodName = (TextView) row.findViewById(R.id.txtFoodName);
    ;
    ImageView foodImage = (ImageView) row.findViewById(R.id.imgFoodImage);
    TextView foodLink = (TextView) row.findViewById(R.id.txtFoodLink);
    TextView foodLocation = (TextView) row.findViewById(R.id.txtFoodLocation);
    CheckBox checkBox = (CheckBox) row.findViewById(R.id.cbcheckbox);
    TextView foodGia = (TextView) row.findViewById(R.id.txtGia);

    foodName.setText(position + items[position].getFoodName().toString());
    foodLink.setText(items[position].getFoodLink().toString());
    foodName.setTag(foodLink.getText().toString());
    foodImage.setImageResource(items[position].getFoodImage());
    foodLocation.setText(items[position].getFoodLocation().toString());
    foodImage.setTag(foodLocation.getText().toString());
    foodGia.setText("Giá: " + String.valueOf(items[position].getFoodGia()) + " đ");
    foodName.setOnClickListener(itemClickListener);
    foodImage.setOnClickListener(itemClickListener);
//        viewHolder.checkBox.setTag( position);

    for (int i = 0; i < arr.size(); i++) {
      if (items[position].getId() == arr.get(i).getId()) {
        Log.d("tiktzuki", "getView: " + i);
        checkBox.setChecked(true);
      }
    }

    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                CheckBox cb = (CheckBox)buttonView;
        if (isChecked) {
          arr.add(items[position]);
          tongTien = calPrice(arr);
//                  Toast.makeText(context, "Bạn đã chọn: "+items[position].getFoodName(), Toast.LENGTH_SHORT).show();
        } else {
          for (ItemsList item : arr) {
            if (item.getId() == items[position].getId())
              arr.remove(item);
          }
          tongTien = calPrice(arr);

//                    Toast.makeText(context, "Bạn đã b ỏ chọn: "+items[position].getFoodName(), Toast.LENGTH_SHORT).show();
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

  private void showView(String v) {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(v));
    if (intent.resolveActivity(context.getPackageManager()) != null)
      context.startActivity(intent);
  }

}
