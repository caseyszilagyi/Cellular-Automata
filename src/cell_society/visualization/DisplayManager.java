package cell_society.visualization;

import cell_society.Main;
import cell_society.backend.Simulation;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

  private final GridDisplay gridDisplay;
  private final GraphDisplay graphDisplay;
  private final AnimationManager animationManager;
  private final Pane gridPane;
  private final Pane graphPane;
  private final Pane buttonPane;
  private final Pane parameterPane;

  private final String VISUALIZATION_RESOURCE_PACKAGE = "cell_society/visualization/resources";

  private final String LIGHT_MODE_LABEL = "LightModeLabel";
  private final String DARK_MODE_LABEL = "DarkModeLabel";
  private final String COLORFUL_MODE_LABEL = "ColorfulModeLabel";
  private final String ENGLISH_LABEL = "EnglishLabel";
  private final String SPANISH_LABEL = "SpanishLabel";
  private final String FRENCH_LABEL = "FrenchLabel";

  private final String COLOR_BUTTON_PROPERTY = "ColorModeButton";
  private final String LOAD_SIM_BUTTON_PROPERTY = "LoadNewSimulationButton";
  private final String OPEN_NEW_BUTTON_PROPERTY = "OpenNewWindowButton";
  private final String PLAY_BUTTON_PROPERTY = "PlayPauseButton";
  private final String STEP_BUTTON_PROPERTY = "StepButton";
  private final String SPEED_BUTTON_PROPERTY = "SpeedButton";
  private final String GRID_BUTTON_PROPERTY = "GridButton";
  private final String GRAPH_BUTTON_PROPERTY = "GraphButton";
  private final String LANG_BUTTON_PROPERTY = "LanguageButton";
  private final String ERROR_TITLE_PROPERTY = "ErrorTitle";

  private final int LOAD_SIM_BUTTON_WIDTH = 160;
  private final int OPEN_NEW_BUTTON_WIDTH = 160;
  private final int LANG_BUTTON_WIDTH = 160;
  private final int COLOR_BUTTON_WIDTH = 160;
  private final int PLAY_BUTTON_WIDTH = 120;
  private final int STEP_BUTTON_WIDTH = 120;
  private final int SPEED_BUTTON_WIDTH = 120;
  private final int GRID_BUTTON_WIDTH = 120;
  private final int GRAPH_BUTTON_WIDTH = 120;
  private final int BUTTON_HEIGHT = 20;

  private final int SLIDER_WIDTH = 120;

  private final int PADDING_X = 10;
  private final int PADDING_Y = 15;

  private final int ROW1_Y = PADDING_Y;
  private final int ROW2_Y = PADDING_Y + ROW1_Y + BUTTON_HEIGHT;
  private final int ROW3_Y = PADDING_Y + ROW2_Y + BUTTON_HEIGHT;

  private final int LOAD_SIM_BUTTON_X = PADDING_X;
  private final int OPEN_NEW_BUTTON_X = PADDING_X + LOAD_SIM_BUTTON_X + LOAD_SIM_BUTTON_WIDTH;
  private final int LANG_BUTTON_X = PADDING_X + OPEN_NEW_BUTTON_X + OPEN_NEW_BUTTON_WIDTH;
  private final int COLOR_BUTTON_X = PADDING_X + LANG_BUTTON_X + LANG_BUTTON_WIDTH;
  private final int PLAY_BUTTON_X = PADDING_X * 2;
  private final int STEP_BUTTON_X = PADDING_X + PLAY_BUTTON_X + PLAY_BUTTON_WIDTH;
  private final int SPEED_BUTTON_X = PADDING_X + STEP_BUTTON_X + STEP_BUTTON_WIDTH;
  private final int GRID_BUTTON_X = PADDING_X + SPEED_BUTTON_X + SPEED_BUTTON_WIDTH;
  private final int GRAPH_BUTTON_X = PADDING_X + GRID_BUTTON_X + GRID_BUTTON_WIDTH;

  private String[] COLOR_MODES_LIST;
  private String[] LANGUAGES_LIST;

  private Simulation currentSim;
  private String currentSimulationType;
  private ResourceBundle resourceBundle;

  private Map<String, String> returnMap;

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

    gridPane = new Pane();
    graphPane = new Pane();
    buttonPane = new Pane();
    parameterPane = new Pane();

    parameterPane.setLayoutX(PADDING_X * 3);
    parameterPane.setLayoutY(ROW3_Y);

    root.getChildren().add(gridPane);
    root.getChildren().add(graphPane);
    root.getChildren().add(buttonPane);
    root.getChildren().add(parameterPane);

    gridDisplay = new GridDisplay(scene, gridPane);
    graphDisplay = new GraphDisplay(scene, graphPane);
    animationManager = new AnimationManager(this);
    returnMap = new HashMap<>();

    // default to English
    resourceBundle = ResourceBundle.getBundle(String.format("%s/properties/languages/English", VISUALIZATION_RESOURCE_PACKAGE));
    updateDropdownLabelsAfterLanguageChange();

    //default to light mode
    changeStylesheet("LightMode.css");

    makeAllButtons();
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

  private void makeSimParameterSliders(Map<String, double[]> parameterMap){
    parameterPane.getChildren().clear();
    returnMap.clear();

    int index = 0;

    for(Map.Entry<String, double[]> entry : parameterMap.entrySet()){
      String parameterName = entry.getKey();
      double min = entry.getValue()[0];
      double max = entry.getValue()[1];
      double current = entry.getValue()[2];
      returnMap.put(parameterName, Double.toString(current));

      makeSlider(parameterName, min, max, current, SLIDER_WIDTH * index + PADDING_X * (index - 1), 0);

      index++;
    }
  }

  private void makeSlider(String parameterName, double min, double max, double current, double xPos, double yPos){
    Slider slider = new Slider(min, max, current);

    slider.setPrefWidth(SLIDER_WIDTH);
    slider.setLayoutX(xPos);
    slider.setLayoutY(yPos);

    Label label = new Label(String.format("%s%s: %.2f", parameterName.substring(0, 1).toUpperCase(), parameterName.substring(1), current));
    label.setLayoutX(xPos + PADDING_X / 2.0);
    label.setLayoutY(yPos + PADDING_Y * 1.2);

    parameterPane.getChildren().add(slider);
    parameterPane.getChildren().add(label);

    // display current value when dragging slider (only frontend)
    slider.setOnMouseDragged(e -> {
      label.setText(String.format("%s: %.2f", parameterName, slider.getValue()));
    });

    // send info to backend only once when mouse is released (prevents lag)
    slider.setOnMouseReleased(e -> {
      String newValue = Double.toString(Math.floor(slider.getValue() * 100) / 100);
      returnMap.replace(parameterName, newValue);
      currentSim.setParameters(returnMap);
    });
  }

  private void createErrorDialog(Exception error){
    Alert newAlert = new Alert(AlertType.ERROR);
    newAlert.setTitle(resourceBundle.getString(ERROR_TITLE_PROPERTY));
    newAlert.setHeaderText(null);
    newAlert.setContentText(resourceBundle.getString(error.getMessage()));
    newAlert.showAndWait();
  }

  private void makeAllButtons() {
    buttonPane.getChildren().clear();

    Button loadSimButton = makeButton(LOAD_SIM_BUTTON_PROPERTY, LOAD_SIM_BUTTON_X, ROW1_Y, LOAD_SIM_BUTTON_WIDTH, BUTTON_HEIGHT);
    Button openNewSimButton = makeButton(OPEN_NEW_BUTTON_PROPERTY, OPEN_NEW_BUTTON_X, ROW1_Y, OPEN_NEW_BUTTON_WIDTH, BUTTON_HEIGHT);
    ChoiceBox<String> languageButton = makeDropdownButton(LANG_BUTTON_PROPERTY, LANG_BUTTON_X, ROW1_Y, LANG_BUTTON_WIDTH, BUTTON_HEIGHT, LANGUAGES_LIST);
    ChoiceBox<String> colorModeButton = makeDropdownButton(COLOR_BUTTON_PROPERTY, COLOR_BUTTON_X, ROW1_Y, COLOR_BUTTON_WIDTH, BUTTON_HEIGHT, COLOR_MODES_LIST);

    Button playPauseButton = makeButton(PLAY_BUTTON_PROPERTY, PLAY_BUTTON_X, ROW2_Y, PLAY_BUTTON_WIDTH, BUTTON_HEIGHT);
    Button stepButton = makeButton(STEP_BUTTON_PROPERTY, STEP_BUTTON_X, ROW2_Y, STEP_BUTTON_WIDTH, BUTTON_HEIGHT);
    Button speedButton = makeButton(SPEED_BUTTON_PROPERTY, SPEED_BUTTON_X, ROW2_Y, SPEED_BUTTON_WIDTH, BUTTON_HEIGHT);
    Button gridButton = makeButton(GRID_BUTTON_PROPERTY, GRID_BUTTON_X, ROW2_Y, GRID_BUTTON_WIDTH, BUTTON_HEIGHT);
    Button graphButton = makeButton(GRAPH_BUTTON_PROPERTY, GRAPH_BUTTON_X, ROW2_Y, GRAPH_BUTTON_WIDTH, BUTTON_HEIGHT);

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

  private Button makeButton(String property, double x, double y, double buttonWidth, double buttonHeight){
    Button button = new Button(resourceBundle.getString(property));
    button.setLayoutX(x);
    button.setLayoutY(y);
    button.setPrefWidth(buttonWidth);
    button.setPrefHeight(buttonHeight);
    buttonPane.getChildren().add(button);
    return button;
  }

  private ChoiceBox<String> makeDropdownButton(String property, double x, double y, double buttonWidth, double buttonHeight, String[] choices){
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.setValue(resourceBundle.getString(property));
    choiceBox.setLayoutX(x);
    choiceBox.setLayoutY(y);
    choiceBox.setPrefWidth(buttonWidth);
    choiceBox.setPrefHeight(buttonHeight);
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

    // minimize views to half the screen size if both views are turned on
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
      updateDropdownLabelsAfterLanguageChange();
      makeAllButtons();
    });
  }

  private void updateDropdownLabelsAfterLanguageChange(){
    COLOR_MODES_LIST = new String[] {resourceBundle.getString(LIGHT_MODE_LABEL), resourceBundle.getString(DARK_MODE_LABEL), resourceBundle.getString(COLORFUL_MODE_LABEL)};
    LANGUAGES_LIST = new String[] {resourceBundle.getString(ENGLISH_LABEL), resourceBundle.getString(SPANISH_LABEL), resourceBundle.getString(FRENCH_LABEL)};
  }

  private void changeStylesheet(String fileName){
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(String.format("/%s/stylesheets/%s", VISUALIZATION_RESOURCE_PACKAGE, fileName)).toExternalForm());
  }

  /**
   * updates the grid view for frontend
   */
  public void updateDisplayGrid(){
    gridDisplay.updateGrid(currentSim.getGridDisplay());
  }

  /**
   * updates the graph view for frontend
   */
  public void updateDisplayGraph(){
    graphDisplay.updateGraph(currentSim.getCellDistribution());
  }

}