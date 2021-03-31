package course.labs.changerate_curency;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import course.labs.changerate_curency.model.CurrencyFeed;
import course.labs.changerate_curency.repository.CurrencyRepository;
import course.labs.changerate_curency.util.CountryService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void getCountryJson(){
    BigDecimal big = new BigDecimal("4.0E-5");
    System.out.println(big);
  }
}