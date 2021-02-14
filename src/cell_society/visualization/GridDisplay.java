package cell_society.visualization;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GridDisplay {

  private final Pane root;
  private int width, height;
  private double cellSideLength, currentScreenWidth, currentScreenHeight;

  public GridDisplay(Pane root, Scene scene, int width, int height) {
    this.root = root;
    this.width = width;
    this.height = height;
    currentScreenWidth = scene.getWidth();
    currentScreenHeight = scene.getHeight();
  }

  public void setCurrentScreenWidth(double width){
    currentScreenWidth = width;
  }

  public void setCurrentScreenHeight(double height){
    currentScreenHeight = height;
  }

  /**
   *
   * @param cellColorSheet Array of integers for the cell colors
   */
  public void updateGrid(String[] cellColorSheet) {
    root.getChildren().clear();

    double maxPossibleCellWidth = currentScreenWidth / width;
    double maxPossibleCellHeight = currentScreenHeight / height;

    if (maxPossibleCellHeight >= maxPossibleCellWidth) {
      cellSideLength = maxPossibleCellWidth;
    } else {
      cellSideLength = maxPossibleCellHeight;
    }

    // for centering horizontally
    double whiteSpace = (currentScreenWidth - (width * cellSideLength)) / 2.0;

    int colorSheetIndex = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        createCell(col * cellSideLength + whiteSpace, row * cellSideLength, cellColorSheet[colorSheetIndex]);
        colorSheetIndex++;
      }
    }
  }

  private void createCell(double x, double y, String colorHex) {
    Polygon cell = new Polygon();

    cell.getPoints().addAll(
        x, y,
        x + cellSideLength, y,
        x + cellSideLength, y + cellSideLength,
        x, y + cellSideLength
    );

    cell.setFill(Color.web(colorHex));
    cell.setStroke(Color.GAINSBORO);
    root.getChildren().add(cell);
  }

}
