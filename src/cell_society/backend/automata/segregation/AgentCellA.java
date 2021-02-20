package cell_society.backend.automata.segregation;

import cell_society.backend.automata.grid.Grid;
import cell_society.backend.automata.Neighbors;

public class AgentCellA extends AgentCell {

  public AgentCellA() {

  }

  public AgentCellA(int row, int col, double satisfactionProp) {
    super(row, col, satisfactionProp);
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    if (isSatisfied(neighbors)) {
      AgentCellA agentCell = new AgentCellA(getRow(), getCol(), getSatisfactionProp());
      nextGrid.placeCell(getRow(), getCol(), agentCell);
    }
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new AgentCellA(row, col, getSatisfactionProp()));
  }

  @Override
  public String toString() {
    return "AgentCellA";
  }

  @Override
  public String getGridRepresentation() {
    return "A";
  }
}
