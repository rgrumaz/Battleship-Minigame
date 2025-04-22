# Battleship
A classic two-player naval strategy game implemented in Java.
## Overview
This program recreates the traditional Battleship game where players strategically place their fleet on a grid and take turns firing at each other's ships. The game combines strategy, memory, and luck as you try to sink all of your opponent's ships before they sink yours.
## Features
- Interactive console-based gameplay
- 10×10 game board
- Five different ships of varying sizes:
    - Aircraft Carrier (5 cells)
    - Battleship (4 cells)
    - Submarine (3 cells)
    - Cruiser (3 cells)
    - Destroyer (2 cells)

- Input validation for ship placement
- Turn-based gameplay with player switching
- Hidden opponent boards
- Visual feedback for hits and misses

## How to Play
1. **Setup Phase**: Each player places their ships on their board
    - Ships can be placed horizontally or vertically
    - Ships cannot overlap or touch each other
    - Input coordinates in the format: `A1 A5` (start and end positions)

2. **Battle Phase**: Players take turns firing at opponent's board
    - Enter a coordinate to fire (e.g., `B5`)
    - The game will display if you hit or missed
    - The game automatically switches between players

3. **Winning**: Sink all your opponent's ships to win

## Game Symbols
- `~` - Empty sea
- `O` - Your ship
- `X` - Hit ship
- `M` - Miss
