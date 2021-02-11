package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an agent in Schelling's model of segregation as a cell on a grid.
 */
public class AgentCell extends Cell {

  private final double satisfactionProp;
  private final AgentType agentType;

  public AgentCell(int row, int col) {
    super(row, col);
    satisfactionProp = 0.5;
    agentType = AgentType.X;
  }

  /**
   * @param row              row index of Grid that this AgentCell resides in
   * @param col              column index of Grid that this AgentCell resides in
   * @param satisfactionProp decimal proportion of neighbors that must be of the same type as this
   *                         AgentCell AgentCell for it to be satisfied and to remain in its current
   *                         location.
   * @param agentType        type of this Agent, potentially representing race, ethnicity, economic
   *                         status, etc.
   */
  public AgentCell(int row, int col, double satisfactionProp, AgentType agentType) {
    super(row, col);
    this.satisfactionProp = satisfactionProp;
    this.agentType = agentType;
  }


  /**
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
   * Allows the cell to determine whether it is satisfied with its current location.  A satisfied
   * agent is surrounded by at least (satisfactionProp) agents that are like itself.  A dissatisfied
   * agent will attempt to relocate to any free location on the grid.  A satisfied agent will remain
   * in place.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid,
      Grid currentGrid) {
    int totalNeighbors = neighbors.size();
    int similarNeighbors = 0;
    for (int k = 0; k < totalNeighbors; k++) {
      AgentCell neighbor = (AgentCell) neighbors.get(k);
      if (this.agentType == neighbor.agentType) {
        similarNeighbors++;
      }
    }
    double similarNeighborsProp = (double) similarNeighbors / totalNeighbors;

    if (similarNeighborsProp < satisfactionProp && moveToVacantSpot(currentGrid, nextGrid, this)) {
      return;
    }
    int row = getRow();
    int col = getCol();
    AgentCell agentCell = new AgentCell(row, col, satisfactionProp, agentType);
    nextGrid.placeCell(row, col, agentCell);
  }

  /**
   * Helper method for the AgentCell to attempt to move to a new location, if available.
   *
   * @param current current Grid configuration
   * @param next    Grid configuration for the next state
   * @param agent   agent's information to bring to the next state.
   * @return whether or not the agent was able to successfully move to a new location.
   */
  public boolean moveToVacantSpot(Grid current, Grid next, AgentCell agent) {
    /*
    If the agent chooses to move to a vacant spot, it must find a currently available spot on the board, and it cannot conflict with the choices of another agent that has already chosen to move too in a round.  The agent will choose to stay in place if no new position is available, writing to the next Grid state appropriately.
     */
    List<Coordinate> potentialSpots = current.getAllVacantSpots();
    while (potentialSpots.size() > 0) {
      int randomCoordinateIndex = ThreadLocalRandom.current().nextInt(0, potentialSpots.size());
      Coordinate coordinate = potentialSpots.get(randomCoordinateIndex);
      int r = coordinate.getFirst();
      int c = coordinate.getSecond();
      if (next.isEmpty(r, c)) {
        AgentCell newAgentCell = new AgentCell(r, c, agent.satisfactionProp, agent.agentType);
        next.placeCell(r, c, newAgentCell);
        return true;
      } else {
        potentialSpots.remove(coordinate);
      }
    }
    return false;
  }
}