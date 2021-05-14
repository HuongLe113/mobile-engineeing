package course.labs.countryInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import course.labs.countryInfo.model.Country;
import course.labs.countryInfo.model.CountryInfo;
import course.labs.countryInfo.repository.CountryApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryInfoActivity extends AppCompatActivity {
  CountryApi countryApi;
  TextView
      txtCapital,
      txtLanguages,
      txtNameCountry,
      txtPopulation,
      txtArea,
      txtContinentName,
      txtCurrencyCode;
  ImageView imgFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_new);
        txtNameCountry =(TextView) findViewById(R.id.txtNameCountry);
        imgFlag =(ImageView) findViewById(R.id.imgFlag);
        txtPopulation =(TextView) findViewById(R.id.txtPopulation);
        txtArea=(TextView) findViewById(R.id.txtArea);
        txtCapital= findViewById(R.id.txtCapital);
        txtLanguages = findViewById(R.id.txtLanguage);
        txtContinentName = findViewById(R.id.txtContinent);
        txtCurrencyCode = findViewById(R.id.txtCurrencyCode);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String countryCode = bundle.getString("countryCode");
        //Set up api
        createCountryApi();
        loadCountry(countryCode);
    }

    private void loadCountry(String countryCode){
       Call call = this.countryApi.getCountry(countryCode);
       call.enqueue(new Callback<CountryInfo>() {
         @Override
         public void onResponse(Call<CountryInfo> call, retrofit2.Response<CountryInfo> response) {
           Country country = response.body().geonames.get(0);
           new DownloadImageTask(imgFlag).execute(country.getFlagUrl());
           txtNameCountry.setText(country.getCountryName() +" ("+ country.getCountryCode() +")");
           txtPopulation.setText(country.getPopulation());
           txtArea.setText(country.getArea()+"km²");
           txtCapital.setText(country.getCapital());
           txtCurrencyCode.setText(country.getCurrencyCode());
           txtLanguages.setText(country.getLanguages());
           txtContinentName.setText(country.getContinentName() +" ("+ country.getContinent() +")");
         }

         @Override
         public void onFailure(Call<CountryInfo> call, Throwable t) {

         }
       });
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
}
