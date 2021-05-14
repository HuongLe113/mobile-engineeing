package course.labs.countryInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import course.labs.countryInfo.model.Country;

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
    new DownloadImageTask(flag).execute(item.get(position).getFlagUrl());
    return row;
  }

}
