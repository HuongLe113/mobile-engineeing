package com.example.bookfood_sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class smsActivity extends AppCompatActivity {
  EditText edtNum, edtContent;
  Button btnSend;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sms);
    edtNum = (EditText) findViewById(R.id.edtNum);
    edtContent = (EditText) findViewById(R.id.edtContent);
    btnSend = (Button) findViewById(R.id.btnSend);
    if (CustomListItemAdapter.arr.size() == 0) {
      edtContent.setText("");
    } else {
      String listItemName = "";
      for (ItemsList item : CustomListItemAdapter.arr) {
        listItemName += item.getFoodName() + "\n";
      }
      edtContent.setText("\t\tMÓN ĂN BẠN ĐÃ ĐẶT \n" + listItemName + "\n\nTỔNG TIỀN LÀ: " + CustomListItemAdapter.tongTien + "00 đ");

    }

    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendSMS(edtNum.getText().toString(), edtContent.getText().toString());
      }
    });
  }

  protected void sendSMS(String to, String content) {
    Log.i("Send SMS", "");
    Intent smsIntent = new Intent(Intent.ACTION_VIEW);

    smsIntent.setData(Uri.parse("smsto:"));
    smsIntent.setType("vnd.android-dir/mms-sms");
    smsIntent.putExtra("address", new String(to));
    smsIntent.putExtra("sms_body", content);

    try {
      startActivity(smsIntent);
      finish();
      Log.i("Finished sending SMS...", "");
    } catch (android.content.ActivityNotFoundException ex) {
      Toast.makeText(smsActivity.this,
          "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}