package cell_society.backend.automata;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents properties or rules of a position on the grid.  Patches are initialized in parallel,
 * and thus can be initialized the same way a cell is.  Patches can hold any number of named integer
 * states.
 *
 * @author George Hong
 */
public class Patch {

  private Map<String, Integer> patchStates = new HashMap<>();

  /**
   * Initialize a Patch
   */
  public Patch() {

  }

  /**
   * Allows implementation of automatic Patch Updates.
   */
  public void applyUpdateRule() {

  }

  /**
   * Add or update a property of the patch
   *
   * @param stateName name of property to be updated
   * @param newValue  new value of provided property
   */
  public void setState(String stateName, int newValue) {
    patchStates.putIfAbsent(stateName, newValue);
    patchStates.put(stateName, newValue);
  }

  /**
   * Get the current state of the patch
   *
   * @param stateName name of property to be returned
   * @return current value of desired property
   */
  public int getState(String stateName) {
    return patchStates.get(stateName);
  }

  /**
   * Helper method to directly lift patch property from current state to the next
   *
   * @return identical copy of this Patch.
   */
  public Patch copy() {
    Patch patch = new Patch();
    patch.patchStates = new HashMap<>(patchStates);
    return patch;
  }

  protected Map<String, Integer> passInternalData() {
    return new HashMap<>(patchStates);
  }

  protected void setInternalData(Map<String, Integer> map) {
    patchStates = new HashMap<>(map);
  }

  /**
   * Debugging tool to view patch properties in the console
   *
   * @return string representing a single patch property.
   */
  public String getGridRepresentation() {
    return "?";
  }
}
