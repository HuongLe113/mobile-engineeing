package course.labs.gallery.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImageUtil {

  public static String saveBitmap(Bitmap bmp, String filename) {
    try {
      FileOutputStream fos = new FileOutputStream(filename);
      BufferedOutputStream bufferOutStream = new BufferedOutputStream(fos);
      bmp.compress(Bitmap.CompressFormat.JPEG, 100, bufferOutStream);
      bufferOutStream.flush();
      bufferOutStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return filename;
  }
  public static void moveToTrash(Context context, String imagePath){
    try {
      File trashDir = context.getExternalFilesDir(Environment.getExternalStorageDirectory() + "/"+Constant.IMAGE_PATH_DELETED );
      if(!trashDir.exists()){
        trashDir.mkdirs();
      }
      File image = File.createTempFile(imagePath.substring(imagePath.lastIndexOf("/")+1, imagePath.lastIndexOf(".")), ".jpg", trashDir);
      File oldImage = new File(imagePath);
      oldImage.renameTo(image);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void deleteImage(String imagePath){
    File oldImage = new File(imagePath);
    oldImage.delete();
  }

  public static List<File> getUploadedImage(File imageFolder){
    File[] listFile = imageFolder.listFiles();
    if(listFile != null && listFile.length != 0){
      return Arrays.asList(listFile).stream().filter(imageFile ->  {
        String filename = imageFile.getName();
        return !imageFile.isDirectory() && ( filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png"));
      } ).collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

}
