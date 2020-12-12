# ConwayLifeGameJavaFX
> The project contains source code of Conway's Game of Life (https://en.wikipedia.org/wiki/Conway's_Game_of_Life). It is organized as Maven project and is written as GUI application on JavaFX for self-study purposes.

## Prerequisites

JDK 1.8 or higher.
Maven

## Installing / Uninstalling

The project is configured to package executable jar file in target directory. The Maven package goal is executed in a usual fashion: go to the root directory of the project (pom.xml file is located there). Then run:

```shell
> mvn package
```

This will create an executable jar and supporting resources inside target directory (it is ignored by Git). To clean target directory, run:

```shell
> mvn clean
```

## Configuration
You may define number of cells in one row/column, colors for life/dead states, size of cell in resources/properties.xml file.

## Usage
When application starts a user chooses initial configuration on the field by clicking on the corresponding cell. User can undo the action by clicking on the cell again. When user completes initial configuration, 
he presses Start button and the cell evolution begins according to the rules defined in https://en.wikipedia.org/wiki/Conway's_Game_of_Life . 
There is an option to press Stop button to freeze current state and then proceed by pressing the Start button again.

## Contributing

If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcomed.

## Licensing

This project is licensed under Unlicense license. It was created to self-study JavaFX capabilities.
