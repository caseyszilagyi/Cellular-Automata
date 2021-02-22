package cell_society.visualization;

import cell_society.backend.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The AnimationManager class handles main simulation
 * loop by calling the backend update method.
 *
 * @author Donghan Park
 */
public class AnimationManager {

  private final DisplayManager displayManager;

  private Simulation currentSim;
  private Timeline animation;

  private boolean animationRunning;

  /**
   *
   * @param displayManager
   */
  public AnimationManager(DisplayManager displayManager){
    this.displayManager = displayManager;
    setupTimeline();
    animationRunning = false;
  }

  private void setupTimeline(){

    int FPS = 10;
    double secondDelay = 1.0 / FPS;

    KeyFrame keyframe = new KeyFrame(Duration.seconds(secondDelay), e -> {
      stepSimulation();
    });

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
  }

  public void setSimulation(Simulation currentSim){
    this.currentSim = currentSim;
    playSimulation();
  }

  public void stepSimulation(){
    if(currentSim != null){
      currentSim.makeStep();
      displayManager.updateDisplayGrid();
      displayManager.updateDisplayGraph();
    }
  }

  public void playPauseSimulation(){
    if(animationRunning){
      pauseSimulation();
    } else {
      playSimulation();
    }
  }

  public void playSimulation(){
    if(currentSim != null){
      animation.play();
      animationRunning = true;
    }
  }

  public void pauseSimulation(){
    if(currentSim != null){
      animation.pause();
      animationRunning = false;
    }
  }

  public double setNextFPS(){
    double speedInterval = 0.25;
    double maxSpeedScale = 2.0;
    double minSpeedScale = 0.25;
    double speedScale = animation.getRate() - speedInterval;
    if (speedScale < minSpeedScale) {
      speedScale = maxSpeedScale;
    }
    animation.setRate(speedScale);

    return speedScale;
  }
}
