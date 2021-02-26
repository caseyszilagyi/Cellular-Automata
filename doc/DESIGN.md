# Cell Society Design Final
### Names

## Team Roles and Responsibilities

 * Team Member #1: Casey Szilagyi
    - XML/Configuration
    - Integrating front/back end

 * Team Member #2

 * Team Member #3


## Design goals
- We tried to keep the details of how cells behave as deep in the project
as possible (in cell classes) so that higher level classes could be reused
  
- Wanted the grid to be the same for all simulation types so that the only
thing varying was the cell types (we ended up having different stepper types
  as well)

## High-level Design
- The XML file has all of the details for the cells that populate the grid.
Additionally, the file has the grid type, grid shape, and simulation stepper
  type that need to be used
  
- The simulation initializer uses the XMLFileReader to parse through this data
and initialize the grid, populate the grid with cells and patches, and give
  the grid to the simulation class along with the simulation stepper.
  
- The simulation class uses the stepper to make singular steps in the simulation.
The details for performing a step is done within each cell type for the given
  simulation, as well as in the simultion stepper class.
  
- The displayManager class is what is calling the makeStep function in
the simulation class. Each time this is done, the front end can call
  the getGridDisplay method to get an integer array that has all of the 
  details to display the grid (including shape)


#### Core Classes
- Main: The class that is run to initialize the simulation
- Simulation: This class has an instance of a simulation initializer, that
deals with configuring the simulation through the XMLReader
  
- DisplayManager: This class has most of the implementation details for the
display

## Assumptions that Affect the Design
- It is assumed that the user will declare the correct stepper type
in the XML file, or else the program will break due to downcasting


#### Features Affected by Assumptions
- 


## Significant differences from Original Plan
- Didn't really have much of a controller, and instead just had a display
that initialized a simulation and called the step method
  
- Didn't anticipate the XML file having such a large role in configuring the
simulation. This wasn't necessarily deviating from the plan, it's just that
  the amount of data to be read in through the XML file was unexpected
  
- Had simulation steppers that were different for each simulation type. We
thought that we could include all of the implementation details for each simulation
  within the different cell types but this proved more difficult than we originally
  anticipated


## New Features HowTo
- To add a new patch variable, all the user has to do is add a new patch
  and all the relevant details under the gridInfo tag in the XML file
  

#### Easy to Add Features
- Adding more variables to the patch (which deals with non-moving variables) is
pretty easy
  
- Very easy to add new shapes/neighbor types. Simply make a new cellStructure
type, and decide whatever neighbor configuration is desired. The display details
  have to be implemented in the front end through math
  


#### Other Features not yet Done
- Store the current simulation configuration as an XML file
- Alter a single cell state by clicking on it
