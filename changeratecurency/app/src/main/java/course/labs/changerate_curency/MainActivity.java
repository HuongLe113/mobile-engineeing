package course.labs.changerate_curency;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import course.labs.changerate_curency.model.Country;
import course.labs.changerate_curency.model.CurrencyExchangeItem;
import course.labs.changerate_curency.model.CurrencyFeed;
import course.labs.changerate_curency.repository.CurrencyApi;
import course.labs.changerate_curency.repository.CurrencyRepository;
import course.labs.changerate_curency.util.CountryService;

public class MainActivity extends AppCompatActivity {
  CurrencyRepository currencyRepository = new CurrencyRepository();
  CurrencyApi currencyApi;
  CurrencyFeed feed;
  AutoCompleteTextView acFromCurrencyCode;
  ImageView imgFrom;
  ArrayAdapter<Country> adapterFrom;
  EditText edtFrom;

  AutoCompleteTextView acToCurrencyCode;
  ImageView imgTo;
  ArrayAdapter<Country> adapterTo;
  TextView txtTo;

  Button btnConvert;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    acFromCurrencyCode = findViewById(R.id.acFromCurrencyCode);
    imgFrom = findViewById(R.id.imgFrom);
    edtFrom = findViewById(R.id.edtFrom);

    acToCurrencyCode = findViewById(R.id.acToCurrencyCode);
    imgTo = findViewById(R.id.imgTo);
    txtTo = findViewById(R.id.txtTo);

    btnConvert = findViewById(R.id.btnConvert);

    List<Country> countries = CountryService.getCountriesList(this);
    adapterFrom = new CountryAdapter(this, R.layout.country_list_item, countries);
    acFromCurrencyCode.setAdapter(adapterFrom);
    acFromCurrencyCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = view.findViewById(R.id.txtCurrencyCode);
        acFromCurrencyCode.setText(txt.getText().toString());
        ImageView imageView = (ImageView) view.findViewById(R.id.imgCountry);
        imgFrom.setImageDrawable(imageView.getDrawable());
      }
    });

    adapterTo = new CountryAdapter(this, R.layout.country_list_item, countries);
    acToCurrencyCode.setAdapter(adapterTo);
    acToCurrencyCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = view.findViewById(R.id.txtCurrencyCode);
        acToCurrencyCode.setText(txt.getText().toString());
        ImageView imageView = (ImageView) view.findViewById(R.id.imgCountry);
        imgTo.setImageDrawable(imageView.getDrawable());
      }
    });

    btnConvert.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        convert(
            acFromCurrencyCode.getText().toString(),
            acToCurrencyCode.getText().toString(),
            edtFrom.getText().toString()
        );
      }
    });
  }

  private static void setImg(ImageView imgView ,String imgUrl){

    int SDK_INT = android.os.Build.VERSION.SDK_INT;
    if (SDK_INT > 8)
    {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
          .permitAll().build();
      StrictMode.setThreadPolicy(policy);
      try {
        URL url = new URL(imgUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
        imgView.setImageBitmap(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void convert(String from, String to, String value){
    if(!onlyWord(to)){
      return;
    }
    if(!onlyWord(from)){
      return;
    }
    if(!hasNoSpecialChar(value) || !isDigit(value)){
      return;
    }
    loadCurrency(from,to,value);
  }

  public void loadCurrency(String currencyFromCode, String currencyToCode, String value){
    currencyRepository.getCurrencyFeedFromCode(currencyFromCode).subscribe(feed->{
      for(CurrencyExchangeItem item: feed.getCurrencyExchangeItems()){
        String toCode;
        toCode = item.getTitle().substring(item.getTitle().lastIndexOf("(")+1, item.getTitle().lastIndexOf(")")).trim();
        if (toCode.equals(currencyToCode)){
          String half = item.getDescription().substring(item.getDescription().indexOf("=")+1).trim();
          half = half.substring(0, half.indexOf(" ")).trim();
          BigDecimal unit = new BigDecimal(half);
          BigDecimal currencyValue = new BigDecimal(value);
          txtTo.setText( unit.multiply(currencyValue).toString() );
        }
      }
    });
  }

  private boolean isDigit(String digitString){
    String regex = "(?<=^| )\\d+(\\.\\d+)?(?=$| )";
    if(!digitString.matches(regex))
      return false;
    return true;
  }

  private boolean hasNoSpecialChar(String string){
    String regex = "\\`|\\~|\\!|\\@|\\#|\\$|\\%|\\^|\\&|\\*|\\(|\\)|\\+|\\=|\\[|\\{|\\]|\\}|\\||\\\\|\\'|\\<|\\,|\\>|\\?|\\/|\\\"\"|\\;|\\:";
    if(string.matches(regex))
      return false;
    return true;
  }

  private boolean onlyWord(String string){
    String regex ="^[a-z|A-Z]*$";
    if(string.matches(regex))
      return true;
    return false;
  }
}