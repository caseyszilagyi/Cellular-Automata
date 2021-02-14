package cell_society;

import cell_society.visualization.DisplayManager;
import cell_society.backend.Simulation;
import cell_society.backend.automata.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

  private Group root;

  @Override
  public void start(Stage stage) {

    int FPS = 120;
    double SECOND_DELAY = 1.0 / FPS;
    String STAGE_TITLE = "Cell Society Simulation";
    root = new Group();

    Scene mainScene = setupScene(stage);
    stage.setTitle(STAGE_TITLE);
    stage.setScene(mainScene);
    stage.show();

    KeyFrame keyframe = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
        //simulationGrid = simulation.makeStep();
        //Some step where this grid is passed to the front end for display, these should
        //be the only 2 steps necessary.
    });

    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
    animation.play();
  }

  private Scene setupScene(Stage stage) {

    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 800;

    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

    DisplayManager displayManager = new DisplayManager(stage, root, scene);

    // scene.setOnKeyPressed(e -> inputManager.handleKeyInput(e.getCode()));
    // scene.setOnMouseMoved(e -> inputManager.handleMouseMoved(e.getX(), e.getY()));
    // scene.setOnMouseClicked(e -> inputManager.handleMouseClick(e.getButton()));

    return scene;
  }
}
