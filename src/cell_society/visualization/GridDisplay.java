package cell_society.visualization;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

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

  private final int BORDER_LENGTH = 50;

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

    double screenWidth = scene.getWidth() - BORDER_LENGTH * 2.0;
    double screenHeight = scene.getHeight() - BORDER_LENGTH * 2.0;

    cellWidth =  screenWidth / gridWidth;
    cellHeight = screenHeight / gridHeight;

    createRectangleGrid(cellWidth, cellHeight, cellColorSheet);
  }

  private void createRectangleGrid(double cellWidth, double cellHeight, String[] cellColorSheet){
    int colorSheetIndex = 0;

    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {
        createCell(
            col * cellWidth + BORDER_LENGTH,
            row * cellHeight + BORDER_LENGTH,
            cellColorSheet[colorSheetIndex],
            RECTANGLE
        );
        colorSheetIndex++;
      }
    }
  }

  /*
  private void createHexagonGrid(double whiteSpaceX, double whiteSpaceY, String[] cellColorSheet){
    int colorSheetIndex = 0;
    double hexagonWidth = -1 * cellSideLength / 4;
    double hexagonHeightMultiplier = 2.0 / 3;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        createCell(
            col * cellSideLength + whiteSpaceX + hexagonWidth,
            row * cellSideLength * hexagonHeightMultiplier + whiteSpaceY,
            cellColorSheet[colorSheetIndex],
            HEXAGON
        );
        colorSheetIndex++;
      }
      hexagonWidth = -1 * hexagonWidth;
    }
  }

  private void createTriangleGrid(double whiteSpaceX, double whiteSpaceY, String[] cellColorSheet) {
    int colorSheetIndex = 0;
    boolean triangleDirectionUp;

    for (int row = 0; row < height; row++) {
      if (row % 2 == 0){
        triangleDirectionUp = true;
      } else {
        triangleDirectionUp = false;
      }
      for (int col = 0; col < width; col++) {
        String triangleDirection;
        if(triangleDirectionUp){
          triangleDirection = TRIANGLE_UP;
        } else {
          triangleDirection = TRIANGLE_DOWN;
        }
        triangleDirectionUp = !triangleDirectionUp;
        createCell(
            col * cellSideLength + whiteSpaceX / 2.0,
            row * cellSideLength + whiteSpaceY,
            cellColorSheet[colorSheetIndex],
            triangleDirection
        );
        colorSheetIndex++;
      }
    }
  }

   */

  private void createCell(double x, double y, String colorHex, String cellShape) {
    Polygon cell = new Polygon();

    // how to not use this switch statement..?
    switch (cellShape) {
      case RECTANGLE -> drawRectangleCell(cell, x, y);
      //case HEXAGON -> drawHexagonCell(cell, x, y);
      //case TRIANGLE_UP -> drawTriangleCellUp(cell, x, y);
      //case TRIANGLE_DOWN -> drawTriangleCellDown(cell, x, y);
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

  /*
  private void drawHexagonCell(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x + cellSideLength * 0.5, y,
        x + cellSideLength, y + cellSideLength / 3.0,
        x + cellSideLength, y + cellSideLength * (2.0 / 3.0),
        x + cellSideLength * 0.5, y + cellSideLength,
        x, y + cellSideLength * (2.0 / 3.0),
        x, y + cellSideLength / 3.0
    );
  }

  private void drawTriangleCellUp(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x, y + cellSideLength,
        x + cellSideLength, y,
        x + cellSideLength * 2.0, y + cellSideLength
    );
  }

  private void drawTriangleCellDown(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x, y,
        x + cellSideLength * 2.0, y,
        x + cellSideLength, y + cellSideLength
    );
  }

   */
}