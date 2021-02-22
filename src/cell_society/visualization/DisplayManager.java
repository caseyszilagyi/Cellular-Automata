package cell_society.visualization;

import cell_society.Main;
import cell_society.backend.Simulation;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
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

  private ResourceBundle resourceBundle;

  private final GridDisplay gridDisplay;
  private final GraphDisplay graphDisplay;
  private final AnimationManager animationManager;
  private final Pane gridPane;
  private final Pane graphPane;
  private final Pane buttonPane;
  private final Pane parameterPane;

  private final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources";

  private final String COLOR_MODE_BUTTON_PROPERTY = "ColorModeButton";
  private final String LOAD_NEW_SIMULATION_BUTTON_PROPERTY = "LoadNewSimulationButton";
  private final String OPEN_NEW_WINDOW_BUTTON_PROPERTY = "OpenNewWindowButton";
  private final String PLAY_PAUSE_BUTTON_PROPERTY = "PlayPauseButton";
  private final String STEP_BUTTON_PROPERTY = "StepButton";
  private final String SPEED_BUTTON_PROPERTY = "SpeedButton";
  private final String GRID_BUTTON_PROPERTY = "GridButton";
  private final String GRAPH_BUTTON_PROPERTY = "GraphButton";
  private final String LANGUAGE_BUTTON_PROPERTY = "LanguageButton";

  private String[] COLOR_MODES_LIST;
  private String[] LANGUAGES_LIST;

  private final String ERROR_TITLE = "ErrorTitle";

  private Simulation currentSim;
  private String currentSimulationType;

  /**
   * Constructor that creates an instance of the DisplayManager
   * @param root The root node of the main scene graph
   * @param scene The container for the main scene graph
   */
  public DisplayManager(Main main, Stage stage, Pane root, Scene scene) {
    this.main = main;
    this.stage = stage;
    this.root = root;
    this.scene = scene;

    resourceBundle = ResourceBundle.getBundle(String.format("%s/properties/languages/English", VISUALIZATION_RESOURCE_PACKAGE));
    updateListLanguages();

    gridPane = new Pane();
    graphPane = new Pane();
    buttonPane = new Pane();
    parameterPane = new Pane();
    root.getChildren().add(gridPane);
    root.getChildren().add(graphPane);
    root.getChildren().add(buttonPane);
    root.getChildren().add(parameterPane);

    gridDisplay = new GridDisplay(scene, gridPane);
    graphDisplay = new GraphDisplay(scene, graphPane);

    animationManager = new AnimationManager(this);

    makeAllButtons();

    changeStylesheet("LightMode.css");
  }

  private void updateListLanguages(){
    COLOR_MODES_LIST = new String[] {resourceBundle.getString("LightModeLabel"), resourceBundle.getString("DarkModeLabel"), resourceBundle.getString("ColorfulModeLabel")};
    LANGUAGES_LIST = new String[] {resourceBundle.getString("English"), resourceBundle.getString("Spanish"), resourceBundle.getString("French")};
  }

  private void changeStylesheet(String fileName){
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(String.format("/%s/stylesheets/%s", VISUALIZATION_RESOURCE_PACKAGE, fileName)).toExternalForm());
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

  private void loadNewSimulation(String simulationType, String fileName){
    if (!simulationType.equals("") && !fileName.equals("")){
      try {
        currentSim = new Simulation(simulationType, fileName);
        currentSimulationType = simulationType;

        currentSim.initializeSimulation(); //initialize backend sim

        animationManager.setSimulation(currentSim);
        gridDisplay.setCurrentSimType(currentSimulationType);
        graphDisplay.setCurrentSimType(currentSimulationType);

        updateDisplayGraph();
        updateDisplayGrid();

        makeSimParameterSliders(currentSim.getParameters());
      }
      catch (Exception error){
        createErrorDialog(error);
      }
    }
  }

  Map<String, String> returnMap = new HashMap<String, String>();

  private void makeSimParameterSliders(Map<String, double[]> parameterMap){
    parameterPane.getChildren().clear();
    returnMap.clear();

    double initialXPos = 10;
    double initialYPos = 10;

    int index = 0;
    for(Map.Entry<String, double[]> entry : parameterMap.entrySet()){
      String parameterName = entry.getKey();
      double min = entry.getValue()[0];
      double max = entry.getValue()[1];
      double current = entry.getValue()[2];
      returnMap.put(parameterName, Double.toString(current));

      makeSlider(parameterName, min, max, current, initialXPos, initialYPos + 30 * index);

      index++;
    }
  }

  private void makeSlider(String parameterName, double min, double max, double current, double xPos, double yPos){
    Slider slider = new Slider(min, max, current);

    slider.setPrefSize(100, 20);

    slider.setTranslateX(xPos);
    slider.setTranslateY(yPos);

    parameterPane.getChildren().add(slider);

    slider.setOnMouseReleased(e -> {
      String newValue = Double.toString(Math.floor(slider.getValue() * 100) / 100);
      returnMap.replace(parameterName, newValue);
      currentSim.setParameters(returnMap);
    });
  }

  //for testing
  private void printReturnMap(){
    for(Map.Entry<String, String> entry : returnMap.entrySet()){
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  private void createErrorDialog(Exception error){
    Alert newAlert = new Alert(AlertType.ERROR);
    newAlert.setTitle(resourceBundle.getString(ERROR_TITLE));
    newAlert.setHeaderText(null);
    newAlert.setContentText(resourceBundle.getString(error.getMessage()));
    newAlert.showAndWait();
  }

  private void makeAllButtons() {
    buttonPane.getChildren().clear();

    Button loadSimButton = makeButton(LOAD_NEW_SIMULATION_BUTTON_PROPERTY, 10, 10, 120);
    Button openNewSimButton = makeButton(OPEN_NEW_WINDOW_BUTTON_PROPERTY, 10 + 10 + 120, 10, 160);

    Button playPauseButton = makeButton(PLAY_PAUSE_BUTTON_PROPERTY, 10, 40, 80);
    Button stepButton = makeButton(STEP_BUTTON_PROPERTY, 10 + 10 + 80, 40, 80);
    Button speedButton = makeButton(SPEED_BUTTON_PROPERTY, 10 + 10 + 80 + 10 + 80, 40, 120);

    Button gridButton = makeButton(GRID_BUTTON_PROPERTY, scene.getWidth() - 130, 10, 120);
    Button graphButton = makeButton(GRAPH_BUTTON_PROPERTY, scene.getWidth() - 130, 40, 120);

    ChoiceBox<String> colorModeButton = makeDropdownButton(COLOR_MODE_BUTTON_PROPERTY, 10 + 10 + 80 + 10 + 80 + 10 + 120, 40, 120, COLOR_MODES_LIST);
    ChoiceBox<String> languageButton = makeDropdownButton(LANGUAGE_BUTTON_PROPERTY, 310, 10, 120, LANGUAGES_LIST);

    applyLoadSimButtonLogic(loadSimButton);
    applyOpenNewSimButtonLogic(openNewSimButton);
    applyPlayPauseButtonLogic(playPauseButton);
    applyStepButtonLogic(stepButton);
    applySpeedButtonLogic(speedButton);
    applyColorModeButtonLogic(colorModeButton);
    applyGridButtonLogic(gridButton);
    applyGraphButtonLogic(graphButton);
    applyLanguageButtonLogic(languageButton);
  }

  private Button makeButton(String property, double x, double y, double buttonWidth){
    Button button = new Button(resourceBundle.getString(property));
    button.setLayoutX(x);
    button.setLayoutY(y);
    button.setPrefWidth(buttonWidth);
    buttonPane.getChildren().add(button);
    return button;
  }

  private ChoiceBox<String> makeDropdownButton(String property, double x, double y, double buttonWidth, String[] choices){
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.setValue(resourceBundle.getString(property));
    choiceBox.setLayoutX(x);
    choiceBox.setLayoutY(y);
    for (String choice : choices) {
      choiceBox.getItems().add(choice);
    }
    buttonPane.getChildren().add(choiceBox);
    return choiceBox;
  }

  private void applyGridButtonLogic(Button button){
    button.setOnMouseClicked(e ->
        togglePaneVisibility(gridPane)
    );
  }

  private void applyGraphButtonLogic(Button button){
    button.setOnMouseClicked(e ->
        togglePaneVisibility(graphPane)
    );
  }

  private void togglePaneVisibility(Pane pane){
    pane.setVisible(!pane.isVisible());

    boolean isMinimized = gridPane.isVisible() && graphPane.isVisible();
    gridDisplay.setIsMinimized(isMinimized);
    graphDisplay.setIsMinimized(isMinimized);
  }

  private void applyLoadSimButtonLogic(Button button){
    button.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      String[] simulationInfo = loadNewFile();
      loadNewSimulation(simulationInfo[0], simulationInfo[1]);
    });
  }

  private void applyOpenNewSimButtonLogic(Button button){
    button.setOnMouseClicked(e ->
        main.createNewWindow()
    );
  }

  private void applyPlayPauseButtonLogic(Button button){
    button.setOnMouseClicked(e ->
        animationManager.playPauseSimulation()
    );
  }

  private void applyStepButtonLogic(Button button){
    button.setOnMouseClicked(e -> {
      animationManager.pauseSimulation();
      animationManager.stepSimulation();
    });
  }

  private void applySpeedButtonLogic(Button button){
    button.setOnMouseClicked(e -> {
      String newText = String.format("%s: x%.2f", resourceBundle.getString(SPEED_BUTTON_PROPERTY), animationManager.setNextFPS());
      button.setText(newText);
    });
  }

  private void applyColorModeButtonLogic(ChoiceBox<String> button){
    button.setOnAction(e -> {
      String selectedMode = button.getSelectionModel().getSelectedItem();
      String fileName = String.format("%s.css", resourceBundle.getString(selectedMode.replace(" ", "")));
      changeStylesheet(fileName);
    });
  }

  private void applyLanguageButtonLogic(ChoiceBox<String> button){
    button.setOnAction(e -> {
      String selectedLanguage = button.getSelectionModel().getSelectedItem();
      resourceBundle = ResourceBundle.getBundle(String.format("%s/properties/languages/%s", VISUALIZATION_RESOURCE_PACKAGE, selectedLanguage));
      updateListLanguages();
      makeAllButtons();
    });
  }

  /**
   *
   */
  public void updateDisplayGrid(){
    gridDisplay.updateGrid(currentSim.getGridDisplay());
  }

  /**
   *
   */
  public void updateDisplayGraph(){
    graphDisplay.updateGraph(currentSim.getCellDistribution());
  }

}