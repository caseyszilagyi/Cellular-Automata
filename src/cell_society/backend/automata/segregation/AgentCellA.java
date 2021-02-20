package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

public class AgentCellA extends AgentCell {

  public AgentCellA() {

  }

  public AgentCellA(int row, int col, double satisfactionProp) {
    super(row, col, satisfactionProp);
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    if (probeState(neighbors, null, null)) {
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
