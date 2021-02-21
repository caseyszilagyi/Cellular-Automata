package cell_society.visualization;

import cell_society.Main;
import cell_society.backend.Simulation;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
  private final GraphDisplay graphDisplay;
  private final AnimationManager animationManager;
  private final Pane gridPane;
  private final Pane graphPane;

  private final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources";

  private final String COLOR_MODE_BUTTON_PROPERTY = "ColorModeButton";
  private final String LOAD_NEW_SIMULATION_BUTTON_PROPERTY = "LoadNewSimulationButton";
  private final String OPEN_NEW_WINDOW_BUTTON_PROPERTY = "OpenNewWindowButton";
  private final String PLAY_PAUSE_BUTTON_PROPERTY = "PlayPauseButton";
  private final String STEP_BUTTON_PROPERTY = "StepButton";
  private final String SPEED_BUTTON_PROPERTY = "SpeedButton";
  private final String[] COLOR_MODES_LIST = {"Dark Mode", "Light Mode", "Colorful Mode"};

  private Simulation currentSim;
  private String currentSimulationType;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Main main, Stage stage, Pane root, Scene scene) {
    this.main = main;
    resourceBundle = ResourceBundle.getBundle(String.format("%s/properties/languages/English", VISUALIZATION_RESOURCE_PACKAGE));
    this.stage = stage;
    this.root = root;
    this.scene = scene;

    gridPane = new Pane();
    graphPane = new Pane();
    root.getChildren().add(gridPane);
    root.getChildren().add(graphPane);

    //graphPane.setVisible(false);

    gridDisplay = new GridDisplay(scene, gridPane);
    graphDisplay = new GraphDisplay(scene, graphPane);

    animationManager = new AnimationManager(this);

    makeAllButtons();

    changeStylesheet("LightMode.css");
  }

  private void changeStylesheet(String fileName){
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(String.format("/%s/stylesheets/%s", VISUALIZATION_RESOURCE_PACKAGE, fileName)).toExternalForm());
  }

  private void loadNewSimulation(String simulationType, String fileName){
    if (!simulationType.equals("") && !fileName.equals("")){
      try {
        currentSim = new Simulation(simulationType, fileName);
        currentSimulationType = simulationType;
        currentSim.initializeSimulation();
        animationManager.setSimulation(currentSim);

        gridDisplay.setCurrentSimInfo(currentSim, currentSimulationType);
        graphDisplay.setCurrentSimInfo(currentSim, currentSimulationType);
        updateDisplayGraph();
        updateDisplayGrid();

        animationManager.pauseSimulation();
      } catch (Exception error){
        // exception handling

        Alert newAlert = new Alert(AlertType.ERROR);
        newAlert.setTitle(resourceBundle.getString("ErrorTitle"));
        newAlert.setHeaderText(null);
        newAlert.setContentText(resourceBundle.getString(error.getMessage()));

        newAlert.showAndWait();
      }
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
    ComboBox<String> colorModeButton = makeDropdownButton(COLOR_MODE_BUTTON_PROPERTY, 10+10+80+10+80+10+120, 40, 120, COLOR_MODES_LIST);
    Button loadSimButton = makeButton(LOAD_NEW_SIMULATION_BUTTON_PROPERTY, 10, 10, 120);
    Button addNewSimButton = makeButton(OPEN_NEW_WINDOW_BUTTON_PROPERTY, 10+10+120, 10, 160);

    Button playPauseButton = makeButton(PLAY_PAUSE_BUTTON_PROPERTY, 10, 40, 80);
    Button stepButton = makeButton(STEP_BUTTON_PROPERTY, 10+10+80, 40, 80);
    Button speedButton = makeButton(SPEED_BUTTON_PROPERTY, 10+10+80+10+80, 40, 120);



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
      String newText = String.format("%s: x%.2f", resourceBundle.getString("SpeedButton"), animationManager.setNextFPS());
      speedButton.setText(newText);
    });

    colorModeButton.setOnAction(e -> {
      String selected = colorModeButton.getSelectionModel().getSelectedItem();
      String fileName = String.format("%s.css", selected.replace(" ", ""));
      changeStylesheet(fileName);
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

  private ComboBox<String> makeDropdownButton(String property, double x, double y, double buttonWidth, String[] choices){
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setPromptText(resourceBundle.getString(property));
    comboBox.setLayoutX(x);
    comboBox.setLayoutY(y);
    for (String choice : choices) {
      comboBox.getItems().add(choice);
    }
    root.getChildren().add(comboBox);
    return comboBox;
  }

  /**
   *
   */
  public void updateDisplayGrid(){
    gridDisplay.updateGrid(currentSim.getGridDisplay()); //convert backend grid into frontend grid
  }

  /**
   *
   */
  public void updateDisplayGraph(){
    graphDisplay.updateGraph();
  }
}
