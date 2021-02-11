package cell_society.visualization;

import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;

public class DisplayManager {

  private final Group root;
  private final Scene scene;

  private int gridWidth, gridHeight;

  public DisplayManager(Group root, Scene scene) {
    this.root = root;
    this.scene = scene;

    gridWidth = 20;
    gridHeight = 30;

    // create random dummy cell color sheet for testing purposes
    int[] cellColorSheet = new int[gridWidth * gridHeight];
    for(int i = 0; i < gridWidth * gridHeight; i++){
      cellColorSheet[i] = new Random().nextInt(3);
    }

    createDisplayGrid(cellColorSheet);
  }

  private void createDisplayGrid(int[] cellColorSheet){
    GridDisplay grid = new GridDisplay(root, scene, gridWidth, gridHeight);

    grid.updateGrid(cellColorSheet);

    scene.widthProperty().addListener((currentWidth, oldWidth, newWidth) -> {
      grid.setCurrentScreenWidth(newWidth.doubleValue());
      grid.updateGrid(cellColorSheet);
    });

    scene.heightProperty().addListener((currentHeight, oldHeight, newHeight) -> {
      grid.setCurrentScreenHeight(newHeight.doubleValue());
      grid.updateGrid(cellColorSheet);
    });
  }
}
