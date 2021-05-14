package course.labs.changerate_curency.util;

import java.text.NumberFormat;

public class CurrencyFormat {
  public static String format(String currenyString){
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    String fractionalPart = currenyString.substring(currenyString.lastIndexOf(".") > 0 ? currenyString.lastIndexOf(".") : currenyString.length(), currenyString.length());
    String decimalPart = numberFormat.format(Double.valueOf(currenyString));
    decimalPart = decimalPart.substring(1, decimalPart.lastIndexOf("."));
    return decimalPart + fractionalPart;
  }
}
