package course.labs.countryInfo.model;

import java.util.List;

public class CountryInfo {
  public List<Country> geonames;

  @Override
  public String toString() {
    return "CountryInfo{" +
        "geonames=" + geonames +
        '}';
  }
}
