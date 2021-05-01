package course.labs.changerate_curency.repository;

import java.lang.annotation.Target;

import course.labs.changerate_curency.model.CurrencyFeed;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CurrencyApi {
 @GET
 Call<CurrencyFeed> getFeed(@Url String country);
}
