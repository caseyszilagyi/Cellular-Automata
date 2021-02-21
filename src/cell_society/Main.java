package cell_society;

import cell_society.visualization.DisplayManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Donghan Park
 */
public class Main extends Application {

  private final int SCREEN_WIDTH = 1000;
  private final int SCREEN_HEIGHT = 800;
  private final String STAGE_TITLE = "Cell Society Simulation";

  @Override
  public void start(Stage stage) {
    createNewWindow(STAGE_TITLE);
  }

  public void createNewWindow(String stageTitle){
    Stage stage = new Stage();
    stage.setTitle(stageTitle);
    stage.setMinWidth(800);
    stage.setMinHeight(600);

    Pane root = new Pane();

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();

    new DisplayManager(this, stage, root, scene);
  }
}
