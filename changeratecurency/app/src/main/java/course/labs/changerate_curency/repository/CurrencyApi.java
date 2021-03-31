package course.labs.changerate_curency.repository;

import java.lang.annotation.Target;

import course.labs.changerate_curency.model.CurrencyFeed;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CurrencyApi {
 @GET
 Single<CurrencyFeed> getFeed(@Url String country);
}
