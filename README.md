Cell Society
====

This project implements a cellular automata simulator.

Names: Donghan Park (dp239), George Hong (grh17), Casey Szilagyi (crs79)

### Timeline

Start Date: 2/8/2021

Finish Date: 2/22/2021

Hours Spent:
- Casey: 25+25 = 50
- George: 25+25 = 50
- Donghan: 15+28 = 43

### Primary Roles
- Casey:
  - XML/config (Entire simulation_initializer package)
  - Front/Back end integration
- George:
  - Backend (`cell_society/backend/automata` package and parts of some simulation steppers)
- Donghan:
  - Frontend (Entire visualization package)
  - Some front/back end integration

### Resources Used

For XML file reading: https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html

For window resize listeners: https://blog.idrsolutions.com/2012/11/adding-a-window-resize-listener-to-javafx-scene

For weighted random generation: https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java

For using a Pair-like object with HashMaps:
https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value

Generating Random integers:
https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java

### Running the Program

Main class: `Main`

Data files needed:

- Frontend:
  - property files in `src/cell_society/visualization/resources/properties/languages` for each supported language
  - property files in `src/cell_society/visualization/resources/properties/simulationColorCodes` for each simulation; responsible for storing the names, colors, and outlines for the cells of each simulation
  - CSS files in `src/cell_society/visualization/resources/stylesheets` for each supported component style

- Backend:
  - Configuration files in `data/config_files`, responsible for containing all the information necessary for loading in a simulationo
  - Default value files in `data/default_values`, responsible for assigning default values if none are given and deciding the range of sliders for the front end
  - To implement cell rules, the corresponding cell classes and patches are stored in `backend/automata/simulation_name`

- XML Configuration
  - Each XML file has a "coreSpecifications" tag that has attributes and the grid type, stepper type, and other details necessary to the simulation
  - Also a gridInfo tag. Inside this tag is where grids and patches are declared. The row and column is the same for all grids and patches
    - Each grid is what the cells have. It is defined by the type, grid state (layout of cells), randoom probabilities, codes, decode values, and any parameters
    - If "Random" is put in place of an initial grid configuration, the program will default to a randoom configuration based on the random probabilities established in randomoProbs
    - There can be many patches initialized. Pathces are used to declare values that aren't a part of cells but rather a feature of a location on the grid. They are implemented in essentially the same way as the grid.

Features implemented:
- Simulation:
  - All 5 of the Basic simulations have been implemented, and 3 out of the 4 simulations from the complete have backend support.
  - Implemented all three of the Grid edge types, but we discovered that these are hard-coded into the `SimulationStepper` functions (although the initial Grid type is configurable, it quickly becomes overwritten).  The `ToroidalGrid` is included for Wa-Tor, and both it and the standard grid are most easily seen and are persistent during simulation runs, but the `InfiniteGrid` can be added in the stepper.
  - Implemented all three cell shape types with various neighbors and adjacent cell definitions.
  - Grid edge types and cell shapes are interchangable.
  - A random configuration generator that allows the user to specify probabilities of each cell type, as well as default parameters if the user doesn't specify cell parameters


- Configuration:
  - Error handling that allows the user to select another file if an error message is displayed; accounts for the following errors:
    - WrongType: When the wrong file type is given
    - CoreGridSpecification: Stepper, grid type, or cell structure is missing
    - IncorrectGridSpecification: Rows/columns and number of cells has a discrepancy
    - InvalidCellMapping: No cell mapping for a specific character
    - CoreSpecificationError: Can't initialize the simulatioon without core specificatioons
    - InvalidCellType: Cell encoding class type is not correct
    - InvalidGridType = Invalid grid type specified
    - InvalidStepperType: Invalid stepper type specified
    - InvalidStructureType: Invalid structure type specified
    - InvalidPatchType: Invalid patch type specified
  
  - The following aspects of the simulation can be styled, either through user input or files
    - Language used for the simulation
    - Shape of grid with appropriate neighbor checking (rectangle, hexagon, triangle)
    - Whether or not grid locations should be outlined
    - Color of cell or patch states


- Visualization:
  - Implemented buttons to pause/resume, step forward, and change the speed of the simulation
  - Implemented button to load a new configuration file to run a new simulation
  - Implemented button to open a new simulation window to allow users to run multiple different simulations simultaneously
  - Implemented two display views (typical cell grid & pie graph of populations of all types of cells) that can be shown/hidden independently of each other by the user; both views are synced, meaning turning one on in the middle of the simulation will still have accurate data in the current time
    - Both display views are dynamically scaled when the simulation window is resized at any point in time
  - Grid view can be displayed with cells of different shapes (supports rectangles, hexagons, and triangles)
  - All simulations that have adjustable parameters will show sliders that allow users to change each parameter independently at any point in time while the simulation is running
  - The appearance of the UI components can be changed by the user at any point in time (supports dark mode, light mode, and colorful mode)
  - The language of the text displayed in the simulation can be changed by the user at any point in time (supports English, Spanish, and French)


### Notes/Assumptions

Assumptions or Simplifications:
- Due to the fact that we realized the negative aspects of downcasting, the user has to act in good faith when declaring a stepper in the XML file. A declaration of the wrong stepper type will result in type casting errors
- Patch data can be unnecessarily declared and stored and there is nothing to stop a user from doing this
- *Foraging Ants:* Ants replicate the ability to hold a direction and to move in one of two directions, but if neither of those spots are valid, they stay in place and switch directions without moving for a turn.  Additionally, ants are initialized with random directions instead of away/towards the Cell.
- *Sugar Scape:* Agents are limited to a vision of 1, but this can be changed by editing the enums implementing `Direction` to scale the direction vectors.
- All six directions of a hexagon are considered adjacent, rather than a strictly smaller subset, like with square cells.

Interesting data files:
- `game_of_life/gliderGun.xml`
- `rock_paper_scissors/randomAllFiveTest.xml`
- `percolation/centerSpread.xml`

Known Bugs:
- The `NestCell` of the *Foraging Ants* simulation sporadically disappears.
- Not all errors with the XML file are properly caught


Extra credit:

- Continuous localization support; the language of the simulation can be changed by the user at any point in time (doesn't have to be set before a simulation view is created)
- Continuous responsive UI; both the display grid & graph dynamically changes size when the application window is resized at any time
  - When both the grid & graph display views are open, they each resize dynamically to fit both inside the simulation window
- All visualization related styling (including the cell colors, names, and outlines for each simulation) can be easily changed via property files
- We designed the `Grid` and `Cell`s to create shallow copies each time because we anticipated having the ability to store and refer to previous versions of the `Grid`.  this can be easily implemented in the `Simulation` class.

### Impressions

- We all feel that the project went pretty well overall. Although we didn't necessary hit every design goal, we felt that we maintained a pretty good program structure while being able to implement a large number of features
  - The package organization was pretty good
  - We tried to generalize behavior as much as possible in the back end. Although there were some small mistakes, we feel that our design allows the user to add a new simulation without too large of a hassle
  - Some overhead is necessary to create cells, but we felt that this allowed the responsibilities to be clearly distributed and allowed for a uniform way of adding new cells.  Both *Rock, Paper, Scissors* and *Segregation* should accommodate even more types and agents with ease.
  - The way the XML file is read in allows it to be expanded to include more details about the grid
  - The front end has a lot of features that allow for pretty good control over the simulation.
- Simulation rules could be vague or different between sources, but this also provided some room for interpretation.
- Enjoyable project to work on, but was sometimes tough because of all the moving components.  

