package cell_society.visualization;

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

  public GridDisplay(Group root, Scene scene, int width, int height) {
    this.root = root;
    this.scene = scene;
    this.width = width;
    this.height = height;

    currentScreenWidth = 800;
    currentScreenHeight = 800;

    createGrid();

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
        createGrid();
      }
    });

    scene.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> currentHeight, Number oldHeight, Number newHeight) {
        currentScreenHeight = newHeight.doubleValue();
        createGrid();
      }
    });
  }

  private void createGrid() {
    root.getChildren().clear();

    double maxPossibleCellWidth = currentScreenWidth / width;
    double maxPossibleCellHeight = currentScreenHeight / height;

    if (maxPossibleCellHeight >= maxPossibleCellWidth) {
      cellSideLength = maxPossibleCellWidth;
    } else {
      cellSideLength = maxPossibleCellHeight;
    }

    double whiteSpace = (currentScreenWidth - (width * cellSideLength)) / 2.0;
    if (whiteSpace <= 0){
      whiteSpace = 0;
    }

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        createCell(col * cellSideLength + whiteSpace, row * cellSideLength);
      }
    }
  }

  private void createCell(double x, double y) {
    Polygon cell = new Polygon();
    cell.getPoints().addAll(
        x, y,
        x + cellSideLength, y,
        x + cellSideLength, y + cellSideLength,
        x, y + cellSideLength
    );
    cell.setFill(Color.AZURE);
    cell.setStroke(Color.BLACK);
    root.getChildren().add(cell);
  }

}
