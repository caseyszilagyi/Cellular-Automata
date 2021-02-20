package cell_society.visualization;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 * The GridDisplay class is responsible for creating and updating the main display grid that is
 * viewed by the user.
 *
 * @author Donghan Park
 */
public class GridDisplay {

  private final Pane root;
  private double cellSideLength, currentScreenWidth, currentScreenHeight;

  private int width, height;

  private final int BORDER_LENGTH = 50;

  private final String HEXAGON = "hexagon";
  private final String RECTANGLE = "rectangle";
  private final String TRIANGLE_UP = "triangleUp";
  private final String TRIANGLE_DOWN = "triangleDown";

  public GridDisplay(Pane root, Scene scene) {
    this.root = root;
    currentScreenWidth = scene.getWidth() - BORDER_LENGTH * 2;
    currentScreenHeight = scene.getHeight() - BORDER_LENGTH * 2;
  }

  public void setCurrentScreenWidth(double width) {
    currentScreenWidth = width - BORDER_LENGTH * 2;
  }

  public void setCurrentScreenHeight(double height) {
    currentScreenHeight = height - BORDER_LENGTH * 2;
  }

  public void setGridDimensions(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
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

    createTriangleGrid(whiteSpaceX, whiteSpaceY, cellColorSheet);
  }

  private void createRectangleGrid(double whiteSpaceX, double whiteSpaceY, String[] cellColorSheet){
    int colorSheetIndex = 0;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        createCell(
            col * cellSideLength + whiteSpaceX,
            row * cellSideLength + whiteSpaceY,
            cellColorSheet[colorSheetIndex],
            RECTANGLE
        );
        colorSheetIndex++;
      }
    }
  }

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
            col * cellSideLength / 2.0 + whiteSpaceX,
            row * cellSideLength + whiteSpaceY,
            cellColorSheet[colorSheetIndex],
            triangleDirection
        );
        colorSheetIndex++;
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
    root.getChildren().add(cell);
  }


  private void drawRectangleCell(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x, y,
        x + cellSideLength, y,
        x + cellSideLength, y + cellSideLength,
        x, y + cellSideLength
    );
  }

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
        x + cellSideLength / 2.0, y,
        x + cellSideLength, y + cellSideLength
    );
  }

  private void drawTriangleCellDown(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x, y,
        x + cellSideLength, y,
        x + cellSideLength / 2.0, y + cellSideLength
    );
  }
}