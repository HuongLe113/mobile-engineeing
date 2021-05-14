package course.labs.changerate_curency.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
  static String File_History_Name = "history.json";

  public static List<Object[]> getHistoryList(Context context){
    List<Object[]> history = new ArrayList<>();
    FileInputStream fis = null;
    StringBuilder stringBuilder = new StringBuilder();
    try {
      File newFile = new File(File_History_Name);
      if(newFile.exists()){
        newFile.createNewFile();
      }
      fis = context.openFileInput(File_History_Name);
      InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
      BufferedReader reader = new BufferedReader(inputStreamReader);
      String line = reader.readLine();
      while (line != null) {
        stringBuilder.append(line).append('\n');
        line = reader.readLine();
      }
    } catch (IOException e) {
    } finally {
      Gson gson = new Gson();
      if (stringBuilder.toString().length()==0){
        return history;
      }
      JsonArray jsonArray = (JsonArray) gson.fromJson(stringBuilder.toString(), JsonArray.class);
      for (int i=0; i<jsonArray.size(); i++){
        JsonArray item = (JsonArray) jsonArray.get(i);
        history.add(new Object[]{item.get(0).getAsString(), item.get(1).getAsString(), item.get(2).getAsString(), item.get(3).getAsString(), item.get(4).getAsString()});
      }
    }
    return history;
  }

  public static void writeHistory(List<Object[]> history,Context context){
    Gson gson = new Gson();
    try (FileOutputStream fos = context.openFileOutput(File_History_Name, Context.MODE_PRIVATE)) {
      fos.write(gson.toJson(history).getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
