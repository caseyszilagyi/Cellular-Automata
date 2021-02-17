package cell_society.visualization;

import cell_society.backend.Simulation;
import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The DisplayManager class is responsible for maintaining and updating the display grid
 * and various buttons for controlling visualization.
 *
 * @author Donghan Park
 */
public class DisplayManager {

  private final Stage stage;
  private final Group root;
  private final Scene scene;

  private final ResourceBundle resourceBundle;

  private final GridDisplay gridDisplay;
  private final AnimationManager animationManager;
  private final Pane pane;

  public static final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources/";
  public static final String VISUALIZATION_RESOURCE_FOLDER = "/" + VISUALIZATION_RESOURCE_PACKAGE;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Stage stage, Group root, Scene scene) {
    resourceBundle = ResourceBundle.getBundle(VISUALIZATION_RESOURCE_PACKAGE + "properties/Spanish");
    this.stage = stage;
    this.root = root;
    this.scene = scene;
    pane = new Pane();
    root.getChildren().add(pane);

    gridDisplay = new GridDisplay(pane, scene);

    animationManager = new AnimationManager(this);

    makeAllButtons();

    changeStylesheet("LightMode.css");
    changeStylesheet("DarkMode.css");
  }

  private void changeStylesheet(String fileName){
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(VISUALIZATION_RESOURCE_FOLDER + "stylesheets/" + fileName).toExternalForm());
  }

  private void loadNewSimulation(String simulationType, String fileName){

    Simulation currentSim = new Simulation(simulationType, fileName);
    currentSim.initializeSimulation();
    animationManager.setSimulation(currentSim);

    updateDisplayGrid(currentSim);
  }

  private void makeAllButtons(){
    Button loadSimButton = makeButton("LoadSimulationButton", 10, 10);
    Button startButton = makeButton("StartButton", 10, 40);
    Button pauseButton = makeButton("PauseButton", 10, 70);
    Button stepButton = makeButton("StepButton", 10, 100);
    Button speedButton = makeButton("SpeedButton", 10, 130);

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


    startButton.setOnMouseClicked(e -> {
      animationManager.playSimulation();
    });


    pauseButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
    });


    stepButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      animationManager.stepSimulation();
    });


    speedButton.setOnMouseClicked(e -> {
      speedButton.setText(resourceBundle.getString("SpeedButton") + ": x" + animationManager.setNextFPS());
    });
  }

  private Button makeButton(String property, double x, double y){
    Button button = new Button(resourceBundle.getString(property));
    button.setLayoutX(x);
    button.setLayoutY(y);
    button.setPrefWidth(120);
    root.getChildren().add(button);
    return button;
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

    addResizeWindowEventListeners(cellColorSheet);
  }

  private void addResizeWindowEventListeners(String[] cellColorSheet){
    // update grid every time window WIDTH is resized
    scene.widthProperty().addListener((currentWidth, oldWidth, newWidth) -> {
      animationManager.pauseSimulation();
      gridDisplay.setCurrentScreenWidth(newWidth.doubleValue());
      gridDisplay.updateGrid(cellColorSheet);
    });

    // update grid every time window HEIGHT is resized
    scene.heightProperty().addListener((currentHeight, oldHeight, newHeight) -> {
      animationManager.pauseSimulation();
      gridDisplay.setCurrentScreenHeight(newHeight.doubleValue());
      gridDisplay.updateGrid(cellColorSheet);
    });
  }
}
