package cell_society.visualization;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;

/**
 * The GraphDisplay class is responsible for creating and updating the
 * main display pie graph that is viewed by the user.
 *
 * @author Donghan Park
 */
public class GraphDisplay extends ViewDisplay {

  private final Pane pane;
  private final Scene scene;

  /**
   * Constructor that creates an instance of the GraphDisplay object
   * @param scene Main scene of the stage
   * @param pane Pane of where the graph components will be stored
   */
  public GraphDisplay(Scene scene, Pane pane){
    this.scene = scene;
    this.pane = pane;
  }

  /**
   * Updates the display graph view with the most up-to-date cell population information
   * @param cellTypesMap Map of entries, with keys being an integer code for the name of cell
   *                     type, and values being the current population of that cell type
   */
  public void updateGraph(Map<Integer, Integer> cellTypesMap){
    pane.getChildren().clear();

    PieChart chart = new PieChart();
    chart.setLegendVisible(false);

    double screenWidth = scene.getWidth() - getHorizontalBorderLength() * 2.0;
    double screenHeight = scene.getHeight() - (getVerticalBorderLength() + getHorizontalBorderLength());

    double xPosition = getHorizontalBorderLength();
    if(getIsMinimized()){
      screenWidth = screenWidth / 2.0;
      xPosition = scene.getWidth() - getHorizontalBorderLength() / 2.0 - screenWidth;
    }

    chart.getData().clear();

    ResourceBundle resourceBundle = getColorSheetResourceBundle();

    for(Map.Entry<Integer, Integer> entry : cellTypesMap.entrySet()){
      String cellLabelName = resourceBundle.getString(String.format("%dname", entry.getKey()));
      int cellPopulation = entry.getValue();

      PieChart.Data slice = new PieChart.Data(cellLabelName, cellPopulation);
      chart.getData().add(slice);

      String colorHexValue = resourceBundle.getString(Integer.toString(entry.getKey()));
      slice.getNode().setStyle(String.format("-fx-pie-color: #%s;" , colorHexValue));
      slice.setName(String.format("%s: %d", slice.getName(), (int) slice.getPieValue()));
    }

    chart.setPrefSize(screenWidth, screenHeight);
    chart.setLayoutX(xPosition);
    chart.setLayoutY(getVerticalBorderLength());

    pane.getChildren().add(chart);
  }

}
