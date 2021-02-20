package cell_society.backend.automata.sugar_scape;

import cell_society.backend.automata.Patch;

public class SugarPatch extends Patch {

  public static final String SUGAR = "sugar";
  private final int sugarGrowBackRate;
  private final int sugarGrowBackInterval;
  private int sugarGrowBackCounter;

  public SugarPatch(int sugar, int sugarGrowBackRate, int sugarGrowBackInterval) {
    sugarGrowBackCounter = 0;
    this.sugarGrowBackRate = sugarGrowBackRate;
    this.sugarGrowBackInterval = sugarGrowBackInterval;
    super.setState(SUGAR, sugar);
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
}
