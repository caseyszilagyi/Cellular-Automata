package cell_society;

import cell_society.visualization.DisplayManager;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) {

    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 800;

    String STAGE_TITLE = "Cell Society Simulation";
    Pane root = new Pane();

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setTitle(STAGE_TITLE);
    stage.setScene(scene);
    stage.show();

    new DisplayManager(stage, root, scene);
  }
}
