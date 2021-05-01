package course.labs.changeratecurrentcy.repository;

import course.labs.changeratecurrentcy.model.CountryInfo;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryApi {
  String ENDPOINT = "http://api.geonames.org";

  @GET("countryInfoJSON?formatted=true&lang=it&username=tiktzuki&style=full")
  Single<CountryInfo> getAllCountries();

  @GET("countryInfoJSON?formatted=true&lang=en&username=tiktzuki&style=full")
  Single<CountryInfo> getCountry(@Query("country") String country);
}
