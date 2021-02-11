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


  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    return grid.getDirectNeighbors(row, col);
  }

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

    if (similarNeighborsProp >= satisfactionProp) {
      int row = getRow();
      int col = getCol();
      AgentCell agentCell = new AgentCell(row, col, satisfactionProp, agentType);
      nextGrid.placeCell(row, col, agentCell);
    } else {
      moveToVacantSpot(currentGrid, nextGrid, this);
    }
  }

  public void moveToVacantSpot(Grid current, Grid next, AgentCell agent) {
    /*
    If the agent chooses to move to a vacant spot, it must find a currently available spot, and it cannot conflict with the choices of another agent that chooses to move too in a round.  Does nothing if no choices are available.
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
      } else {
        potentialSpots.remove(coordinate);
      }
    }
  }
}
