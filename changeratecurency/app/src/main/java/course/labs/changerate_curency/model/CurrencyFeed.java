package course.labs.changerate_curency.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class CurrencyFeed {
  @Element(name = "title")
  @Path("channel")
  private String chanelTitle;
  @Element(name ="link")
  @Path("chanel")
  private String link;
  @Element(name = "description")
  @Path("channel")
  private String desciption;
  @Element(name = "lastBuildDate")
  @Path("channel")
  private String lastBuildate;
  @Element(name = "copyright")
  @Path("channel")
  private String copyright;

  @ElementList(name = "item", inline =  true)
  @Path("channel")
  private List<CurrencyExchangeItem> currencyExchangeItems;

  public String getChanelTitle() {
    return chanelTitle;
  }

  public void setChanelTitle(String chanelTitle) {
    this.chanelTitle = chanelTitle;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDesciption() {
    return desciption;
  }

  public void setDesciption(String desciption) {
    this.desciption = desciption;
  }

  public String getLastBuildate() {
    return lastBuildate;
  }

  public void setLastBuildate(String lastBuildate) {
    this.lastBuildate = lastBuildate;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public List<CurrencyExchangeItem> getCurrencyExchangeItems() {
    return currencyExchangeItems;
  }

  public void setCurrencyExchangeItems(List<CurrencyExchangeItem> currencyExchangeItems) {
    this.currencyExchangeItems = currencyExchangeItems;
  }
}
