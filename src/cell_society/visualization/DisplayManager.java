package cell_society.visualization;

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

    createDisplayGrid();
  }

  private void createDisplayGrid(){
    GridDisplay grid = new GridDisplay(root, scene, gridWidth, gridHeight);
  }
}
