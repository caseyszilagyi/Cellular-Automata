package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SquareStructure extends CellStructure {

  @Override
  public List<Coordinate> getAllNeighboringCoordinates(int row, int col) {
    List<Coordinate> allNeighboringCoordinates = new ArrayList<>();
    for (Direction d : SquareDirection.values()) {
      allNeighboringCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allNeighboringCoordinates;
  }

  @Override
  public List<Coordinate> getAllAdjacentCoordinates(int row, int col) {
    List<Coordinate> allAdjacentCoordinates = new ArrayList<>();
    for (Direction d : SquareDirection.TOP.getAdjacentDirections(row, col)) {
      allAdjacentCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allAdjacentCoordinates;
  }

  @Override
  public Direction getRandomDirection(int row, int col) {
    SquareDirection[] directions = SquareDirection.values();
    int length = directions.length;
    return directions[ThreadLocalRandom.current().nextInt(0, length)];
  }

  public int getCode(){
    return 0;
  }

}
