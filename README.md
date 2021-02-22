Cell Society
====

This project implements a cellular automata simulator.

Names: Donghan Park (dp239), George Hong (grh17), Casey Szilagyi (crs79)

### Timeline

Start Date: 2/8/2021

Finish Date: 2/22/2021

Hours Spent:
- Casey: 50
- George: 25
- Donghan: 41

### Primary Roles
- Casey:
    - XML
    - Front/Back end integration
- George:
    - Backend
- Donghan:
    - Frontend

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
  - All config files stored in data/config_files
  - Default values stored in data/default_values
  - src/cell_society/visualization/resources has all the properties and stylesheets for styling

Features implemented:
  - Implemented the 5 basic simulation types and 3 of the complete. All of these are able
to have different shapes so that they behave differently
    
  - A random configuration generator that allows the user to specify probabilities of 
each cell type, as well as default parameters if the user doesn't specify cell parameters
    
  - Pause, play, change speed buttons as well as a new simulation loader and a
cell proportion graph. Also have style changing abilities.
    
  - Sliders to change cell parameters during the completion of the simulation.

### Notes/Assumptions

Assumptions or Simplifications:
  - Due to the fact that we realized the negative aspects of downcasting, the user has
to act in good faith when making XML data

Interesting data files:
- game_of_life/gliderGun.xml
- rock_paper_scissors/randomAllFiveTest.xml

Known Bugs:


Extra credit:

### Impressions

