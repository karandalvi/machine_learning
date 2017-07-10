import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SimpleLinearRegression {

  Map<String, List<Integer>> data;
  List<String> features;
  String outcome;

  public SimpleLinearRegression(String path, List<String> features, String outcome) {

      data = new HashMap<>();
      this.features = features;
      this.outcome = outcome;
      this.readCSV(path);
  }

  private void readCSV(String path) {

      System.out.println("Reading csv file at " + path);
      String csvFile = path;
      String delimiter = ",";

      String line;
      boolean firstLine = true;
      boolean outcomeFound = false;
      List<String> featureList = new ArrayList<String>();

      try {

          BufferedReader br = new BufferedReader(new FileReader(csvFile));
          while ((line = br.readLine()) != null) {

              String[] token = line.split(delimiter);

              if (firstLine) {

                  for (int i=0; i<token.length; i++) {
                      outcomeFound = outcomeFound || token[i].equals(outcome);
                      List<Integer> values = new ArrayList<>();
                      featureList.add(token[i]);
                      data.put(token[i], values);
                  }

                  firstLine = false;
            }
            else {

                for (int i=0; i<token.length; i++) {
                    data.get(featureList.get(i)).add(Integer.parseInt(token[i]));
                }
            }
        }

        if (!outcomeFound)
          throw new Exception();

      }
      catch (Exception e) {
          System.out.println(e);
      }

    }

}
