package course.labs.changeratecurrentcy.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Country {
  String capital;
  String languages;
  String countryName;
  String countryCode;
  String flagurl;
  String population;
  String areaInSqKm;
  String south;
  String north;
  String east;
  String west;
  String continentName;
  String continent;


  String currencyCode;

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public String getFlagurl() {
    return "https://flagcdn.com/w160/"+String.format("%s", this.countryCode).toLowerCase()+".png";
  }

  public void setFlagurl(String flagurl) {
    this.flagurl = flagurl;
  }

  public Bitmap getFlagBitmap(){
    Bitmap bitmap = null;
    int SDK_INT = android.os.Build.VERSION.SDK_INT;
    if (SDK_INT > 8)
    {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
          .permitAll().build();
      StrictMode.setThreadPolicy(policy);
      try {
        URL url = new URL(this.getFlagurl());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
      } catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return bitmap;
  }

  public String getPopulation() {
    return population;
  }

  public void setPopulation(String population) {
    this.population = population;
  }

  public String getArea() {
    return areaInSqKm;
  }

  public void setArea(String area) {
    this.areaInSqKm = area;
  }

  public String getCapital() {
    return capital;
  }

  public void setCapital(String capital) {
    this.capital = capital;
  }

  public String getLanguages() {
    return languages;
  }

  public void setLanguages(String languages) {
    this.languages = languages;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getAreaInSqKm() {
    return areaInSqKm;
  }

  public void setAreaInSqKm(String areaInSqKm) {
    this.areaInSqKm = areaInSqKm;
  }

  public String getSouth() {
    return south;
  }

  public void setSouth(String south) {
    this.south = south;
  }

  public String getNorth() {
    return north;
  }

  public void setNorth(String north) {
    this.north = north;
  }

  public String getEast() {
    return east;
  }

  public void setEast(String east) {
    this.east = east;
  }

  public String getContinentName() {
    return continentName;
  }

  public void setContinentName(String continentName) {
    this.continentName = continentName;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getContinent() {
    return continent;
  }

  public void setContinent(String continent) {
    this.continent = continent;
  }
  public static List<Country> getListFromJson(Object json) {
    Log.d("Tiktzuki", "getListFromJson: "+json);
    CountryInfo countryInfo = (CountryInfo) json;
    List<Country> countries = new ArrayList<>();
    return countries;
  }

  @Override
  public String toString() {
    return "{" +
        "name='" + countryName + '\'' +
        ", flagurl='" + flagurl + '\'' +
        ", population=" + population +
        ", area=" + areaInSqKm +
        '}';
  }
}
