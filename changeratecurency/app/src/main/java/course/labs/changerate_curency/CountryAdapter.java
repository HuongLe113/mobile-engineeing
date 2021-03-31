package course.labs.changerate_curency;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import course.labs.changerate_curency.model.Country;

public class CountryAdapter extends ArrayAdapter {
  Context context;
  List<Country> items;
  List<Country> filteredItem = new ArrayList<>();
  public CountryAdapter(@NonNull Context context, int resource, @NonNull List objects) {
    super(context, resource, objects);
    this.items = objects;
    this.context = context;
  }

  @Override
  public int getCount() {
    return filteredItem.size();
  }

  @NonNull
  @Override
  public Filter getFilter() {
    return new CountryFilter(this, items);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Country country = filteredItem.get(position);
    LayoutInflater inflater = LayoutInflater.from(this.getContext());
    View row = inflater.inflate(R.layout.country_list_item, null);
    ImageView imgFlag = row.findViewById(R.id.imgCountry);
    TextView currencyCode = row.findViewById(R.id.txtCurrencyCode);
    TextView  name = row.findViewById(R.id.txtCountryName);
    currencyCode.setText(country.getCurrencyCode());
    name.setText(" : "+country.getName() + " ( "+country.getCode()+" )");
    int SDK_INT = android.os.Build.VERSION.SDK_INT;
    if (SDK_INT > 8)
    {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
          .permitAll().build();
      StrictMode.setThreadPolicy(policy);
      try {
        URL url = new URL(country.getFlagUrl());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
        imgFlag.setImageBitmap(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return row;
  }

  private class CountryFilter extends Filter{
    CountryAdapter adapter;
    List<Country> originalList;
    List<Country> filteredList;

    public CountryFilter(CountryAdapter adapter, List<Country> originalList){
      super();
      this.adapter = adapter;
      this.originalList = originalList;
      this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      filteredList.clear();
      final  FilterResults results = new FilterResults();

      if(constraint == null || constraint.length() == 0){
        filteredList.addAll(originalList);
      } else {
        final  String filterPattern = constraint.toString().toLowerCase().trim();
        for(final  Country country: originalList){
          if(country.getCode().toLowerCase().contains(filterPattern)
              || country.getName().toLowerCase().contains(filterPattern)
              || country.getCurrencyCode().toLowerCase().contains(filterPattern)
          ){
            filteredList.add(country);
          }
        }
      }
      results.values = filteredList;
      results.count = filteredList.size();
      return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      adapter.filteredItem.clear();
      adapter.filteredItem.addAll((List) results.values);
      adapter.notifyDataSetChanged();
    }
  }
}
