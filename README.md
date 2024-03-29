# CENG453 CATAN GAME

This folder contains the frontend client application of CATAN. It was developed using Spring Boot and JavaFX

## Requirements

- JDK 21
- Maven

## Build and Run

## How to Play

1. Sign up (if you do not have an account)
2. Sign in using your e-mail and password
3. The game will automatically start.
4. In the beginning player settlements and roads are placed randomly.
5. Each player starts with 3 bricks, 1 grain, 3 lumber, 0 ore and 1 wool.
6. Each player receives sources from their adjacent tiles depending on color of the tile.
7. The human player is "Player 1". "Player 2", "Player 3" and "Player 4" are controlled by AI.
8. To roll the dice, click on dice. The total number is shown below the dices. 
If the dice total is on a tile, depending on the color of that tile, 
the source is distributed to the player that are adjacent to that tile.
If the dice total is 7, no source are distributed.   

   - If the tile is brown, "BRICK" resource is distributed.
   - If the tile is dark green, "LUMBER" resource is distributed.
   - If the tile is yellow, "GRAIN" resource is distributed.
   - If the tile is grey, "ORE" resource is distributed.
   - If the tile is light green, "WOOL" resource is distributed.

9. Click on one of the possible empty roads to build road.
You can build roads adjacent to your existing roads, settlements or cities. To build road
you need 1 lumber and 1 brick.
10. Click on one of the possible empty settlement place to build settlement.
You can build settlement if you have a road adjacent to that empty settlement place and if that place
is at least 2 roads away from any other settlement.
11. Click on one of the owned settlements to upgrade the settlement to a city.
12. When you want to end your turn, click "End Turn".
13. - Each settlement worth 1 point.
    - Each city worth 2 points.
    - Having longest path worth 2 points.
    - If a player reaches 8 points, the game ends.
14. If you want to play again, click "Play Again".

## AI Strategy
If AI player has enough resources to build a road, it randomly builds a road. Then,
if AI player has enough resources to build a settlement, it randomly builds a settlement. Then,
if AI player has enough resources to build a city, it randomly builds a city. AI turn ends after this.



## TurnControllers
We created PlayerTurnController abstract class to implement Ai and Human turn implementations.
AiTurnController implements decision making of Ai players. HumanTurnController implements visibility of available buttons to Human players.

![alt text](/docs/img/TurnControllers.png)


## Singleton Pattern
Singleton Pattern is used to easily inject controllers between each other. It made our workplace simply controllable and prevented classes from being taught twice.