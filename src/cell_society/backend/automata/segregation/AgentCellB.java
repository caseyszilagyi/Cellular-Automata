package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

public class AgentCellB extends AgentCell {

  public AgentCellB() {

  }

  public AgentCellB(int row, int col, double satisfactionProp, String agentType) {
    super(row, col, satisfactionProp, agentType);
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    if (isSatisfied(neighbors)) {
      AgentCellB agentCell = new AgentCellB(getRow(), getCol(), getSatisfactionProp(), getCellID());
      nextGrid.placeCell(getRow(), getCol(), agentCell);
    }
  }

  @Override
  public String toString() {
    return "B";
  }
}
