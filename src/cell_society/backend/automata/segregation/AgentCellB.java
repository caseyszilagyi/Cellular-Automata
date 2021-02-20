package cell_society.backend.automata.segregation;

import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.Neighbors;

public class AgentCellB extends AgentCell {

  public AgentCellB() {

  }

  public AgentCellB(int row, int col, double satisfactionProp) {
    super(row, col, satisfactionProp);
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    if (probeState(neighbors, null, null)) {
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
    return "AgentCellB";
  }

  @Override
  public String getGridRepresentation() {
    return "B";
  }
}
