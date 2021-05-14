package course.labs.changerate_curency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter {
  Context context;
  List<Object[]> items;

  public HistoryAdapter(@NonNull Context context, int resource,  @NonNull List objects) {
    super(context, resource, objects);
    this.items = objects;
    this.context = context;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Object[] item = items.get(position);
    LayoutInflater inflater = LayoutInflater.from(this.getContext());
    View row = inflater.inflate(R.layout.history_item, null);
    TextView date = row.findViewById(R.id.txtHistoryDate);
    TextView formValue = row.findViewById(R.id.txtHistoryFromValue);
    TextView form = row.findViewById(R.id.txtHistoryFrom);
    TextView toValue = row.findViewById(R.id.txtHistoryToValue);
    TextView to= row.findViewById(R.id.txtHistoryTo);
    date.setText(item[0].toString());
    formValue.setText(item[1].toString());
    form.setText(item[2].toString());
    toValue.setText(item[3].toString());
    to.setText(item[4].toString());
    return row;
  }

}
