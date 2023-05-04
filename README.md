# CS-5800: Vending Machine Assignment

This repository contains an implementation of simulated vending machine software for CS-5800 (Advanced Software Engineering).
Essentially, the state design pattern is used to manage the different behaviors required for the state of the vending machine.
Additionally, the chain of responsibility design pattern is leveraged to actually perform the dispensing of items.
The `driver` package implements a basic driver program demonstrating the instantiation and usage of the different classes implemented here. 
An example of its output can be found in the `output.png` file.

## Build & Run

To build with Maven, simply navigate to the project root in the command line and run:

```shell
mvn package
```

Alternatively, IDEs such as IntelliJ should be able to build the source files using their standard build utilities.

Once built, the project can be run using the `driver` package:

```shell
java -cp ./target/vending-machine-assignment-1.0-SNAPSHOT.jar driver.Main
```