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

  private final int SCREEN_WIDTH = 800;
  private final int SCREEN_HEIGHT = 800;

  @Override
  public void start(Stage stage) {
    createNewWindow("Cell Society Simulation");
  }

  public void createNewWindow(String stageTitle){
    Stage stage = new Stage();
    stage.setTitle(stageTitle);
    Pane root = new Pane();

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();

    new DisplayManager(this, stage, root, scene);
  }
}
