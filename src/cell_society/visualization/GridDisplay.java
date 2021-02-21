package cell_society.visualization;

import java.util.ResourceBundle;
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

  private final int HORIZONTAL_BORDER_LENGTH = 50;
  private final int VERTICAL_BORDER_LENGTH = 90;

  private final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources/";
  private final ResourceBundle resourceBundle = ResourceBundle.getBundle(VISUALIZATION_RESOURCE_PACKAGE + "properties/GridType");

  private final String RECTANGLE = "rectangle";
  private final String HEXAGON = "hexagon";
  private final String TRIANGLE = "triangle";
  private final String TRIANGLE_UP = "triangle_up";
  private final String TRIANGLE_DOWN = "triangle_down";

  private int gridWidth, gridHeight;
  private double cellWidth, cellHeight;

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
  public void updateGrid(String[] cellColorSheet, String gridShapeType) {
    pane.getChildren().clear();

    double screenWidth = scene.getWidth() - HORIZONTAL_BORDER_LENGTH * 2.0;
    double screenHeight = scene.getHeight() - (VERTICAL_BORDER_LENGTH + HORIZONTAL_BORDER_LENGTH);

    cellWidth =  screenWidth / gridWidth;
    cellHeight = screenHeight / gridHeight;

    switch(gridShapeType){
      case RECTANGLE -> createRectangleGrid(cellWidth, cellHeight, cellColorSheet);
      case HEXAGON -> createHexagonalGrid(cellWidth, cellHeight, cellColorSheet);
      case TRIANGLE -> createTriangleGrid(cellWidth, cellHeight, cellColorSheet);
    }
  }

  private void createRectangleGrid(double cellWidth, double cellHeight, String[] cellColorSheet){
    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {
        createCell(
            col * cellWidth + HORIZONTAL_BORDER_LENGTH,
            row * cellHeight + VERTICAL_BORDER_LENGTH,
            cellColorSheet[row * gridWidth + col],
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
            cellColorSheet[row * gridWidth + col],
            HEXAGON
        );
      }
      horizontalBorderLength = -1 * horizontalBorderLength;
    }
  }

  private void createTriangleGrid(double cellWidth, double cellHeight, String[] cellColorSheet){
    String trianglePointingDirection;

    for(int row = 0; row < gridHeight; row++){
      if (row % 2 == 0){
        trianglePointingDirection = TRIANGLE_UP;
      } else {
        trianglePointingDirection = TRIANGLE_DOWN;
      }
      for(int col = 0; col < gridWidth; col++){
        createCell(
            col * cellWidth + HORIZONTAL_BORDER_LENGTH,
            row * cellHeight + VERTICAL_BORDER_LENGTH,
            cellColorSheet[row * gridWidth + col],
            trianglePointingDirection
        );
        if (trianglePointingDirection.equals(TRIANGLE_UP)){
          trianglePointingDirection = TRIANGLE_DOWN;
        } else {
          trianglePointingDirection = TRIANGLE_UP;
        }
      }
    }
  }

  private void createCell(double x, double y, String colorHex, String cellShape) {
    Color defaultCellColor = Color.AZURE;

    Polygon cell = new Polygon();

    switch (cellShape) {
      case RECTANGLE -> drawRectangleCell(cell, x, y);
      case HEXAGON -> drawHexagonCell(cell, x, y);
      case TRIANGLE_UP -> drawTriangleCellPointingUp(cell, x, y);
      case TRIANGLE_DOWN -> drawTriangleCellPointingDown(cell, x, y);
    }

    if (colorHex != null) { // if the cell is empty
      cell.setFill(Color.web(colorHex));
      cell.setStroke(Color.BLACK);
      //cell.setStroke(Color.web(colorHex)); // <-- this is no border since it's the same fill
    } else {
      cell.setFill(defaultCellColor);
      //cell.setStroke(Color.GAINSBORO);
    }

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
        x, y + cellHeight * (1.0 / 4),
        x + cellWidth / 2.0, y - cellHeight * (1.0 / 4),
        x + cellWidth, y + cellHeight * (1.0 / 4),
        x + cellWidth, y + cellHeight * (3.0 / 4),
        x + cellWidth / 2.0, y + cellHeight * (5.0 / 4),
        x, y + cellHeight * (3.0 / 4)
    );
  }

  private void drawTriangleCellPointingUp(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x + cellWidth / 2.0, y,
        x + cellWidth * 1.5, y + cellHeight,
        x - cellWidth / 2.0, y + cellHeight
    );
  }

  private void drawTriangleCellPointingDown(Polygon cell, double x, double y) {
    cell.getPoints().addAll(
        x - cellWidth / 2.0, y,
        x + cellWidth * 1.5, y,
        x + cellWidth / 2.0, y + cellHeight
    );
  }

}