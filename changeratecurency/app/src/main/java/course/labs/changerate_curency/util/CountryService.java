package course.labs.changerate_curency.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import course.labs.changerate_curency.MainActivity;
import course.labs.changerate_curency.model.Country;

public class CountryService {
  public static List<Country> getCountriesList(Context context){
    List<Country> countries = new ArrayList<>();

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(
          new InputStreamReader(context.getAssets().open("countries.json"), "UTF-8"));
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null)
        sb.append(line);
      Gson gson = new Gson();
      JsonObject obj = gson.fromJson(sb.toString(), JsonObject.class);
      JsonArray jsonCurrencyArray = (JsonArray) obj.get("data");
      for(int i=0; i<jsonCurrencyArray.size(); i++){
        JsonObject countryObj = (JsonObject) jsonCurrencyArray.get(i);
        countries.add(new Country(
            countryObj.get("code").getAsString(),
            countryObj.get("currencyCode").getAsString(),
            countryObj.get("name").getAsString(),
            "https://flagcdn.com/w40/"+countryObj.get("code").getAsString().toLowerCase()+".png"
        ));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return countries;
  }
}
