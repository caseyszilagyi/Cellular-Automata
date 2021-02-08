# Cell Society Design Plan
### Team Number: 6
### Names

Donghan Park (dp239), George Hong (grh17), Casey Szilagyi (crs79)

## Introduction

- The goal is to create a program that can run cellular autonoma scenarios with as much flexibility as possible. This means that new simulation types should be able to be added relatively easily and the interface and program logic should be separated

Primary Design Goals:

1. Configuration: use user input and read XML configuration files to set up the desired simulation
2. Simulation Model: update cells based on simulation rules and states of neighboring cells
3. Visualization: represent the simulation model using graphics

Primary Architecture of Design:

- The program will include a basic simulation. This way, we have the methods that are common among all simulations built in at the core of the simulation class, and each specific simulation type can inherit these properties.
- In order to keep the simulation as separate from the visual interface as possible, there will be some type of interface that takes the result of a simulation step and gives it to the front end to display.
- The visual interface will include a grid to display the results of the step of the simulation as well as the buttons/sliders that the user is able to interact with. The user will only have access to these buttons/sliders, and nothing else, because the user should not be able to edit any details relating to the simulation or the grid display. That should all be handled by the inner workings of the programs.

## Overview

Grid, UserInputHandler, and ErrorHandler all link to both Simulation and Display to integrate the front and back end.

![](https://i.imgur.com/wENktJ2.jpg)

![](https://i.imgur.com/ytAFt32.jpg)

#### Frontend:


```java
//The display will have some type of loop that displays the grid (through the grid 
//display), checks for errors/displays those (gets errors through error handler) and 
//checks for user input (through the user input handler)
public class Display{
    public void DisplayGrid();
    public void getUserInput();
    public void checkForErrors();
}
```
```java
// Does this represent the bottom slider and the top controls?
public class UserInterface{
    public void openFileExplorer/Dropdown()
    public void getUserInput();
}
```
```java
public class GridDisplay{
    public Grid requestCurrent(Simulation s)
    public void showGrid();
}
```
```java
//This class deals with transferring any user input data to the simulation so that 
//the simulation remains separate from the user interface.
public class UserInputHandler{
    public void handleMouseClick()
    public void pause()
    public void runAutomatically()
    public void switchSimulation()
}
```
```java
//Deals with transferring error data from the simulation to the display in order 
//to be displayed. Might need another class to hold the error messages, and this 
//can just transfer the boolean data of whether an error is present?
public class ErrorHandler{
    public boolean checkIfError();
    public void setIfMissingDataTagError();
    public void setIfNonRectangularXMLData();
    public void setIfMissingInitialization();
}

public void (open/close)Simulation()

```



#### Backend:
```java
// Populates a Grid.  
public class Cell{
    public Neighbors getNeighbors(CellInitializer cellInit)
    public void makeDecisions(Neighbors neighbors, Grid grid)
}
```

```java
// Aggregates cells and allows a "birds-eye" view of layout, allowing for easier access to neighbors.
public class Grid{
    // useful checker to find vacant spots to move to 
    public boolean isEmpty(int row, int col)
    public void placeCell(int row, int col, Cell cell)
}
```

```java
// Holds the Grid and its associated Cells
public class Simulation{
    // Holds user parameters such as CHANCE_TO_BURN, TIME_TO_REPRODUCE, ...etc
    public int getProperty()
    public int setProperty()
    // checks to see if simulation has ended
    public boolean isOver()
    // Applies overall checks, such as with percolation that may only be determined 
    public void applyOverallDecisions()
    // Sets to an earlier place in history, removing layouts afterwards 
    public void rewind()
    // Send information to the frontend
    public Grid send()
}
```
```java
// Holds the update loop.
public class SimulationStepper{
    public void step(Grid g)    
}
```

```java
// Holds the neighbors of the cell, hiding implementation details
public class Neighbors{
    public int size()
    public int getTypeCount(Cell cell)
}
```

```java
// Calls on the XML
public class SimulationInitializer{
// Reads XML and is then responsible for setting up the correct simulation
    public Simulation readXMLFile(String fileName)
}

```
#### Other Implementations
- **Grid: 2D array vs. List<List<>> vs. 1D array**
    - A 2D array is possibly the most straightforward implementation, but does not allow for changing of size.
    - A list of lists would allow for changes to the Grid size while looping through.  Starting out empty might require a tiny amount of extra work.
    - 1D array can allow for encoding 2D positions by use of integer division and modulo operators, but this would make finding neighbors more difficult, especially with compass directions.
    - Using an adjacency list representing a graph would be very flexible and easy for determining neighbors, but movement of cells could be difficult, and exact positions would be hard to display.


## User Interface

- Grid for simulation
- Toolbar for loading different simulations (i.e., a "choose file" button that allows the user to pick the desired XML file)
- Set of buttons for interacting with the simulation 1) start, 2) stop, 3) step, and 4) reset
- Additionally, there will be some parameters taken (depending on the simulation) at the beginning which deals with how to initialize cells. This will be done with a UserInputHandler that is checked when initializing the simulation in order to transfer data from the display to the simulation
- Errors will be handled using an errorhandler, which will be checked in the simulation loop. this will serve as kind of a way to go between the simulation and display to determine if there are any errors that need to be shown.

