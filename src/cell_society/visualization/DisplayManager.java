package cell_society.visualization;

import cell_society.backend.Simulation;
import cell_society.backend.automata.Grid; // <-- dependence on Grid class will be removed once back-end methods are implemented

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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

  private GridDisplay gridDisplay;
  private AnimationManager animationManager;

  private final Pane pane;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Stage stage, Group root, Scene scene) {
    this.root = root;
    this.scene = scene;
    pane = new Pane();
    root.getChildren().add(pane);

    gridDisplay = new GridDisplay(pane, scene);
    addResizeWindowEventListeners();

    animationManager = new AnimationManager(this);

    // temporary test for load configuration file button
    Button loadSimButton = new Button("LOAD NEW FILE");
    root.getChildren().add(loadSimButton);

    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/config_files"));
    loadSimButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();

      File selectedDirectory = fileChooser.showOpenDialog(stage);

      if (selectedDirectory != null){
        String fileName = selectedDirectory.getName();
        String simulationType = selectedDirectory.getParentFile().getName();
        loadNewSimulation(simulationType, fileName);
      }
    });

    Button playSimButton = new Button("PLAY"); // all these buttons are temporary.. should be placed in separate class
    playSimButton.setLayoutX(150);
    root.getChildren().add(playSimButton);

    playSimButton.setOnMouseClicked(e -> {
      animationManager.playSimulation();
    });

    Button pauseSimButton = new Button("PAUSE");
    pauseSimButton.setLayoutX(200);
    root.getChildren().add(pauseSimButton);

    pauseSimButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
    });

    Button stepSimButton = new Button("STEP");
    stepSimButton.setLayoutX(250);
    root.getChildren().add(stepSimButton);

    stepSimButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      animationManager.stepSimulation();
    });
  }

  private void loadNewSimulation(String simulationType, String fileName){

    Simulation currentSim = new Simulation(simulationType, fileName);
    currentSim.initializeSimulation();
    animationManager.setSimulation(currentSim);

    updateDisplayGrid(currentSim);
  }

  private String[] convertCharSheetToColors(char[] charSheet, Map<Character, String> charToColorMap){
    String[] colorSheet = new String[charSheet.length];

    for(int i = 0; i < colorSheet.length; i++){
      colorSheet[i] = charToColorMap.get(charSheet[i]);
    }

    return colorSheet;
  }

  public void updateDisplayGrid(Simulation currentSim){
    gridDisplay.setGridDimensions(currentSim.getGridWidth(), currentSim.getGridHeight());
    String[] cellColorSheet = convertCharSheetToColors(currentSim.getGrid(), currentSim.getColorMapping());
    gridDisplay.updateGrid(cellColorSheet);
  }

  private void addResizeWindowEventListeners(){
    // update grid every time window WIDTH is resized
    scene.widthProperty().addListener((currentWidth, oldWidth, newWidth) -> {
      gridDisplay.setCurrentScreenWidth(newWidth.doubleValue());
      //gridDisplay.updateGrid(cellColorSheet);
    });

    // update grid every time window HEIGHT is resized
    scene.heightProperty().addListener((currentHeight, oldHeight, newHeight) -> {
      gridDisplay.setCurrentScreenHeight(newHeight.doubleValue());
      //gridDisplay.updateGrid(cellColorSheet);
    });
  }
}
