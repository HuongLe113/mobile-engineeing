package com.example.doinhietdo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoiNhietDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_nhiet_do);
        EditText textc = findViewById(R.id.textc);
        EditText textf = findViewById(R.id.textf);

        Button btnCtoF = findViewById(R.id.btnCtoF);
        Button btnFtoC = findViewById(R.id.btnFtoC);
        Button btnClear = findViewById(R.id.btnClear);

        btnCtoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double c = Double.parseDouble(textc.getText().toString());
                    Convert convert = new Convert();
                    convert.setC(c);
                    convert.convertCtoF();
                    double doF = convert.getF();
                    textf.setText(String.valueOf(doF));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("CONVERT", e.getMessage());
                    textf.setText(e.getMessage());
                }

            }
        });

        btnFtoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double f = 0.0;
                try {
                    f = Double.parseDouble(textf.getText().toString());
                    Convert convert = new Convert();
                    convert.setF(f);
                    convert.convertFtoC();
                    textc.setText(String.valueOf(convert.getC()));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("CONVERT", e.getMessage());
                    textc.setText(e.getMessage());
                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textc.setText("");
                textf.setText("");
            }
        });


    }
}