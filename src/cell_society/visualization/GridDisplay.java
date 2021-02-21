package cell_society.visualization;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * The GridDisplay class is responsible for creating and updating the main display grid that is
 * viewed by the user.
 *
 * @author Donghan Park
 */
public class GridDisplay {

  private final Scene scene;
  private final Pane pane;

  private int gridWidth, gridHeight;
  private double cellWidth, cellHeight;

  private final int HORIZONTAL_BORDER_LENGTH = 50;
  private final int VERTICAL_BORDER_LENGTH = 50;

  private final String HEXAGON = "hexagon";
  private final String RECTANGLE = "rectangle";
  private final String TRIANGLE_UP = "triangleUp";
  private final String TRIANGLE_DOWN = "triangleDown";

  public GridDisplay(Scene scene, Pane pane) {
    this.scene = scene;
    this.pane = pane;
  }

  public void setGridDimensions(int gridWidth, int gridHeight) {
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
  }

  /**
   * @param cellColorSheet Array of integers for the cell colors
   */
  public void updateGrid(String[] cellColorSheet) {
    pane.getChildren().clear();

    double screenWidth = scene.getWidth() - HORIZONTAL_BORDER_LENGTH * 2.0;
    double screenHeight = scene.getHeight() - VERTICAL_BORDER_LENGTH * 2.0;

    cellWidth =  screenWidth / gridWidth;
    cellHeight = screenHeight / gridHeight;

    createTriangleGrid(cellWidth, cellHeight, cellColorSheet);
  }

  private void createRectangleGrid(double cellWidth, double cellHeight, String[] cellColorSheet){
    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {
        createCell(
            col * cellWidth + HORIZONTAL_BORDER_LENGTH,
            row * cellHeight + VERTICAL_BORDER_LENGTH,
            cellColorSheet[row * gridHeight + col],
            RECTANGLE
        );
      }
    }
  }

  private void createHexagonalGrid(double cellWidth, double cellHeight, String[] cellColorSheet){
    double horizontalBorderLength = -1 * cellWidth / 4;

    for(int row = 0; row < gridHeight; row++){
      for(int col = 0; col < gridWidth; col++){
        createCell(
            col * cellWidth + HORIZONTAL_BORDER_LENGTH + horizontalBorderLength,
            row * cellHeight + VERTICAL_BORDER_LENGTH,
            cellColorSheet[row * gridHeight + col],
            HEXAGON
        );
      }
      horizontalBorderLength = -1 * horizontalBorderLength;
    }
  }

  private void createTriangleGrid(double cellWidth, double cellHeight, String[] cellColorSheet){
    String triangleType;

    for(int row = 0; row < gridHeight; row++){
      if (row % 2 == 0){
        triangleType = TRIANGLE_UP;
      } else {
        triangleType = TRIANGLE_DOWN;
      }
      for(int col = 0; col < gridWidth; col++){
        createCell(
            col * cellWidth + HORIZONTAL_BORDER_LENGTH,
            row * cellHeight + VERTICAL_BORDER_LENGTH,
            cellColorSheet[row * gridHeight + col],
            triangleType
        );
        if (triangleType.equals(TRIANGLE_UP)){
          triangleType = TRIANGLE_DOWN;
        } else {
          triangleType = TRIANGLE_UP;
        }
      }
    }
  }

  private void createCell(double x, double y, String colorHex, String cellShape) {
    Polygon cell = new Polygon();

    // how to not use this switch statement..?
    switch (cellShape) {
      case RECTANGLE -> drawRectangleCell(cell, x, y);
      case HEXAGON -> drawHexagonCell(cell, x, y);
      case TRIANGLE_UP -> drawTriangleCellUp(cell, x, y);
      case TRIANGLE_DOWN -> drawTriangleCellDown(cell, x, y);
    }

    if (colorHex != null) {
      cell.setFill(Color.web(colorHex));
    } else {
      cell.setFill(Color.AZURE);
    }

    cell.setStroke(Color.GAINSBORO); // border color (can be changed by user)
    pane.getChildren().add(cell);
  }


  private void drawRectangleCell(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x, y,
        x + cellWidth, y,
        x + cellWidth, y + cellHeight,
        x, y + cellHeight
    );
  }


  private void drawHexagonCell(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x, y + cellHeight * (1.0 / 3),
        x + cellWidth / 2.0, y - cellHeight * (1.0 / 3),
        x + cellWidth, y + cellHeight * (1.0 / 3),
        x + cellWidth, y + cellHeight * (2.0 / 3),
        x + cellWidth / 2.0, y + cellHeight * (4.0 / 3),
        x, y + cellHeight * (2.0 / 3)
    );
  }

  private void drawTriangleCellUp(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x + cellWidth / 2.0, y,
        x + cellWidth * 1.5, y + cellHeight,
        x - cellWidth / 2.0, y + cellHeight
    );
  }

  private void drawTriangleCellDown(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x - cellWidth / 2.0, y,
        x + cellWidth * 1.5, y,
        x + cellWidth / 2.0, y + cellHeight
    );
  }

}