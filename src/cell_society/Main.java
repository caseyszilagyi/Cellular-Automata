package cell_society;

import cell_society.visualization.DisplayManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) {

    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 800;

    String STAGE_TITLE = "Cell Society Simulation";
    Group root = new Group();

    Scene mainScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setTitle(STAGE_TITLE);
    stage.setScene(mainScene);
    stage.show();

    DisplayManager displayManager = new DisplayManager(stage, root, mainScene);
  }
}
