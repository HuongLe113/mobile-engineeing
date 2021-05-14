package course.labs.changerate_curency.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import course.labs.changerate_curency.model.Country;
import course.labs.changerate_curency.model.CurrencyFeed;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class CurrencyRepository {
  private CurrencyApi api;
  public static final String BASE_URL = "https://all.fxexchangerate.com/";
  Retrofit retrofit;

  public CurrencyRepository() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    retrofit = new Retrofit.Builder()
        .baseUrl(this.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient.build())
        .addConverterFactory(
            SimpleXmlConverterFactory.create())
        .build();
    this.api = retrofit.create(CurrencyApi.class);
  }

  public Call<CurrencyFeed> getCurrencyFeed(String from){
    return api.getFeed(from);
  }

  public Call<CurrencyFeed> getCurrencyFeedFromCode(String code){
    return api.getFeed("https://"+code.toLowerCase()+".fxexchangerate.com/rss.xml");
  }
}