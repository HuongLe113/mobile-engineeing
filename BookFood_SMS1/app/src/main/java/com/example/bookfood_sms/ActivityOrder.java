package com.example.bookfood_sms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityOrder extends AppCompatActivity {
    TextView txtOrder;

    ImageView iconback;
    Button btnGoiGuiSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        txtOrder =(TextView) findViewById(R.id.txtorder);
        iconback =(ImageView) findViewById(R.id.imgBack) ;
        btnGoiGuiSMS =(Button) findViewById(R.id.btnGoiGuiSMS);


        Toast.makeText(this, "Số lượng món ăn: "+ CustomListItemAdapter.arr.size(), Toast.LENGTH_SHORT).show();
            if(CustomListItemAdapter.arr.size()==0){
                txtOrder.setText("Bạn chưa chọn món nào!");
            }else{
                String listItemName = "";
                for(ItemsList item: CustomListItemAdapter.arr){
                    listItemName += item.getFoodName() + "\n";
                }
                txtOrder.setText("\t\tMÓN ĂN BẠN ĐÃ CHỌN\n\n"+listItemName+"\n\n"+"TỔNG TIỀN LÀ: "+CustomListItemAdapter.tongTien+"00 đ") ;

            }

        iconback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityOrder.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnGoiGuiSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(ActivityOrder.this)
                        .setTitle("Chốt đơn")
                        .setMessage("Bạn chắc chắn muốn  đặt món")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(txtOrder.getText().toString()=="[]"){
                                    Toast.makeText(ActivityOrder.this, "Bạn chưa chọn món nào?", Toast.LENGTH_SHORT).show();
                                }else{
                                    Intent intent1 = new Intent(ActivityOrder.this, smsActivity.class);
                                    startActivity(intent1);
                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentMain = new Intent(ActivityOrder.this, MainActivity.class);
                        startActivity(intentMain);
                    }
                }).show();

            }
        });

    }
}