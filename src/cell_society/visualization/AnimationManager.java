package cell_society.visualization;

import cell_society.backend.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The AnimationManager class handles the main simulation loop by updating the
 * display views at a certain FPS. It includes methods to play/pause the
 * simulation, as well as step or change the speed.
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
   * Constructor that creates an instance of the AnimationManager object
   * @param displayManager Reference to the DisplayManager object for the current simulation window
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

  /**
   * Sets the reference to the current Simulation object with a new Simulation object
   * @param currentSim Reference to the current Simulation object in the backend
   */
  public void setSimulation(Simulation currentSim){
    this.currentSim = currentSim;
  }

  /**
   *
   */
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

  /**
   * Toggles the animation loop to play/pause
   */
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

  /**
   * Pauses the animation loop
   */
  public void pauseSimulation(){
    if(currentSim != null){
      animationRunning = false;
    }
  }

  /**
   * Toggles the speed of the animation loop by decreasing the animation rate
   * by a certain increment. If the new rate is below the minimum required,
   * the rate is set to the maximum allowed.
   * @return New rate of the animation
   */
  public double setNextFPS(){
    double speedScale = animation.getRate() - SPEED_INTERVAL;
    if (speedScale < MIN_SPEED_SCALE) {
      speedScale = MAX_SPEED_SCALE;
    }
    animation.setRate(speedScale);

    return speedScale;
  }
}
