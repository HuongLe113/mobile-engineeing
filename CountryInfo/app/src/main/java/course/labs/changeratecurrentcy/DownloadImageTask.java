package course.labs.changeratecurrentcy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
  ImageView imageView;
  DownloadImageTask(ImageView imageView){
    this.imageView = imageView;
  }
  protected Bitmap doInBackground(String... flagUrl) {
    Bitmap bitmap = null;
    int SDK_INT = android.os.Build.VERSION.SDK_INT;
    if (SDK_INT > 8) {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
          .permitAll().build();
      StrictMode.setThreadPolicy(policy);
      try {
        URL url = new URL(flagUrl[0]);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        BitmapFactory.Options options = new BitmapFactory.Options();

        bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
      } catch (IOException e) {
        e.printStackTrace();
      }}
    return bitmap;
  }

  protected void onPostExecute(Bitmap result) {
    imageView.setImageBitmap(result);
  }
}
