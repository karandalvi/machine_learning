import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SimpleLinearRegression {

    Map<String, List<Double>> data;
    String feature;
    String outcome;

    double slope;
    double intercept;

    public SimpleLinearRegression(String path, String feature, String outcome) {

        data = new HashMap<>();
        this.feature = feature;
        this.outcome = outcome;
        this.readCSV(path, this.data);
        this.buildModel();
    }

    private void readCSV(String path, Map<String, List<Double>> data) {

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
                        List<Double> values = new ArrayList<>();
                        featureList.add(token[i]);
                        data.put(token[i], values);
                    }

                    firstLine = false;
                }
                else {

                  for (int i=0; i<token.length; i++) {
                      data.get(featureList.get(i)).add(Double.parseDouble(token[i]));
                  }
                }
            }

            if (!data.containsKey(outcome) || !data.containsKey(feature))
                throw new Exception();

        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    private void buildModel() {

        double featureMean = 0;
        double outcomeMean = 0;

        for (double i: data.get(feature)) {
            featureMean += (double) i;
        }
        featureMean = featureMean / data.get(feature).size();

        for (double i: data.get(outcome)) {
            outcomeMean += (double) i;
        }
        outcomeMean = outcomeMean / data.get(outcome).size();

        double numerator = 0;
        double denominator = 0;

        for (int i=0; i<data.get(outcome).size(); i++) {
            numerator += (data.get(outcome).get(i) - outcomeMean) *
                      (data.get(feature).get(i) - featureMean);

            denominator += Math.pow((data.get(feature).get(i)
                              - featureMean), 2);
        }

        this.slope = numerator / denominator;
        this.intercept = outcomeMean - this.slope * featureMean;

        System.out.println("Model built successfully");
    }

    public double predict(double feature) {
        return this.intercept + this.slope * feature;
    }

    public double getSlope() {
        return this.slope;
    }

    public double getIntercept() {
        return this.intercept;
    }

    public double getRMSE() {
        return this.getRMSE(this.data);
    }

    public double getRMSE(Map<String, List<Double>> data) {

        double rss = 0;
        for (int i=0; i<data.get(outcome).size(); i++) {

          double residue = data.get(outcome).get(i) -
                            this.predict(data.get(feature).get(i));

            rss += Math.pow(residue, 2);
        }
        return rss / data.get(outcome).size();
    }

    public double getRMSE(String path) {

        Map<String, List<Double>> testData = new HashMap<>();
        this.readCSV(path, testData);
        return this.getRMSE(testData);
    }
}
