package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.simulation_initializer.CellParameters;

/**
 * Represents an agent in Schelling's model of segregation as a cell on a grid.
 * <p>
 * Primary Action: a satisfied agent will place a copy of itself in the same place, on the next
 * grid. Secondary Action: N/A Probe State: probe whether the cell is satisfied
 */
public class AgentCell extends Cell {

  private double satisfactionProp;

  public AgentCell(int row, int col, double satisfactionProp) {
    super(row, col);
    this.satisfactionProp = satisfactionProp;
  }

  public AgentCell() {

  }

  public double getSatisfactionProp() {
    return satisfactionProp;
  }

  @Override
  public void initializeParams(CellParameters parameters) {
    this.satisfactionProp = parameters.getAsDouble("satisfactionprop");
  }

  /**
   * The AgentCell considers all neighbors in its vicinity to make its decisions.
   *
   * @param grid grid holding the current configuration of cells
   * @return
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    return grid.getDirectNeighbors(row, col);
  }

  /**
   * The update method of this class requires knowledge of other dissatisfied AgentCells. Therefore,
   * the makeDecisions method is solely responsible for placing satisfied Cells onto the nextState.
   * Handling the movement of the neighBors is dealt with elsewhere.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    if (probeState(neighbors, null, null)) {
      AgentCell agentCell = new AgentCell(getRow(), getCol(), satisfactionProp);
      nextGrid.placeCell(getRow(), getCol(), agentCell);
    }
  }

  /**
   * Determines whether a Cell is satisfied
   *
   * @param neighbors
   * @param currentGrid
   * @param nextGrid
   * @return
   */
  public boolean probeState(Neighbors neighbors, Grid currentGrid,
      Grid nextGrid) {
    double numSameNeighbors = neighbors.getTypeCount(this);
    double totalNeighbors = neighbors.size();
    if (totalNeighbors == 0) {
      return true;
    }
    return numSameNeighbors / totalNeighbors >= satisfactionProp;
  }

  @Override
  public String toString() {
    return "+";
  }
}
