package cell_society.visualization;

import cell_society.backend.Simulation;
import cell_society.backend.automata.Grid;


import java.io.File;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
Width and height: two different methods that return ints
getGridWidth(), getGridHeight()

updating the grid:
getGrid(): returns array of characters
getColorMapping(): returns map of characters that points to string of hex "00FF00"

initialize the simulation: see simulation class example
*/

/**
 * The DisplayManager class is responsible for maintaining and updating the display grid
 * and various buttons for controlling visualization.
 *
 * @author Donghan Park
 */
public class DisplayManager {

  private final Group root;
  private final Scene scene;
  private final Stage stage;

  private int gridWidth, gridHeight;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Stage stage, Group root, Scene scene) {
    this.stage = stage;
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

    // EXAMPLES FOR INPUTS FROM BACK-END:
    char[] charSheet = {
        'd', 'd', 'd', 'd',
        'a', 'd', 'a', 'd',
        'd', 'd', 'a', 'd',
        'd', 'a', 'd', 'd'
    };

    HashMap<Character, String> charToColorMap = new HashMap<Character, String>();
    charToColorMap.put('a', "FF0000");
    charToColorMap.put('d', "00FF00");

    // temporary test for load configuration file button
    Button button = new Button("BUTTON");
    root.getChildren().add(button);

    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/config_files"));
    button.setOnMouseClicked(e -> {
      File selectedDirectory = fileChooser.showOpenDialog(stage);
      String fileName = selectedDirectory.getPath();
      String simulationType = selectedDirectory.getParentFile().getName();

      Simulation mySim = new Simulation("Game of Life", fileName);
      mySim.initializeSimulation();
      Grid currGrid = mySim.getGrid();

      // temporarily create grid within constructor
      updateDisplayGrid(currGrid.getGridWidth(), currGrid.getGridHeight(), convertCharSheetToColors(charSheet, charToColorMap));

      currGrid.printCurrentState();
      mySim.makeStep();
      mySim.getGrid().printCurrentState();
    });
  }

  private String[] convertCharSheetToColors(char[] charSheet, HashMap<Character, String> charToColorMap){
    String[] colorSheet = new String[charSheet.length];
    for(int i = 0; i < colorSheet.length; i++){
      colorSheet[i] = charToColorMap.get(charSheet[i]);
    }

    return colorSheet;
  }

  private void updateDisplayGrid(int gridWidth, int gridHeight, String[] cellColorSheet){
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
