package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Patch;

public class AntPatch extends Patch {

  public static final String FOOD_PHEROMONE_LEVEL = "foodpheromonelevel";
  public static final String FOOD_LEVEL = "foodlevel";

  public AntPatch(int food, int foodPheromoneLevel) {
    super.setState(FOOD_LEVEL, food);
    super.setState(FOOD_PHEROMONE_LEVEL, foodPheromoneLevel);
  }

  @Override
  public Patch copy() {
    AntPatch patch = new AntPatch(super.getState(FOOD_LEVEL), super.getState(FOOD_PHEROMONE_LEVEL));
    patch.setInternalData(super.passInternalData());
    return patch;
  }
}
