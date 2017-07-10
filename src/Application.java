public class Application {

  public static void main(String[] args) {

    String filesPath = "/home/karandalvi/machine_learning/data/";

    SimpleLinearRegression slr = new
    SimpleLinearRegression(filesPath + "training.csv",  //path
                           "x",                         //feature
                           "y");                        //outcome

    System.out.println("Prediction -> x:20, y:" + slr.predict(20));
    System.out.println("Prediction -> x:30, y:" + slr.predict(30));
    System.out.println("RMSE on training data: " + slr.getRMSE());
    System.out.println("RMSE on test data: " + slr.getRMSE(filesPath + "test.csv"));
  }
}