![](https://i.imgur.com/owo3T00.png)

## Configuration File Format

(Potential 1: separating width, height, & various parameters in separate tags)
```xml
<simulation>
    <simultype type="gof"/>
    <title>
        Game of Life
    </title>
    <desc>
       Placeholder description
    </desc>
    <author>
       Authors...
    </author>
    <parameter prob=0.5/>
    <parameter width=100/>
    <parameter height=100/>
    
    <initial>
         0 0 0 1 
         1 1 1 0 
         0 1 0 1
         1 1 0 0
    </initial>

</simulation>
```

(Potential 2: keeping width & height as values in the initial configuration of states)
```xml
<simulation>
    <simType type="gof"/>
    <title>
        Game of Life
    </title>
    <desc>
        Placeholder description
    </desc>
    <author>
        Authors...
    </author>
    <initial>
        4 4
        1 1 1 1
        1 0 0 1
        1 0 0 1
        1 1 1 1
    </initial>
    <parameters prob=20 spawn_rate=30/>
</simulation>
```

## Design Details

The `SimulationInitializer` provides support for reading XML and produces an object of the correction Simulation type that the frontend can work with.  The `Simulation` can include important parameters or defines global changes in the behavior of its cells.  At the core of the simulation is a stepper object that helps it iterate through each step.

The `Cell` class populates the `Grid`. `Cell` objects are intended to be extended to encode different behaviors of players in the simulations.  Fish, sharks, fires, and moving individuals can all serve as extensions of the `Cell` class.

Different players may consider their neighbors differently, such as Fires only needing to observe neighbors directly up, down, left, or to the right, while an individual may choose to observe everyone within a one block (or more) radius.  This allows the `identifyNeighbors` method to define which other `Cells` it needs to make decisions.  Players in the simulation can make vastly different choices based on their neighbors. This behavior is encapsulated in the `makeDecisions` method.

Certain configurations of cells may trigger special behaviors, but this may not be known until all cells are in place on their final configuration.  A prime example of this is the Percolation assignment, where water may not trickle down until the final block is placed.  To achieve this, the `Simulation` is given responsibility of checking when the grid is entirely updated, by using the `applyOverallDecisions` method.

#### Cell Implementation/Design
One option is to encode each cell's behavior into an object and its methods.  This allows for a common cell interface, and all cells would modularly contain how it behaves.  The potential issue with this approach is that many classes may have to be generated while creating new simulations.  It also adds a layer of complexity to the timing and organization of reading the XML files and the placement of the initial layout, since cells can vary hugely.

Another option is to encode each cell as an integer.  When examining the simulation, cell encodings can use a lookup table to define behavior.  At any time, the game state is encoded as an array of ints.  Setting the grid up becomes very straightforward, and updates are similarly easy.  The problem with this approach is that updating cells requires a central source of logic that may become unwieldy or difficult to update.

#### Loop Step
This code provides a look into how the simulation update step might work;
```java
// 
Grid currentStep;
Grid nextStep = new Grid(...);

for (int r : Grid.height){
    for (int c : Grid.length){
        Cell cell = Grid.get(r, c);
        Neighbors n = cell.identifyNeighbors(currentStep);
        // How does makeDecisions inform position, instance variables, 
        // and other behavior on the nextStep?
        makeDecisions(nextStep, n);
    }
}
// messy fix to needs of stuff like Percolation.  Maybe extend Grid to deal 
// with events/updates that sees how the classes are in relation to one another.  
applyOverallDecisions();

InitializeSimulation();
Simulation loop:
    getCurrentState();
    makeStep();
    determineIfFinished();
    giveStateToVisualizer();

currentStep = nextStep;
```

## Use Cases
#### A cell checks its neighbors and if dissatisfied can choose to move anywhere on the grid
```java
// Actually a ResidentCell, but loop must be general
Cell resident;

Neighbors n = resident.identifyNeighbors(currentStep);
resident.makeDecisions(nextStep, n);
    calls -> double prop = getNumSameAsSelf(n) / n.size();
          -> if (prop > CONSTANT) 
                 ResidentACell self = new ResidentACell();
                 // Place cell representing self in a new random position
                 nextStep.update(randRow, randCol, self);
                 // nextStep.placeRandom(self) as a convenience method?
```

#### A fish with maximum energy will split
```java
Cell fish;

Neighbors n = fish.identifyNeighbors(currentStep);
fish.makeDecisions(nextStep, n);
             // breedTime is an instance variable of fish
    calls -> breedTime++;
             if (breedTime > CONSTANT)
             // Split fish
                 FishCell fishA = new FishCell();
                 FishCell fishB = new FishCell();
                 nextStep.update(r, c, fishA);
                 nextStep.update(r, c, fishB);
             else 
                 // Place fish in appropriate position         
```

#### Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)

```java
// (r, c) positions are given by the main loop
// alternatively, we could just choose to represent a dead cell with null.
Neighbors n = cell.identifyNeighbors(currentStep);
cell.makeDecisions(nextStep, n);
    calls -> // examine n
          -> DeadCell dc = new DeadCell();
          -> // update the Grid holding the next state
          -> nextStep.update(r, c, dc);
```

#### Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)

```java
// (r, c) positions are given by the main loop
Neighbors n = cell.identifyNeighbors(currentStep);
cell.makeDecisions(nextStep, n);
    calls -> // examine n
          -> AliveCell ac = new AliveCell();
          -> // update the Grid holding the next state
          -> nextStep.update(r, c, ac);
```

#### Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
.step()
->  Grid currentGeneration;
    Grid nextGeneration = new Grid(...);

    for (int r : Grid.height){
        for (int c : Grid.length){
            Cell cell = Grid.get(r, c);
            Neighbors n = cell.identifyNeighbors(currentGeneration);
            // How does makeDecisions inform position, instance variables, 
            // and other behavior on the nextStep?
            makeDecisions(nextGeneration, n);
        }
    }
    applyOverallDecisions();
    
.send()     
```
#### Set a simulation parameter: set the value of a global configuration parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire

```java
readXMLFile(String fileName){
    File file = new File(fileName);
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(file);
    Element root = document.getDocumentElement();
    probCatch = document.getAttribute("ProbCatch");
    
    CellLogic cellLogic = new CellLogic();
    cellLogic.setFireProb(ProbCatch);
}
```

#### Switch simulations: load a new simulation from an XML file, stopping the current running simulation, Segregation, and starting the newly loaded simulation, Wa-Tor

This would be called by user input, so when the simulation checks for user input, it will make a new simulation initializer that will replace the current simulation
```java
checkForUserInput(){
    String nextLine = getNextLine();
    if(nextLine != null){
        SimulationInitializer currentSimulation = new SimulationInitializer(nextLine);
        currentGrid = currentSimulation.getGrid();[]['/']
    }
}
```

#### Take the user input from the GUI for the Wa-Tor World simulation and send it to the simulation initializer so that it can make the starting state of the simulation


In the display class, this method will be called on an instance of the userInterface class and will get the input from the user interface.
```java
userInterface.getUserInput(){
    // The userData class's implementation will have to change for different 
    // simulation types, becasue the user input data will never be the same
    userInputData userData = new userInputData();
    userData.putUserDataInStructure();
    //Getting the data, putting it into an arraylist of arraylists
    //format
    ArrayList<Integer> intData = userData.getIntData();
    ArrayList<Boolean> boolData = userData.getBoolData();
    ArrayList<ArrayList<>> allData = new ArrayList<ArrayList<>>(boolData, intData);
    
.send();
}

// In the userInputData class, specifically implemented 
// for each simulation type
putUserDataInStructure(){
    int value1 = slider1.getValue();
    boolean bool1 = checkBox1.isSelected();
    ...
    placeDataInStructure();
}
```

#### Bad data is given in the XML file, so the simulation intializer tells the error handler

```java
//Method called durnig the initialization
checkForBadData(){
    //When one of these result in an error, the errorhandler will be notified
    checkForAllCrucialTags();
    checkForNonRectangularData();
    checkForMissingInitialization();
}

//This is a basic idea of what the errorHandler would do. it would have a bunch of 
//different errors and maybe booleans that refer to those errors, and if true, the 
//display would be able to show a certain error message.
errorHandler.setCrucialTagsError(true);
errorHandler.hasError(true);

//and in the display loop, errors would be checked for every loop, so it would catch this
if(errorHandler.getError){
    Image displayError = errorHandler.findWhichError();
    display(displayError);
}
```

#### A shark eats a fish when they share the same cell

```java
Cell fish;

Neighbors n = fish.identifyNeighbors(currentStep);
fish.makeDecisions(nextStep, n);

//in makeDecisions
if (overlappingCell.getClass().getName().equals("shark")){
    destroy(fish)
}
```

#### Reset simulation: stop and reset the simulation

```java
// resetting a simulation will essentially be the same as switching to the 
// same simulation, except no new user parameters will have to be taken
restartSimulation(String simulationName){
    grid.clear();
    SimulationInitializer.readXMLFile(simulationName);
    
    //load initial state of simulation
    //update sim; continue sim loop
}
```

## Design Considerations

Our group decided that the core simulation update functionality would be transferring information from the current board onto a new one, maintaining the feeling of a simultaneous update.  The new board is then set as the current board.
- **Pros:** Straightforward and easy to update and position
- **Cons:** Requires two Grids and constructing new objects each time to populate the new grid.

One alternative to transferring data from two boards is to enable Cells to maintain current and next state information.
- **Pros:** Very clean structure and would allow checking of overall positions to be easy.  This also avoids the need to populate a new grid object with cells.
- **Cons:** Cells may occasionally have to maintain many pieces of information such as energy or a timer or type.  Depending on the design, this interaction may conflict with changes to the Cell object (a Shark Cell transitioning to the spot of a Fish Cell).  Cells would also need to update their next state as current and clear the next information.

Having the cell logic within each cell as opposed to in a separate class or in the class for a grid
- **Pros:** Makes it so that each cell is responsible for it's own update so that each simulation step is generalized. Makes it easier to make a new simulation becasue each cell can just be made with different parameters
- **Cons:** Maybe a little bit repetitive, because each cell has all the same logic but all confined to the individual cell. Additionally, any change in logic mid simulation would be more difficult to implement
- Need to figure out how to implement our cells. Do we have one type of cell per simulation, or do we have multiple types that extend the same type (such as a shark, fish, and water) cell type
- How do we check neighbors? Does each cell simply have its neighbor data in a list? Or do we do this through the grid?


## Team Responsibilities

* Team Member #1
  **George Hong**
  - Primary: Backend
  - Secondary: XML
  
* Team Member #2
  **Casey Szilagyi**
  - Primary: XML (and how it integrates into the back end)
  - Secondary: Front/Back end integration

* Team Member #3
  **Donghan Park**
  - Primary: Frontend
  - Secondary: Front/Back end integration
