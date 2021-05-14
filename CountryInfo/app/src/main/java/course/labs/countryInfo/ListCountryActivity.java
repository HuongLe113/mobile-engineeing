package course.labs.countryInfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import course.labs.countryInfo.model.Country;
import course.labs.countryInfo.model.CountryInfo;
import course.labs.countryInfo.repository.CountryApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListCountryActivity extends AppCompatActivity {
  CountryApi countryApi;
  ListView countryListView;
  CountryListItemAdapter adapter;
  SearchView searchView;
  List<Country> filterCoutry = new ArrayList<Country>();
  List<Country> items = new ArrayList<>();
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_country);
    countryListView = findViewById(R.id.list_view_country);
    searchView = findViewById(R.id.coutryListSearch);

    int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
    TextView textView = (TextView) searchView.findViewById(id);
    textView.setTextColor(Color.BLACK);

    createCountryApi();
    loadAllCountry();
  }

  private void createCountryApi(){
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create();
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(new Interceptor() {
          @Override
          public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
          }
        }).build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(CountryApi.ENDPOINT)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    countryApi = retrofit.create(CountryApi.class);
  }
  private void loadAllCountry(){
    compositeDisposable.add(countryApi.getAllCountries()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(getCountryObserver()));
  }

  DisposableSingleObserver<CountryInfo> getCountryObserver(){
    return new DisposableSingleObserver<CountryInfo>() {
      @Override
      public void onSuccess(CountryInfo value) {
        if(!value.equals(null)){
          items = value.geonames;
          filterCoutry = value.geonames;
          adapter = new CountryListItemAdapter(
              ListCountryActivity.this,
              R.layout.list_item_country,
              items);
          countryListView.setAdapter(adapter);
          countryListView.setEnabled(true);
          countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent = new Intent(getApplicationContext(), CountryInfoActivity.class);
              intent.putExtra("countryCode", filterCoutry.get(position).getCountryCode());
              startActivity(intent);
            }
          });
          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
              filterCoutry = new ArrayList<>();
              for(Country country : items){
                if( country.getCountryName().toLowerCase().contains(query.toLowerCase())){
                  filterCoutry.add(country);
                }
              }
              adapter = new CountryListItemAdapter(
                  ListCountryActivity.this,
                  R.layout.list_item_country,
                  filterCoutry);
              countryListView.setAdapter(adapter);
              return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              filterCoutry = items;
              if(newText.equals("")){
                adapter = new CountryListItemAdapter(
                    getApplicationContext(),
                    R.layout.list_item_country,
                    filterCoutry);
                countryListView.setAdapter(adapter);
                return true;
              }
              return false;
            }
          });
        }
      }
      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
        Toast.makeText(ListCountryActivity.this, "Can not load country", Toast.LENGTH_SHORT).show();
      }
    };
  }

}
