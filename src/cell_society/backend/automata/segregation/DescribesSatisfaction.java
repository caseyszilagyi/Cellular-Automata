package cell_society.backend.automata.segregation;

import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.Neighbors;

interface DescribesSatisfaction {

  void relocate(int row, int col, Grid nextGrid);

  boolean isSatisfied(Neighbors neighbors);
}
