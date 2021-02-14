package cell_society.visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AnimationManager {

  public AnimationManager(){
    setupTimeline();
  }

  private void setupTimeline(){

    int FPS = 120;
    double SECOND_DELAY = 1.0 / FPS;

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
}
