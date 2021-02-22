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
  private final int MIN_SCREEN_WIDTH = 800;
  private final int MIN_SCREEN_HEIGHT = 600;
  private final String STAGE_TITLE = "Cell Society Simulation";

  @Override
  public void start(Stage stage) {
    createNewWindow();
  }

  public void createNewWindow(){
    Stage stage = new Stage();
    stage.setTitle(STAGE_TITLE);
    stage.setMinWidth(MIN_SCREEN_WIDTH);
    stage.setMinHeight(MIN_SCREEN_HEIGHT);

    Pane root = new Pane();

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();

    new DisplayManager(this, stage, root, scene);
  }
}
