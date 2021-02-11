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

  private double cellWidth;
  private double currentScreenWidth, currentScreenHeight;

  public GridDisplay(Group root, Scene scene, int width, int height) {
    this.root = root;
    this.scene = scene;
    this.width = width;
    this.height = height;

    currentScreenWidth = 800;
    currentScreenHeight = 800;

    cellWidth = Math.min(currentScreenWidth / width, currentScreenHeight / height);
    createGrid();

    scene.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> currentWidth, Number oldWidth,
          Number newWidth) {
        currentScreenWidth = newWidth.doubleValue();
        createGrid();
      }
    });

    scene.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> currentHeight, Number oldHeight,
          Number newHeight) {
        currentScreenHeight = newHeight.doubleValue();
        createGrid();
      }
    });
  }

  private void createGrid() {

    root.getChildren().clear();

    if((currentScreenHeight / height) >= (currentScreenWidth / width)){
      cellWidth = currentScreenWidth / width;
    } else {
      cellWidth = currentScreenHeight / height;
    }


    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        createCell(col * cellWidth, row * cellWidth);
      }
    }
  }

  private void createCell(double x, double y) {
    Polygon cell = new Polygon();
    cell.getPoints().addAll(
        x, y,
        x + cellWidth, y,
        x + cellWidth, y + cellWidth,
        x, y + cellWidth
    );
    cell.setFill(Color.AZURE);
    cell.setStroke(Color.BLACK);
    root.getChildren().add(cell);
  }

}
