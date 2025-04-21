package battleship;

import java.util.Scanner;

/**
 * Main game controller that manages player interaction, game flow, and turn-based gameplay.
 * Handles ship placement, turn transitions, and processing player actions in a Battleship game.
 */

public class Game {
    private final GameBoard player1Board = new GameBoard();
    private final GameBoard player2Board = new GameBoard();
    private final Scanner scanner = new Scanner(System.in);

    private void nextPlayerPrompt() {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

    public void placeFleet(GameBoard board, Ship[] fleet) {
        for (Ship ship : fleet) {
            while (true) {
                try {
                    System.out.println();
                    System.out.println("Enter the coordinates of the " + ship.getShipName() + " (" + ship.getLength() + " cells):");

                    String[] coords = readCoords();
                    int[] parsed = parseCoords(coords);

                    int startRow = parsed[0];
                    int startCol = parsed[1];
                    int endRow = parsed[2];
                    int endCol = parsed[3];

                    if (!board.validCoords(startRow, startCol, endRow, endCol)) {
                        System.out.println("Error!");
                        continue;
                    }
                    if (!ship.isCorrectLength(startRow, startCol, endRow, endCol)) {
                        System.out.println("Error! Wrong length of the " + ship.getShipName() + "! Try again:");
                        continue;
                    }
                    if (board.isTooClose(startRow, startCol, endRow, endCol)) {
                        System.out.println("Error!");
                        continue;
                    }

                    ship.deploy(board, startRow, startCol, endRow, endCol);
                    break;

                } catch (Exception e) {
                    System.out.println("Error!");
                }
            }
        }
    }

    public void start() {
        System.out.println("Player 1, place your ships on the game field");
        player1Board.printBoard();
        placeFleet(player1Board, Ship.createFleet());

        nextPlayerPrompt();

        System.out.println("Player 2, place your ships on the game field");
        player2Board.printBoard();
        placeFleet(player2Board, Ship.createFleet());

        nextPlayerPrompt();

        playGame();
    }

    private void playGame() {
        boolean player1Turn = true;
        while (true) {
            GameBoard currentPlayerBoard = player1Turn ? player1Board : player2Board;
            GameBoard opponentBoard = player1Turn ? player2Board : player1Board;

            opponentBoard.printFogBoard();
            System.out.println("---------------------");
            currentPlayerBoard.printBoard();

            System.out.println("Player " + (player1Turn ? "1" : "2") + ", it's your turn:");

            String input = scanner.nextLine();
            int[] coords = parseSingleCoord(input);
            int row = coords[0];
            int col = coords[1];

            boolean isHit = opponentBoard.takeShot(row, col);

            if (isHit) {
                if (opponentBoard.isShipSunk(row, col)) {
                    if (opponentBoard.countHits() == 17) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return;
                    } else {
                        System.out.println("You sank a ship!");
                    }
                }
            }

            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();

            player1Turn = !player1Turn;
        }
    }

    public String[] readCoords() {
        String input = scanner.nextLine();
        return input.toUpperCase().split(" ");
    }

    /**
     * Parses a single coordinate input in the format "A1" to array indices.
     *
     * @param coords The coordinate string in letter-number format (e.g., "A1", "J10")
     * @return An int array where index 0 is the row (0-9) and index 1 is the column (0-9)
     */
    public int[] parseCoords(String[] coords) {
        String start = coords[0];
        String end = coords[1];
        int startRow = start.charAt(0) - 'A'; // Letters
        int startCol = Integer.parseInt(start.substring(1)) - 1; // Numbers
        int endRow = end.charAt(0) - 'A';
        int endCol = Integer.parseInt(end.substring(1)) - 1;
        return new int[]{startRow, startCol, endRow, endCol};
    }

    public int[] parseSingleCoord(String input) {
        int row = input.charAt(0) - 'A';
        int col = Integer.parseInt(input.substring(1)) - 1;
        return new int[]{row, col};
    }
}
