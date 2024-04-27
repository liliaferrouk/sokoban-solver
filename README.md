# sokoban-solver
An AI-powered Sokoban solver using the A* algorithm. This project features an efficient approach to solving Sokoban puzzles, optimizing for the best path and strategy.


## Features

- Utilizes the A* algorithm to find the optimal path in solving Sokoban puzzles.
- Calculates the heuristic based on the distance between the pusher, boxes, and goals.

## Getting Started

To get started with the project:
1. Clone the repository:
```shell
git clone https://github.com/liliaferrouk/sokoban-solver.git
```
2. Navigate to the project directory:
```shell
cd sokoban-solver
```
3. Compile and run the application:
```shell
javac -d bin **/*.java
java -cp bin Sokoban
```

## How to Use
- Manual Control: Navigate the pusher manually using the keyboard controls to solve the level.
- AI Control: Press the I key to activate the AI solver, which uses the A* algorithm to find the optimal path and solve the level for you.
- Pause and Resume: You can stop the AI solver at any time by pressing the I key again, allowing you to play manually. To resume AI control, press the I key once more.


## Acknowledgements
This project was originally developed as part of a university course project at the University of Grenoble Alpes by Guillaume Huard. Thank you to the university and professors for their guidance and support.

Ferrouk Lilia a third-year Computer Science student at the University of Grenoble Alpes
