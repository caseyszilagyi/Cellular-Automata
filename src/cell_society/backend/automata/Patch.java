package cell_society.backend.automata;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents properties or rules of a position on the grid.  Patches are initialized in parallel,
 * and thus can be initialized the same way a cell is.  Patches can hold any number of named integer
 * states.
 */
public class Patch {

  private Map<String, Integer> patchStates = new HashMap<>();

  public Patch() {

  }

  public void setState(String stateName, int newValue) {
    patchStates.putIfAbsent(stateName, newValue);
    patchStates.put(stateName, newValue);
  }

  public int getState(String stateName) {
    return patchStates.get(stateName);
  }

  //  /**
//   * Copies over the patch and its internal states into the next stage
//   *
//   * @return Patch object identical to this one.
//   * @throws CloneNotSupportedException
//   */
//  @Override
//  protected Object clone() throws CloneNotSupportedException {
//    Patch patch = new Patch();
//    patch.patchStates = new HashMap<>(patchStates);
//    return patch;
//  }
  public Patch copy() {
    Patch patch = new Patch();
    patch.patchStates = new HashMap<>(patchStates);
    return patch;
  }
}
