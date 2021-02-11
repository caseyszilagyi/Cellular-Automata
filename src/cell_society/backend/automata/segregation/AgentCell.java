package cell_society.backend.automata.segregation;

import cell_society.backend.automata.Cell;

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
   * @param satisfactionProp decimal proportion of neighbors that must be of the same type as this AgentCell
   *                         AgentCell for it to be satisfied and to remain in its current
   *                         location.
   * @param agentType        type of this Agent, potentially representing race, ethnicity, economic
   *                         status, etc.
   */
  public AgentCell(int row, int col, int satisfactionProp, AgentType agentType) {
    super(row, col);
    this.satisfactionProp = satisfactionProp;
    this.agentType = agentType;
  }

}
