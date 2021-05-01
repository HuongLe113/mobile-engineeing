package course.labs.changeratecurrentcy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import course.labs.changeratecurrentcy.model.Country;

public class CountryListItemAdapter extends ArrayAdapter<Country> {
  Context context;
  List<Country> item;

  public CountryListItemAdapter(@NonNull Context context, int layoutTobeInflated, List<Country> items) {
    super(context, R.layout.list_item_country, items);
    this.context = context;
    this.item = items;
  }
  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View row = inflater.inflate(R.layout.list_item_country, null);
    TextView name = row.findViewById(R.id.country_name);
    ImageView flag = row.findViewById(R.id.country_flag);

    name.setText(item.get(position).getCountryName());
    Log.d("TAG", "getView: "+item.get(position).getPopulation());
    new DownloadImageTask(flag).execute(item.get(position).getFlagurl());
    return row;
  }
}
