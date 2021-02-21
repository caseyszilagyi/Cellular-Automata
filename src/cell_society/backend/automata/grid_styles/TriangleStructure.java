package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class TriangleStructure extends CellStructure {

  @Override
  public List<Coordinate> getAllNeighboringCoordinates(int row, int col) {
    List<TriangularDirection> directions;
    List<Coordinate> allNeighboringCoordinates = new ArrayList<>();
    if ((row + col) % 2 == 0) {
      directions = TriangularDirection.EVEN_1.getEvenConfigurationDirections();
    } else {
      directions = TriangularDirection.EVEN_1.getOddConfigurationDirections();
    }
    for (TriangularDirection d : directions) {
      allNeighboringCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allNeighboringCoordinates;
  }

  @Override
  public List<Coordinate> getAllAdjacentCoordinates(int row, int col) {
    List<Coordinate> allAdjacentCoordinates = new ArrayList<>();
    for (Direction d : TriangularDirection.ODD_1.getAdjacentDirections(row, col)) {
      allAdjacentCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allAdjacentCoordinates;
  }

  public int getCode(){
    return 1;
  }
}
