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

  private final double SPEED_INTERVAL = 0.25;
  private final double MIN_SPEED_SCALE = 0.25;
  private final double MAX_SPEED_SCALE = 2.0;

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
      if(animationRunning){
        stepSimulation();
      }
      updateDisplays();
    });

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
    animation.play();
  }

  public void setSimulation(Simulation currentSim){
    this.currentSim = currentSim;
  }

  public void stepSimulation(){
    if(currentSim != null){
      currentSim.makeStep();
    }
  }

  private void updateDisplays(){
    if(currentSim != null){
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

  private void playSimulation(){
    if(currentSim != null){
      animationRunning = true;
    }
  }

  public void pauseSimulation(){
    if(currentSim != null){
      animationRunning = false;
    }
  }

  public double setNextFPS(){
    double speedScale = animation.getRate() - SPEED_INTERVAL;
    if (speedScale < MIN_SPEED_SCALE) {
      speedScale = MAX_SPEED_SCALE;
    }
    animation.setRate(speedScale);

    return speedScale;
  }
}
