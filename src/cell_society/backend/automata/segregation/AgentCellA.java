package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

/**
 * Represents an AgentCell with Type A
 *
 * @author George Hong
 */
public class AgentCellA extends AgentCell {

  /**
   * Parameter-less constructor for XML setup.
   */
  public AgentCellA() {

  }

  /**
   * Creates an instance of AgentCell A
   *
   * @param row              row index to place this AgentCell
   * @param col              column index to place this AgentCell
   * @param satisfactionProp proportion of neighbors needed to be of type AgentCellA for this
   *                         AgentCell to remain in the same position.
   */
  public AgentCellA(int row, int col, double satisfactionProp) {
    super(row, col, satisfactionProp);
  }

  /**
   * The update method of this class requires knowledge of other dissatisfied AgentCells. Therefore,
   * the performPrimaryAction method is solely responsible for placing satisfied Cells onto the
   * nextState. Handling the movement of the neighbors is dealt with elsewhere.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid grid that currently holds the cell configuration
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    if (probeState(neighbors, null, null)) {
      AgentCellA agentCell = new AgentCellA(getRow(), getCol(), getSatisfactionProp());
      nextGrid.placeCell(getRow(), getCol(), agentCell);
    }
  }

  /**
   * Allows an external class to handle the relocation of this AgentCell
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new AgentCellA(row, col, getSatisfactionProp()));
  }

  /**
   * Gets the String name of this Class for use with the CellDecoder
   *
   * @return String "AgentCellA"
   */
  @Override
  public String toString() {
    return "AgentCellA";
  }

  /**
   * @return "A", representing AgentCell of type A
   * @Deprecated Returns a length-1 string to be printed in the grid to show occupation by a cell
   */
  @Override
  public String getGridRepresentation() {
    return "A";
  }
}
