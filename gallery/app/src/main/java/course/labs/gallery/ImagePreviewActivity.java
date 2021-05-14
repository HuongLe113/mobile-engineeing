package course.labs.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;

import course.labs.gallery.utils.Constant;
import course.labs.gallery.utils.DialogUtil;
import course.labs.gallery.utils.ImageUtil;
import course.labs.gallery.utils.LoadImageTask;

public class ImagePreviewActivity extends BaseActivity{
  boolean inTrash = false;

  ConstraintLayout clImage;
  ImageView ivReviewImage;

  String imagePath = null;
  ImageView btnBack;
  ImageView btnDelete;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_image_preview;
  }

  @Override
  protected void createView() {
    getDataIntent();
    clImage = findViewById(R.id.cl_image);
    ivReviewImage = findViewById(R.id.iv_preview_image);
    btnBack = findViewById(R.id.iv_back);
    btnDelete = findViewById(R.id.btn_delete);
    inTrash = imagePath.toLowerCase().contains("deleted");

    loadImage(imagePath);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DialogUtil.createAlertDialog(ImagePreviewActivity.this,
            !inTrash ? "Đưa hình ảnh vào thùng rác" : "Xóa vĩnh viễn",
            !inTrash ? "Đưa hình này vào thùng rác" : "Xóa hình ảnh này vĩnh viễn",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                if(inTrash){
                  ImageUtil.deleteImage(imagePath);
                } else {
                  ImageUtil.moveToTrash(ImagePreviewActivity.this, imagePath);
                }
                showActivity(SplashActivity.class);
              }
            },
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
              }
            }).show();
      }
    });
  }
  private float x1,x2;
  static final int MIN_DISTANCE = 150;
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch(event.getAction()){
      case MotionEvent.ACTION_DOWN:
        x1 = event.getX();
        break;
      case MotionEvent.ACTION_UP:
        x2 = event.getX();
        float deltaX = x2 - x1;

        if (Math.abs(deltaX) > MIN_DISTANCE){
          if (x2 > x1){
            File currentFile = SplashActivity.images.stream().filter(file -> file.getAbsolutePath().equals(imagePath)).findAny().get();
           int index = SplashActivity.images.indexOf(currentFile)-1 > 0 ? SplashActivity.images.indexOf(currentFile)-1 : 0;
            imagePath = SplashActivity.images.get(index).getAbsolutePath();
            loadImage(imagePath);
          }else{
            File currentFile = SplashActivity.images.stream().filter(file -> file.getAbsolutePath().equals(imagePath)).findAny().get();
            int index = SplashActivity.images.indexOf(currentFile)+1 < SplashActivity.images.size() ? SplashActivity.images.indexOf(currentFile)+1 : 0;
            imagePath = SplashActivity.images.get(index).getAbsolutePath();
            loadImage(imagePath);
          }
        }
        break;
    }
    return super.onTouchEvent(event);
  }

  private void loadImage(String imagePath){
    if(imagePath != null && !imagePath.isEmpty()){
      new LoadImageTask(ivReviewImage).execute(new File(imagePath));
    }
  }

  private void getDataIntent(){
    Bundle bundle = getIntent().getBundleExtra(Constant.KEY_EXTRA);
    if(bundle != null){
      imagePath = bundle.getString(Constant.KEY_IMAGE_PATH);
    }
  }

}
