package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Patch;
import cell_society.backend.simulation_initializer.CellParameters;

/**
 * Patch of land for use in the foraging ants simulation.  Patches contain a constant food level and
 * amount of pheromones attached to this patch.
 *
 * @author George Hong
 */
public class AntPatch extends Patch {

  public static final String FOOD_PHEROMONE_LEVEL = "foodpheromonelevel";
  public static final String FOOD_LEVEL = "foodlevel";

  /**
   * Empty constructor for use with XML initialization
   */
  public AntPatch() {

  }

  /**
   * Constructs an instance of the Ant Patch
   *
   * @param food               food level on this patch
   * @param foodPheromoneLevel current pheromone level of this patch
   */
  public AntPatch(int food, int foodPheromoneLevel) {
    super.setState(FOOD_LEVEL, food);
    super.setState(FOOD_PHEROMONE_LEVEL, foodPheromoneLevel);
  }

  /**
   * Initialize the Patch with parameters read from the XML file.  Intended for use alongside the
   * empty constructor.
   *
   * @param parameters
   */
  @Override
  public void initializeParams(CellParameters parameters) {
    super.setState(FOOD_PHEROMONE_LEVEL, parameters.getAsInt(FOOD_PHEROMONE_LEVEL));
    super.setState(FOOD_LEVEL, parameters.getAsInt(FOOD_LEVEL));
  }

  /**
   * Enables the Patch to return a copy of itself that is mutable.
   *
   * @return Patch object with the same internal values as this current Patch
   */
  @Override
  public Patch copy() {
    AntPatch patch = new AntPatch(super.getState(FOOD_LEVEL), super.getState(FOOD_PHEROMONE_LEVEL));
    patch.setInternalData(super.passInternalData());
    return patch;
  }

  /**
   * @Deprecated
   * Returns the Pheromone level of this patch for debugging in the console.
   * @return
   */
  @Override
  public String getGridRepresentation() {
    return super.getState(FOOD_PHEROMONE_LEVEL) + "";
  }
}
