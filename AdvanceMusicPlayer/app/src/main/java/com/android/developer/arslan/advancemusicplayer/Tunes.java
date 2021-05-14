package com.android.developer.arslan.advancemusicplayer;

import android.Manifest;
import android.app.Application;
import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tunes extends AppCompatActivity {
    ListView listView;
    String[] folders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunes);
        listView= (ListView) findViewById(R.id.listViewFolder);


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        displayFolders();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {

                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    public ArrayList<File> findFolder(File root){
        ArrayList<File> at = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile: files) {
            if(singleFile.isDirectory() && !singleFile.isHidden()){//
                at.add(singleFile);
            }
        }
        return at;
    }


    void displayFolders(){
        final ArrayList<File> myFolders = findFolder(Environment.getExternalStorageDirectory());

        folders = new String[ myFolders.size() ];
        for(int i=0;i<myFolders.size();i++){
            //toast(mySongs.get(i).getName().toString());
            folders[i]= myFolders.get(i).getName().toString();
        }
        ArrayAdapter<String> adp1 = new
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,folders);
        //listView.setAdapter(adp1);

        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.my_custom_layout,R.id.textView2,folders));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String folderName= listView.getItemAtPosition(position).toString();
                startActivity(new Intent(getApplicationContext(),Files.class)
                        .putExtra("pos",position).putExtra("folders",myFolders)
                        .putExtra("folderName",folderName));

            }
        });
    }


}
