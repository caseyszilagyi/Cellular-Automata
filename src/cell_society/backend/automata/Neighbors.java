package cell_society.backend.automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the neighbors cells a Cell object will use to make decisions.
 */
public class Neighbors {

  private List<Cell> neighborCells;

  public Neighbors() {
    neighborCells = new ArrayList<>();
  }

  /**
   * Gets the number of neighbors a cell must consider to make a decision
   *
   * @return number of neighbors a cell wants to consider
   */
  public int size() {
    return neighborCells.size();
  }

  public void add(Cell cell) {
    if (cell != null) {
      neighborCells.add(cell);
    }
  }

  public Cell get(int index) {
    return neighborCells.get(index);
  }


  /**
   * Counts the number of neighbors that are of the same type cell as the provided cell
   *
   * @param cell cell currently considering this as all of its neighbors
   * @return number of neighbors that share the same type as the cell
   */
  public int getTypeCount(Cell cell) {
    int count = 0;
    for (Cell neighbor : neighborCells) {
      if (neighbor.getClass() == cell.getClass()) {
        count++;
      }
    }
    return count;
  }
  public void removeNull(){
    neighborCells.removeIf(Objects::isNull);
  }
}
