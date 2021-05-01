package course.labs.changeratecurrentcy.model;

import java.util.List;

import course.labs.changeratecurrentcy.model.Country;

public class CountryInfo {
  public List<Country> geonames;

  @Override
  public String toString() {
    return "CountryInfo{" +
        "geonames=" + geonames +
        '}';
  }
}
