package cell_society.visualization;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * The DisplayManager class is responsible for maintaining and updating the display grid
 * and various buttons for controlling visualization.
 *
 * @author Donghan Park
 */
public class DisplayManager {

  private final Group root;
  private final Scene scene;

  private int gridWidth, gridHeight;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Group root, Scene scene) {
    this.root = root;
    this.scene = scene;

    // temporarily hard-coded size values
    gridWidth = 20;
    gridHeight = 30;

    // create random dummy cell color sheet for testing purposes
    int[] cellColorSheet = new int[gridWidth * gridHeight];
    for(int i = 0; i < gridWidth * gridHeight; i++){
      cellColorSheet[i] = new Random().nextInt(3);
    }

    // temporarily create grid within constructor
    createDisplayGrid(gridWidth, gridHeight, cellColorSheet);
  }

  private void createDisplayGrid(int gridWidth, int gridHeight, int[] cellColorSheet){
    GridDisplay grid = new GridDisplay(root, scene, gridWidth, gridHeight);

    grid.updateGrid(cellColorSheet);

    // update grid every time window WIDTH is resized
    scene.widthProperty().addListener((currentWidth, oldWidth, newWidth) -> {
      grid.setCurrentScreenWidth(newWidth.doubleValue());
      grid.updateGrid(cellColorSheet);
    });

    // update grid every time window HEIGHT is resized
    scene.heightProperty().addListener((currentHeight, oldHeight, newHeight) -> {
      grid.setCurrentScreenHeight(newHeight.doubleValue());
      grid.updateGrid(cellColorSheet);
    });
  }
}
