package cell_society.visualization;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GridDisplay {

  private final Scene scene;
  private final Group root;
  private int width, height;

  private double cellSideLength;
  private double currentScreenWidth, currentScreenHeight;

  private int[] cellColorSheet;

  public GridDisplay(Group root, Scene scene, int width, int height, int[] cellColorSheet) {
    this.root = root;
    this.scene = scene;
    this.width = width;
    this.height = height;
    this.cellColorSheet = cellColorSheet;

    currentScreenWidth = 800;
    currentScreenHeight = 800;

    createGrid(cellColorSheet);

    startScreenSizeChangedListeners();
  }

  public void setGridDimensions(int width, int height){
    this.width = width;
    this.height = height;
  }

  private void startScreenSizeChangedListeners(){
    scene.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> currentWidth, Number oldWidth, Number newWidth) {
        currentScreenWidth = newWidth.doubleValue();
        createGrid(cellColorSheet);
      }
    });

    scene.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> currentHeight, Number oldHeight, Number newHeight) {
        currentScreenHeight = newHeight.doubleValue();
        createGrid(cellColorSheet);
      }
    });
  }

  private void createGrid(int[] cellColorSheet) {
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

    Color cellColor = Color.WHITE;
    switch(colorCode){
      case 1:
        cellColor = Color.BROWN;
        break;
      case 2:
        cellColor = Color.CADETBLUE;
        break;
    }

    cell.setFill(cellColor);
    cell.setStroke(Color.BLACK);
    root.getChildren().add(cell);
  }

}
