package course.labs.changerate_curency.model;

public class Country {
  private String code;
  private String currencyCode;
  private String name;
  private String flagUrl;

  public Country(String code, String currencyCode, String name, String flagUrl) {
    this.code = code;
    this.currencyCode = currencyCode;
    this.name = name;
    this.flagUrl = flagUrl;
  }

  public String getCode() {
    return code;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public String getFlagUrl() {
    return flagUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Country{" +
        "code='" + code + '\'' +
        ", currencyCode='" + currencyCode + '\'' +
        ", name='" + name + '\'' +
        ", flagUrl='" + flagUrl + '\'' +
        '}';
  }
}
