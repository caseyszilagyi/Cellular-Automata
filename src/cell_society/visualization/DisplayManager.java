package cell_society.visualization;

import cell_society.Main;
import cell_society.backend.Simulation;
import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;
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

  private final Main main;

  private final Stage stage;
  private final Pane root;
  private final Scene scene;

  private final ResourceBundle resourceBundle;

  private final GridDisplay gridDisplay;
  private final AnimationManager animationManager;
  private final Pane pane;

  private final String[] colorModes = {"LightMode.css", "Colorful.css", "DarkMode.css"};
  private int currentColorMode = 0;

  private final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources/";
  private final String VISUALIZATION_RESOURCE_FOLDER = "/" + VISUALIZATION_RESOURCE_PACKAGE;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Main main, Stage stage, Pane root, Scene scene) {
    this.main = main;
    resourceBundle = ResourceBundle.getBundle(VISUALIZATION_RESOURCE_PACKAGE + "properties/English");
    this.stage = stage;
    this.root = root;
    this.scene = scene;

    pane = new Pane();
    root.getChildren().add(pane);

    gridDisplay = new GridDisplay(scene, pane);

    animationManager = new AnimationManager(this);

    makeAllButtons();

    changeStylesheet(colorModes[currentColorMode]);
  }

  public void printWindowSize(){
    System.out.println("WINDOW WIDTH: " + stage.getWidth());
  }

  private void changeStylesheet(String fileName){
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(VISUALIZATION_RESOURCE_FOLDER + "stylesheets/" + fileName).toExternalForm());
  }

  private void toggleNextStylesheet(){
    currentColorMode++;
    if(currentColorMode > colorModes.length - 1){
      currentColorMode = 0;
    }
    changeStylesheet(colorModes[currentColorMode]);
  }

  private void loadNewSimulation(String simulationType, String fileName){
    if (!simulationType.equals("") && !fileName.equals("")){
      Simulation currentSim = new Simulation(simulationType, fileName);
      currentSim.initializeSimulation();
      animationManager.setSimulation(currentSim);

      updateDisplayGrid(currentSim);
      //addResizeWindowEventListeners(currentSim);
    }
  }

  private String[] loadNewFile(){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/config_files"));
    File selectedDirectory = fileChooser.showOpenDialog(stage);

    if(selectedDirectory != null){
      String fileName = selectedDirectory.getName();
      String simulationType = selectedDirectory.getParentFile().getName();
      return new String[] {simulationType, fileName};
    }

    return new String[] {"", ""};
  }

  private void openNewWindow(){
    main.createNewWindow("Test stage");
  }

  private void makeAllButtons(){
    Button loadSimButton = makeButton("LoadSimulationButton", 10, 10, 120);
    Button addNewSimButton = makeButton("NewSimulationButton", 10+10+120, 10, 160);

    Button playPauseButton = makeButton("PlayPauseButton", 10, 40, 80);
    Button stepButton = makeButton("StepButton", 10+10+80, 40, 80);
    Button speedButton = makeButton("SpeedButton", 10+10+80+10+80, 40, 120);
    Button colorModeButton = makeButton("ColorModeButton", 10+10+80+10+80+10+120, 40, 120);

    loadSimButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      String[] simulationInfo = loadNewFile();
      loadNewSimulation(simulationInfo[0], simulationInfo[1]);
    });

    addNewSimButton.setOnMouseClicked(e -> {
      openNewWindow();
    });

    playPauseButton.setOnMouseClicked(e -> {
      animationManager.playPauseSimulation();
    });

    stepButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      animationManager.stepSimulation();
    });

    speedButton.setOnMouseClicked(e -> {
      speedButton.setText(resourceBundle.getString("SpeedButton") + animationManager.setNextFPS()); // <-- must use String.format() for info rather than '+'
    });

    colorModeButton.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      toggleNextStylesheet();
    });
  }

  private Button makeButton(String property, double x, double y, double buttonWidth){
    Button button = new Button(resourceBundle.getString(property));
    button.setLayoutX(x);
    button.setLayoutY(y);
    button.setPrefWidth(buttonWidth);
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
    gridDisplay.updateGrid(getCellColorSheet(currentSim), "rectangle");
  }

  private String[] getCellColorSheet(Simulation currentSim){
    return convertCharSheetToColors(currentSim.getGrid(), currentSim.getColorMapping());
  }
}
