package MyPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MovieUtils is a utility class for data handling. It has basic methods for
 * - reading csv file into List<List<String>>
 * - getheader
 * - search data on given key
 */


public class MovieUtils {

public static List<List<String>> ReadFile(String delimiter, String path) throws FileNotFoundException, IOException {

  List<List<String>> records = new ArrayList<>();
  try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = br.readLine()) != null) {
          String[] values = line.split(delimiter);
          records.add(Arrays.asList(values));
          }
      }
  return records;
    }

public static List<String> getHeader(List<List<String>> records) {
    List<String> header = records.get(0);
    return header;
}  

public static List<String> searchDataOnIndex(List<List<String>> data, String searchString, int searchIndex, int resultIndex, boolean BreakAfterFirstMatch) {
        
  List<String> ratings = new ArrayList<>();
  for (List<String> record : data) {
      String movie_id = record.get(searchIndex);     
      String movie_rating = record.get(resultIndex);   

      if (movie_id.equals(searchString)) {
          ratings.add(movie_rating);
          if (BreakAfterFirstMatch) {
            break;
          }
      }
  }
  return ratings;
}   

}