# Cell Society Design Final
### Names

## Team Roles and Responsibilities

 * Team Member #1
    - George Hong, Backend

 * Team Member #2

 * Team Member #3


## Design goals

#### What Features are Easy to Add
- **New simulations** where cells directly *update down* are often very straightforward to write with this system.  Some examples of this include *Spreading Fire*, *Game of Life*, *Rock-Paper-Scissors*, and *Percolation*.  Within all these simulations, cells are directly responsible for influencing the type of `Cell` only in its `(row, col)` for the next step.  These simulations only rely on a single pass update loop.  
   - Encode the desired Cell-types as subclasses of `Cell`.  The `getNeighbors` method is responsible for determining consideration of neighbors.  `performPrimaryAction` is intended to encapsulate all behaviors during the single pass update loop, producing the `Cell`'s next state.
- **More sophisticated update rules:** `Cell` behavior is very localized.  Changes to `Cell` behavior are almost always handled within the `performPrimaryAction` of the cell.  Here, the update rules can be directly changed.  Because all `Cell`s contain knowledge of their location on the grid, even more sophisticated rules can be introduced quickly, such as rules that focus on the relative positioning of `Cell`s.    
- **Cell shape and neighbor definitions:** `CellStructure` global treatment of cell Moore neighbors with different shapes and considerations for adjacent neighbors can be introduced fairly quickly.  Support for square, triangular, and hexagonal already exists.  The introduction of a new shape is through a `CellStructure` and corresponding `Direction` enum, defined as an abstract class and interface, respectively.  Within the `Direction` enum, definitions of adjacent neighbors can be quickly updated.  

## High-level Design

#### Core Classes
- `Grid` holds configuration of all `Cell` and `Patch` objects in a simulation.  Can be queried to provide an encoding of Object placements.
    - `CellStructure` make use of the corresponding `Direction` enum to track the structure of the `Grid`
- `Cell` can represent moving Agents in a simulation or more static positions on the `Grid`.  `Cell`s are able to hold individual information and are directly called to determine their next state on the board.
- `Patch` represent static positions on the board, similar to an immovable plot of land.  They can be constructed Parallel to `Cell`s and also support their own set of update rules.
- `SimulationStepper` manages the update of the `Grid` and communication with the frontend.


## Assumptions that Affect the Design
1. A spot on the `Grid` can only be occupied by one `Cell` at a time
2. The `Direction` enum only transforms a coordinate one unit in any given direction.
3. The `SimulationStepper` class declares the `Grid` inside the `makeStep` function, and the current update loop uses a double for-loop.
#### Features Affected by Assumptions
1. In the ant simulation, only one `AntCell` can occupy an `AntPatch` at a time.
2. `SugarAgents` of *Sugar Scape* are limited to a vision of 1 for now.
3. Grid edges are not able to be declared in the XML files at the moment because they will be overwritten by the `makeStep` function.  An additional restriction of the `InfiniteGrid` implementation is the continued use of a for loop, but this can be swapped out for the more general `getUpdateCoordinateList` method.

## Significant differences from Original Plan
- Patches were introduced to complement `Cell` objects when those `Cell` objects have a more dynamic agent role, where they move across the `Grid`.
- `Cell`s now have a `performSecondaryAction`, `probeState`, and `relocate` method for more complex simulations with the `Stepper`.


## New Features HowTo

#### Easy to Add Features

- Adding a new Simulation
    1. Define all relevant `Cell` types using the provided framework.
        - Implement the rules of the `Cell` in the `performPrimaryAction` method and refine the `getNeighbors` method as necessary.
        - For more complex simulations, additional functionality can be placed in the `performSecondaryAction` method, intended for use outside the main update loop.  The `probeState` and `relocate` methods also serve as helper methods when needed.
    2. `Patch` objects with the desired properties can be introduced.  This is only necessary if desired.    
    3. Use the following update framework to drive the `Cell`s.  The code is general enough to allow for the use of `Patch` objects, but the user does not have to support them.
    ```java
    Grid nextGrid = new Grid(simulationGrid);
    nextGrid.updateRemainingPatches(simulationGrid);

    List<Coordinate> coordinateList = simulationGrid.getCoordinateUpdateList();

    for (Coordinate coordinate : coordinateList) {
      int j = coordinate.getFirst();
      int k = coordinate.getSecond();
      Cell cell = simulationGrid.getCell(j, k);
      Patch patch = nextGrid.getPatch(j, k);
      // TODO: Any other updates can be performed here, but usually not necessary.
      if (patch != null) {
        patch.applyUpdateRule();
      }
      if (cell != null) {
        cell.performPrimaryAction(null, simulationGrid, nextGrid);
      }
    }
    // TODO: Add any additional overall changes here
        
    simulationGrid = nextGrid;
    ```

#### Other Features not yet Done
- **Infinite Grid-compatible Stepper**
- **Scrolling view with Infinite Grid**
    - The `InfiniteGrid` can rely on an adjusted `getIntDisplay` method that enables the frontend to specify the bounding corner and provide a rectangular slice of the `Cell` states from there.  The frontend can then call on this method with sliders to navigate the `InfiniteGrid` that would otherwise not fit on the screen.
- **Bly's Loops**
    - Not complete due to the amount of transition rules.  These transition rules can be directly encoded into the `performPrimaryAction` method of the `Cell`s.
- **Different Arrangements of Neighbors**
    - All `Cell`s have a `performPrimaryAction` method which have access to the `Neighbors` containing neighboring `Cell`s.  Additionally, all location information is present.  This allows the encoding of specific configurations to be done within the `Cell` or as a private helper method.  This requires careful encoding of the relationships between `row, col` of the different `Cell`s.


