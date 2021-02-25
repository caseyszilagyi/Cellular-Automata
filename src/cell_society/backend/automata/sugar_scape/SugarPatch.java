package cell_society.backend.automata.sugar_scape;

import cell_society.backend.automata.Patch;
import cell_society.backend.simulation_initializer.CellParameters;

/**
 * Represents a Patch of Land in the SugarScape simulation, where Sugar can automatically grows back
 * and can be harvested by Agents.
 *
 * @author George Hong
 */
public class SugarPatch extends Patch {

  public static final String SUGAR = "sugar";
  public static final String SUGAR_GROWBACK_RATE = "sugargrowbackrate";
  public static final String SUGAR_GROW_BACK_INTERVAL = "sugargrowbackinterval";
  private int sugarGrowBackRate;
  private int sugarGrowBackInterval;
  private int sugarGrowBackCounter;

  /**
   * Parameter-less constructor for a Sugar Patch.
   */
  public SugarPatch() {

  }

  /**
   * Constructs an instance of a SugarPatch
   *
   * @param sugar                 initial amount of Sugar contained in this patch
   * @param sugarGrowBackRate     amount of sugar regenerated after enough time steps have elapsed
   * @param sugarGrowBackInterval amount of time steps required before Sugar grows back
   */
  public SugarPatch(int sugar, int sugarGrowBackRate, int sugarGrowBackInterval) {
    sugarGrowBackCounter = 0;
    this.sugarGrowBackRate = sugarGrowBackRate;
    this.sugarGrowBackInterval = sugarGrowBackInterval;
    super.setState(SUGAR, sugar);
  }

  /**
   * Initializes the patch with the desired parameters, intended for use with the parameter-less
   * constructor.
   *
   * @param parameters desired parameters for this patch object.
   */
  @Override
  public void initializeParams(CellParameters parameters) {
    sugarGrowBackRate = parameters.getAsInt(SUGAR_GROWBACK_RATE);
    sugarGrowBackInterval = parameters.getAsInt(SUGAR_GROW_BACK_INTERVAL);
    super.setState(SUGAR, parameters.getAsInt(SUGAR));
    sugarGrowBackCounter = 0;
  }

  /**
   * Grow back sugarGrowBackRate units of sugar every sugarGrowBackInterval ticks, up to the maximum
   * capacity.
   */
  @Override
  public void applyUpdateRule() {
    sugarGrowBackCounter++;
    if (sugarGrowBackCounter % sugarGrowBackInterval == 0) {
      int currentPatchSugar = super.getState(SUGAR);
      super.setState(SUGAR, currentPatchSugar + sugarGrowBackRate);
    }
  }

  /**
   * Allows these Patches to be transferred over to the next state
   *
   * @return copy of this patch with the same variable values.
   */
  @Override
  public Patch copy() {
    SugarPatch patch = new SugarPatch(super.getState(SUGAR), sugarGrowBackRate,
        sugarGrowBackInterval);
    patch.sugarGrowBackCounter = sugarGrowBackCounter;
    patch.setInternalData(super.passInternalData());
    //patch.setInternalData(super.passInternalData());
    return patch;
  }

  /**
   * Allows the grid to print this patch, displaying the amount of sugar
   *
   * @return String representation of the amount of sugar contained in this patch
   */
  @Override
  public String getGridRepresentation() {
    return super.getState(SUGAR) + "";
  }

  /**
   * Printes current properties of this patch for debugging
   *
   * @return String representation of this object
   */
  @Override
  public String toString() {
    return super.toString() + "[SUGAR: " + super.getState(SUGAR) + "][COUNTER: "
        + sugarGrowBackCounter + "]";
  }
}
