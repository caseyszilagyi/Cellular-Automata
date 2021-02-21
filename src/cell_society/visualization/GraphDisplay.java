package cell_society.visualization;

import cell_society.backend.Simulation;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;

public class GraphDisplay {

  private final Pane pane;
  private final Scene scene;

  private final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources/";
  private ResourceBundle resourceBundle;

  private final int HORIZONTAL_BORDER_LENGTH = 50;
  private final int VERTICAL_BORDER_LENGTH = 90;

  private Simulation currentSim;
  private String currentSimulationType;

  public GraphDisplay(Scene scene, Pane pane){
    this.scene = scene;
    this.pane = pane;
  }

  public void setCurrentSim(Simulation currentSim){
    this.currentSim = currentSim;
  }

  public void setSimulationType(String simulationType){
    currentSimulationType = simulationType;
  }

  public void updateGraph(){
    pane.getChildren().clear();

    PieChart chart = new PieChart();
    chart.setLegendVisible(false);

    double screenWidth = scene.getWidth() - HORIZONTAL_BORDER_LENGTH * 2.0;
    double screenHeight = scene.getHeight() - (VERTICAL_BORDER_LENGTH + HORIZONTAL_BORDER_LENGTH);

    Map<Integer, Integer> cellTypesMap = currentSim.getCellDistribution();

    chart.getData().clear();

    resourceBundle = ResourceBundle.getBundle(VISUALIZATION_RESOURCE_PACKAGE + "properties/simulationColorCodes/" + currentSimulationType);

    for(Map.Entry<Integer, Integer> entry : cellTypesMap.entrySet()){
      PieChart.Data slice = new PieChart.Data(resourceBundle.getString(entry.getKey() + "name"), entry.getValue());
      chart.getData().add(slice);
      slice.getNode().setStyle("-fx-pie-color: #" + resourceBundle.getString(Integer.toString(entry.getKey())) + ";");
      slice.setName(slice.getName() + ": " + (int) slice.getPieValue());
    }

    chart.setPrefWidth(screenWidth);
    chart.setPrefHeight(screenHeight);
    chart.setLayoutX(HORIZONTAL_BORDER_LENGTH);
    chart.setLayoutY(VERTICAL_BORDER_LENGTH);

    pane.getChildren().add(chart);
  }

}
