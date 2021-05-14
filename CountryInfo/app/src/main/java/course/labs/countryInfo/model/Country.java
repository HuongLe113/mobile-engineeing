package course.labs.countryInfo.model;

public class Country {
  String capital;
  String languages;
  String countryName;
  String countryCode;
  String flagUrl;
  String population;
  String areaInSqKm;
  String continentName;
  String continent;
  String currencyCode;

 public Country(){
 }

  public String getCountryName() {
    return countryName;
  }

  public String getFlagUrl() {
    return "https://flagcdn.com/w40/"+String.format("%s", this.countryCode).toLowerCase()+".png";
  }

  public String getPopulation() {
    return population;
  }

  public String getArea() {
    return areaInSqKm;
  }

  public String getCapital() {
    return capital;
  }

  public String getLanguages() {
    return languages;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getContinentName() {
    return continentName;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public String getContinent() {
    return continent;
  }

  @Override
  public String toString() {
    return "{" +
        "name='" + countryName + '\'' +
        ", flagurl='" + flagUrl + '\'' +
        ", population=" + population +
        ", area=" + areaInSqKm +
        '}';
  }
}