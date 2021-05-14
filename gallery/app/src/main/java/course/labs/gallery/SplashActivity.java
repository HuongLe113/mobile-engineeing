package course.labs.gallery;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import org.wysaid.common.Common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import course.labs.gallery.adapter.ImageGridAdapter;
import course.labs.gallery.utils.AlarmUtil;
import course.labs.gallery.utils.Constant;
import course.labs.gallery.utils.ImageUtil;

public class SplashActivity extends BaseActivity{
  static final int REQUEST_IMAGE_CAPTURE = 1;
  File IMAGE_DELETED_PATH =null;
  File IMAGE_PATH = null;
  ImageView ivLiveTab;
  ImageView ivDeletedTab;
  GridView gridImage;
  ImageView tvSplash;
  String selectedTab = "live";

  public static List<File> images = new ArrayList<>();

  @Override
  protected int getLayoutId() {
    return R.layout.activity_spalsh;
  }

  @Override
  protected void createView() {
    AlarmUtil.scheduleNotification(SplashActivity.this, 0, 1);
    IMAGE_DELETED_PATH = getExternalFilesDir(Environment.getExternalStorageDirectory() + "/"+Constant.IMAGE_PATH_DELETED);
    IMAGE_PATH = getExternalFilesDir(Environment.getExternalStorageDirectory() + "/"+Constant.IMAGE_PATH);;
    gridImage = findViewById(R.id.grid_image);
    ivLiveTab = findViewById(R.id.iv_live_tab);
    ivDeletedTab = findViewById(R.id.iv_deleted_tab);
    tvSplash = findViewById(R.id.iv_splash);

    loadImageToGrid();

    ivLiveTab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectedTab = "live";
        loadImageToGrid();
      }
    });
    ivDeletedTab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectedTab = "deleted";
        loadImageToGrid();
      }
    });
    tvSplash.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        dispatchTakePictureIntent();
      }
    });

  }

  @Override
  protected void onResume() {
    super.onResume();
    loadImageToGrid();
  }

  private void loadImageToGrid(){
    File storageDir = selectedTab.equals("live") ? IMAGE_PATH : IMAGE_DELETED_PATH ;
    images = ImageUtil.getUploadedImage(storageDir);
    ImageGridAdapter adapter = new ImageGridAdapter(this, R.layout.grid_item_image, images);
    gridImage.setAdapter(adapter);

    gridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_IMAGE_PATH, images.get(position).getAbsolutePath());
        showActivity(ImagePreviewActivity.class, bundle);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      Bundle extras = data.getExtras();
      Bitmap imageBitmap = (Bitmap) extras.get("data");
      try {
        File file = createImageFile();
        ImageUtil.saveBitmap(imageBitmap, file.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//    }
  }

  private File createImageFile() throws IOException {
    File storageDir = getExternalFilesDir(Environment.getExternalStorageDirectory() + "/"+Constant.IMAGE_PATH);
    if(!storageDir.exists()){
      storageDir.mkdirs();
    }
    String filename = "JPG" +System.currentTimeMillis();
    File image = File.createTempFile(filename, ".jpg", storageDir);
    return image;
  }

  public static final String TAG = Common.LOG_TAG;
}
