package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        int FPS = 120;
        double SECOND_DELAY = 1.0 / FPS;
        String STAGE_TITLE = "Cell Society Simulation";

        Scene mainScene = setupScene();
        stage.setTitle(STAGE_TITLE);
        stage.setScene(mainScene);
        stage.show();

        KeyFrame keyframe = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            // step function
        });

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(keyframe);
        animation.play();
    }

    private Scene setupScene() {

        Group root = new Group();
        int SCREEN_WIDTH = 800;
        int SCREEN_HEIGHT = 800;

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        // scene.setOnKeyPressed(e -> inputManager.handleKeyInput(e.getCode()));
        // scene.setOnMouseMoved(e -> inputManager.handleMouseMoved(e.getX(), e.getY()));
        // scene.setOnMouseClicked(e -> inputManager.handleMouseClick(e.getButton()));

        return scene;
    }
}
