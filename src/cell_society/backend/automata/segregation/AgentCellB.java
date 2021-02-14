package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

public class AgentCellB extends AgentCell {

  public AgentCellB() {

  }

  public AgentCellB(int row, int col, double satisfactionProp) {
    super(row, col, satisfactionProp);
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    if (isSatisfied(neighbors)) {
      AgentCellB agentCell = new AgentCellB(getRow(), getCol(), getSatisfactionProp());
      nextGrid.placeCell(getRow(), getCol(), agentCell);
    }
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new AgentCellB(row, col, getSatisfactionProp()));
  }

  @Override
  public String toString() {
    return "B";
  }
}
