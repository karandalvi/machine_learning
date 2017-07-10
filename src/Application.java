import java.util.List;
import java.util.ArrayList;
public class Application {

  public static void main(String[] args) {

    List<Object> data = new ArrayList<>();
    List<String> features = new ArrayList<String>();
    String outcome = "y";
    SimpleLinearRegression slr = new SimpleLinearRegression("/home/karandalvi/machine_learning/data/squares.csv", features, outcome);
  }
}
