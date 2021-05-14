package course.labs.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wysaid.common.Common;

import java.io.File;
import java.util.List;

import course.labs.gallery.R;
import course.labs.gallery.utils.LoadImageTask;

public class ImageGridAdapter extends ArrayAdapter<File> {
  Context context;
  public List<File> images;
  public List<File> selectedImages;
  ImageView iv;

  public ImageGridAdapter(@NonNull Context context, int resource,  @NonNull List<File> images) {
    super(context, resource, images);
    this.context = context;
    this.images = images;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View item = inflater.inflate(R.layout.grid_item_image, null);
    iv = item.findViewById(R.id.grid_item);
    new LoadImageTask(iv).execute(images.get(position));
    return item;
  }
  public static final String TAG = Common.LOG_TAG;
}

