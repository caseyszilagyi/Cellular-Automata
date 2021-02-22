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
public class GridDisplay extends ViewDisplay {

  private final Scene scene;
  private final Pane pane;

  private final String RECTANGLE = "rectangle";
  private final String HEXAGON = "hexagon";
  private final String TRIANGLE_UP = "triangle_up";
  private final String TRIANGLE_DOWN = "triangle_down";

  private int gridWidth, gridHeight;
  private double cellWidth, cellHeight;

  /**
   *
   * @param scene
   * @param pane
   */
  public GridDisplay(Scene scene, Pane pane) {
    super();
    this.scene = scene;
    this.pane = pane;
  }

  /**
   * @param cellColorSheet Array of integers for the cell colors
   */
  public void updateGrid(int[] cellColorSheet) {
    pane.getChildren().clear();

    double screenWidth = scene.getWidth() - getHorizontalBorderLength() * 2.0;
    double screenHeight = scene.getHeight() - (getVerticalBorderLength() + getHorizontalBorderLength());

    if(getIsMinimized()){
      screenWidth = screenWidth / 2.0;
    }

    int cellShapeKey = cellColorSheet[0];
    gridHeight = cellColorSheet[1];
    gridWidth = cellColorSheet[2];

    cellWidth =  screenWidth / gridWidth;
    cellHeight = screenHeight / gridHeight;

    switch(cellShapeKey){
      case 0 -> createRectangleGrid(cellWidth, cellHeight, cellColorSheet);
      case 1 -> createTriangleGrid(cellWidth, cellHeight, cellColorSheet);
      case 2 -> createHexagonalGrid(cellWidth, cellHeight, cellColorSheet);
    }
  }

  private void createRectangleGrid(double cellWidth, double cellHeight, int[] cellColorSheet){
    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {
        createCell(
            col * cellWidth + getHorizontalBorderLength(),
            row * cellHeight + getVerticalBorderLength(),
            cellColorSheet[3 + row * gridWidth + col],
            RECTANGLE
        );
      }
    }
  }

  private void createHexagonalGrid(double cellWidth, double cellHeight, int[] cellColorSheet){
    double horizontalBorderLength = -1 * cellWidth / 4;

    for(int row = 0; row < gridHeight; row++){
      for(int col = 0; col < gridWidth; col++){
        createCell(
            col * cellWidth + getHorizontalBorderLength() + horizontalBorderLength,
            row * cellHeight + getVerticalBorderLength(),
            cellColorSheet[3 + row * gridWidth + col],
            HEXAGON
        );
      }
      horizontalBorderLength = -1 * horizontalBorderLength;
    }
  }

  private void createTriangleGrid(double cellWidth, double cellHeight, int[] cellColorSheet){
    String trianglePointingDirection;

    for(int row = 0; row < gridHeight; row++){
      if (row % 2 != 0){
        trianglePointingDirection = TRIANGLE_UP;
      } else {
        trianglePointingDirection = TRIANGLE_DOWN;
      }
      for(int col = 0; col < gridWidth; col++){
        createCell(
            col * cellWidth + getHorizontalBorderLength(),
            row * cellHeight + getVerticalBorderLength(),
            cellColorSheet[3 + row * gridWidth + col],
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

  private void createCell(double x, double y, int colorCode, String cellShape) {
    ResourceBundle resourceBundle = getColorSheetResourceBundle();

    Polygon cell = new Polygon();

    switch (cellShape) {
      case RECTANGLE -> drawRectangleCell(cell, x, y);
      case HEXAGON -> drawHexagonCell(cell, x, y);
      case TRIANGLE_UP -> drawTriangleCellPointingUp(cell, x, y);
      case TRIANGLE_DOWN -> drawTriangleCellPointingDown(cell, x, y);
    }

    cell.setFill(Color.web(resourceBundle.getString(Integer.toString(colorCode))));
    cell.setStroke(Color.BLACK);

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