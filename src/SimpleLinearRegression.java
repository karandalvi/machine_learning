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

  double slope;
  double intercept;

  public SimpleLinearRegression(String path, List<String> features, String outcome) {

      data = new HashMap<>();
      this.features = features;
      this.outcome = outcome;
      this.readCSV(path);
      this.buildModel();
  }

  private void readCSV(String path) {

      System.out.println("Reading csv file at " + path);
      String csvFile = path;
      String delimiter = ",";

      String line;
      boolean firstLine = true;
      List<String> featureList = new ArrayList<String>();

      try {

          BufferedReader br = new BufferedReader(new FileReader(csvFile));
          while ((line = br.readLine()) != null) {

              String[] token = line.split(delimiter);

              if (firstLine) {

                  for (int i=0; i<token.length; i++) {
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

        if (!data.containsKey(outcome) || !data.containsKey(features.get(0)))
          throw new Exception();

      }
      catch (Exception e) {
          System.out.println(e);
      }

    }

 // sum((xi-mean(x)) * (yi-mean(y))) / sum((xi – mean(x))^2)
    private void buildModel() {
        double featureMean = 0;
        double outcomeMean = 0;

        for (int i: data.get(features.get(0))) {
            featureMean += (double) i;
        }
        featureMean = featureMean / data.get(features.get(0)).size();

        for (int i: data.get(outcome)) {
            outcomeMean += (double) i;
        }
        outcomeMean = outcomeMean / data.get(outcome).size();

        double numerator = 0;
        double denominator = 0;

        for (int i=0; i<data.get(outcome).size(); i++) {
              numerator += ((double) data.get(outcome).get(i) - outcomeMean) *
                      ((double) data.get(features.get(0)).get(i) - featureMean);
              denominator += Math.pow(((double) data.get(features.get(0)).get(i)
                              - featureMean), 2);
        }

      slope = numerator / denominator;
      intercept = outcomeMean - slope * featureMean;
      
      System.out.println("Model built successfully");
    }
}
