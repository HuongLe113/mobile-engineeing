package course.labs.gallery.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;

import course.labs.gallery.R;

public class LoadImageTask extends AsyncTask<File, Void, Bitmap> {
  ImageView imageView;

  public LoadImageTask(ImageView imageView){
    this.imageView = imageView;
    imageView.setImageResource(R.drawable.ic_svstatus_loading);
  }

  protected Bitmap doInBackground(File... file) {
    Bitmap bitmap = BitmapFactory.decodeFile(file[0].getAbsolutePath());
    return bitmap;
  }

  protected void onPostExecute(Bitmap result) {
    imageView.setImageBitmap(result);
  }
}
