package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.simulation_initializer.CellParameters;

/**
 * Represents an agent in Schelling's model of segregation as a cell on a grid.
 */
public class AgentCell extends Cell {

  private double satisfactionProp;

  public AgentCell(int row, int col, double satisfactionProp, String agentType) {
    super(row, col);
    this.satisfactionProp = satisfactionProp;
    setCellID(agentType);
  }

  public AgentCell() {

  }

  public double getSatisfactionProp() {
    return satisfactionProp;
  }

  public String getAgentType() {
    return getCellID();
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
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    if (isSatisfied(neighbors)) {
      AgentCell agentCell = new AgentCell(getRow(), getCol(), satisfactionProp, getCellID());
      nextGrid.placeCell(getRow(), getCol(), agentCell);
    }
  }

  /**
   * Determines whether a Cell is satisfied
   *
   * @param neighbors
   * @return
   */
  public boolean isSatisfied(Neighbors neighbors) {
    double numSameNeighbors = 0;
    for (int k = 0; k < neighbors.size(); k++) {
      // In the segregation simulation, all neighbors are of cell type AgentCell
      AgentCell otherCell = (AgentCell) neighbors.get(k);
      if (otherCell.getCellID().equals(this.getCellID())) {
        numSameNeighbors++;
      }
    }
    return numSameNeighbors / neighbors.size() >= satisfactionProp;
  }

  @Override
  public String toString() {
    return getCellID().substring(0, 1);
  }
}
