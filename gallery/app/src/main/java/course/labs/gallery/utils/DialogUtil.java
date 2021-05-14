package course.labs.gallery.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

  public static AlertDialog createAlertDialog(Context context, String message, String title, DialogInterface.OnClickListener onConfirm, DialogInterface.OnClickListener onCancel){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton(android.R.string.ok, onConfirm);
    builder.setNegativeButton(android.R.string.cancel, onCancel);
    return builder.create();
  }
}
