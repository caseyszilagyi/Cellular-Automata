package cell_society.visualization;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AnimationManager {

  private int fps;
  private double secondDelay;

  public AnimationManager(){
    fps = 120;
    secondDelay = 1.0 / fps;
    setupTimeline();
  }

  private void setupTimeline(){

    KeyFrame keyframe = new KeyFrame(Duration.seconds(secondDelay), e -> {
      stepSimulation();
    });

    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
    animation.play();
  }

  private void playSimulation(Timeline animation){
    animation.play();
  }

  private void pauseSimulation(Timeline animation){
    animation.pause();
  }

  public void setFPS(int fps){
    this.fps = fps;
    secondDelay = 1.0 / fps;
  }

  private void stepSimulation(){

  }
}
