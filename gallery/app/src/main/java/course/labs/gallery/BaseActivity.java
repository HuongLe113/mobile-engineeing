package course.labs.gallery;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import course.labs.gallery.utils.Constant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    createView();
  }

  protected  abstract int getLayoutId();

  protected abstract void createView();

  public void showToast(String msg){
    if(msg !=null){
      Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "not thing to show", Toast.LENGTH_SHORT).show();
    }
  }

  public void showActivity(Class t) {
    Intent intent = new Intent(this, t);
    startActivity(intent);
  }

  public void showActivity(Class t, Bundle bundle) {
    Intent intent = new Intent(this, t);
    intent.putExtra(Constant.KEY_EXTRA, bundle);
    startActivity(intent);
  }

}
