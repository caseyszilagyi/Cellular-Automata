package cell_society.visualization;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;

public class GraphDisplay {

  private final Pane pane;
  private final Scene scene;

  public GraphDisplay(Pane pane, Scene scene){
    this.pane = pane;
    this.scene = scene;

    createGraph("yes", "no");
  }

  private void createGraph(String xLabel, String yLabel){
//    CategoryAxis xAxis = new CategoryAxis();
//    NumberAxis yAxis = new NumberAxis();
//    xAxis.setLabel(xLabel);
//    yAxis.setLabel(yLabel);
//
//    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//    barChart.setTitle("BAR CHART TITLE");
//
//    XYChart.Series<String, Number> dataSeries = new XYChart.Series();
//    dataSeries.getData().add(new XYChart.Data("", 200));
//
//    chart.getData().add(dataSeries);
//
//    pane.getChildren().add(chart);
  }

}
