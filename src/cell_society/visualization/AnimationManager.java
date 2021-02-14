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

  public AnimationManager(){
    fps = 1;
    secondDelay = 1.0 / fps;
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
      //This get real grid method can be changed to get grid to get the char array for color mapping
      currentSim.getRealGrid().printCurrentState(); // instead of this, update display grid
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

  public void setFPS(int fps){
    this.fps = fps;
    secondDelay = 1.0 / fps;
  }
}
