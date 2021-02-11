package cell_society.visualization;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GridDisplay {

  private final Group root;
  private int width, height;

  private double cellSideLength;
  private double currentScreenWidth, currentScreenHeight;

  public GridDisplay(Group root, Scene scene, int width, int height) {
    this.root = root;
    this.width = width;
    this.height = height;
    currentScreenWidth = scene.getWidth();
    currentScreenHeight = scene.getHeight();
  }

  public void setGridDimensions(int width, int height){
    this.width = width;
    this.height = height;
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
  public void updateGrid(int[] cellColorSheet) {
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

  private void createCell(double x, double y, int colorCode) {
    Polygon cell = new Polygon();

    cell.getPoints().addAll(
        x, y,
        x + cellSideLength, y,
        x + cellSideLength, y + cellSideLength,
        x, y + cellSideLength
    );

    Color cellColor = switch (colorCode) {
      case 1 -> Color.BROWN;
      case 2 -> Color.CADETBLUE;
      default -> Color.WHITE;
    };

    cell.setFill(cellColor);
    cell.setStroke(Color.BLACK);
    root.getChildren().add(cell);
  }

}
