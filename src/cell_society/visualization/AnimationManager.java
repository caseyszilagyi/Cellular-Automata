package cell_society.visualization;

import cell_society.backend.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AnimationManager {

  private int fps;
  private double secondDelay;
  private Simulation currentSim;
  private Timeline animation;

  private DisplayManager displayManager;

  public AnimationManager(DisplayManager displayManager){
    fps = 10;
    secondDelay = 1.0 / fps;
    this.displayManager = displayManager;
    setupTimeline();
  }

  public void setSimulation(Simulation currentSim){
    this.currentSim = currentSim;
    playSimulation();
  }

  private void setupTimeline(){

    KeyFrame keyframe = new KeyFrame(Duration.seconds(secondDelay), e -> {
      stepSimulation();
    });

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
  }

  public void stepSimulation(){
    if(currentSim != null){
      currentSim.makeStep();
      //This gets the chars that represent the cells
      displayManager.updateDisplayGrid(currentSim);
    }
  }

  public void playSimulation(){
    if(currentSim != null){
      animation.play();
    }
  }

  public void pauseSimulation(){
    if(currentSim != null){
      animation.pause();
    }
  }

  public double setNextFPS(){
    if(animation.getRate() == 2.0){
      animation.setRate(1.75);
    } else if(animation.getRate() == 1.75){
      animation.setRate(1.5);
    } else if (animation.getRate() == 1.5){
      animation.setRate(1.25);
    } else if(animation.getRate() == 1.25){
    animation.setRate(1.0);
    } else if (animation.getRate() == 1.0){
      animation.setRate(0.75);
    } else if (animation.getRate() == 0.75) {
      animation.setRate(0.5);
    } else if (animation.getRate() == 0.5) {
      animation.setRate(0.25);
    } else {
      animation.setRate(2.0);
    }
    return animation.getRate();
  }
}
