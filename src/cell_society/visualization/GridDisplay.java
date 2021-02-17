package cell_society.visualization;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GridDisplay {

  private final Pane root;
  private double cellSideLength, currentScreenWidth, currentScreenHeight;

  private int width, height;

  private final int BORDER_LENGTH = 50;

  public GridDisplay(Pane root, Scene scene) {
    this.root = root;
    currentScreenWidth = scene.getWidth() - BORDER_LENGTH * 2;
    currentScreenHeight = scene.getHeight() - BORDER_LENGTH * 2;
  }

  public void setCurrentScreenWidth(double width){
    currentScreenWidth = width - BORDER_LENGTH * 2;
  }

  public void setCurrentScreenHeight(double height){
    currentScreenHeight = height - BORDER_LENGTH * 2;
  }

  public void setGridDimensions(int width, int height){
    this.width = width;
    this.height = height;
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
    double whiteSpaceX = (currentScreenWidth - (width * cellSideLength)) / 2.0 + BORDER_LENGTH;
    double whiteSpaceY = (currentScreenHeight - (height * cellSideLength)) / 2.0 + BORDER_LENGTH;

    int colorSheetIndex = 0;
    double hexagonWidth = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        createCell(col * cellSideLength + whiteSpaceX + hexagonWidth, row * cellSideLength * (2.0 / 3) + whiteSpaceY, cellColorSheet[colorSheetIndex]);
        colorSheetIndex++;
      }
      if (hexagonWidth == 0){
        hexagonWidth = cellSideLength / 2;
      } else {
        hexagonWidth = 0;
      }
    }
  }

  private void createCell(double x, double y, String colorHex) {
    Polygon cell = new Polygon();

    /*
    cell.getPoints().addAll(
      x, y,
      x + cellSideLength, y,
      x + cellSideLength, y + cellSideLength,
      x, y + cellSideLength
    );
     */
    cell.getPoints().addAll(
        x + cellSideLength * 0.5, y,
        x + cellSideLength, y + cellSideLength / 3.0,
        x + cellSideLength, y + cellSideLength * (2.0 / 3.0),
        x + cellSideLength * 0.5, y + cellSideLength,
        x, y + cellSideLength * (2.0 / 3.0),
        x, y + cellSideLength / 3.0

    );

    if(colorHex != null){
      cell.setFill(Color.web(colorHex));
    } else {
      cell.setFill(Color.AZURE);
    }
    cell.setStroke(Color.GAINSBORO);
    root.getChildren().add(cell);
  }

}
