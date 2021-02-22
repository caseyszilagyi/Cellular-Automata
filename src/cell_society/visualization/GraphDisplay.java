package cell_society.visualization;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;

/**
 * @author Donghan Park
 */
public class GraphDisplay extends ViewDisplay {

  private final Pane pane;
  private final Scene scene;

  /**
   *
   * @param scene
   * @param pane
   */
  public GraphDisplay(Scene scene, Pane pane){
    this.scene = scene;
    this.pane = pane;
  }

  public void updateGraph(){
    pane.getChildren().clear();

    PieChart chart = new PieChart();
    chart.setLegendVisible(false);

    double screenWidth = scene.getWidth() - getHorizontalBorderLength() * 2.0;
    double screenHeight = scene.getHeight() - (getVerticalBorderLength() + getHorizontalBorderLength());

    //screenWidth = screenWidth / 2.0;

    Map<Integer, Integer> cellTypesMap = getCurrentSim().getCellDistribution();

    chart.getData().clear();

    ResourceBundle resourceBundle = getColorSheetResourceBundle();

    for(Map.Entry<Integer, Integer> entry : cellTypesMap.entrySet()){
      PieChart.Data slice = new PieChart.Data(resourceBundle.getString(String.format("%dname", entry.getKey())), entry.getValue());
      chart.getData().add(slice);
      slice.getNode().setStyle(String.format("-fx-pie-color: #%s;" ,resourceBundle.getString(Integer.toString(entry.getKey()))));
      slice.setName(String.format("%s: %d", slice.getName(), (int) slice.getPieValue()));
    }

    chart.setPrefSize(screenWidth, screenHeight);
    chart.setLayoutX(scene.getWidth() - getHorizontalBorderLength() / 2.0 - screenWidth);
    chart.setLayoutY(getVerticalBorderLength());

    pane.getChildren().add(chart);
  }

}
