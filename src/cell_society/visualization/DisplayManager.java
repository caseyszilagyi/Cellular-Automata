package cell_society.visualization;

import java.util.Random;
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
    new GridDisplay(root, scene, gridWidth, gridHeight, cellColorSheet);
  }
}
