package battleship;

import java.util.Arrays;

public class GameBoard {
    private final String[][] board;

    public GameBoard() {
        board = new String[10][10];
        for (int i = 0; i < 10; i++) {
            Arrays.fill(board[i], "~");
        }
    }

    public void printBoard() {
        System.out.println();
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int row = 0; row < 10; row++) {
            System.out.print((char) ('A' + row) + " ");
            for (int col = 0; col < 10; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public void printFogBoard() {
        System.out.println();
        System.out.print("  ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int row = 0; row < 10; row++) {
            System.out.print((char) ('A' + row) + " ");
            for (int col = 0; col < 10; col++) {
               String cell = board[row][col];
                if (cell.equals("X") || cell.equals("M")) {
                    System.out.print(cell + " ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
    }

    public void placeShip(int startRow, int endRow, int startCol, int endCol) {
        int fromRow = Math.min(startRow, endRow);
        int toRow = Math.max(startRow, endRow);
        int fromCol = Math.min(startCol, endCol);
        int toCol = Math.max(startCol, endCol);

        if (startRow == endRow) { //horizontal
            for (int col = fromCol; col <= toCol; col++) {
                board[startRow][col] = "O";
            }
        }

        if (startCol == endCol) { //vertical
            for (int row = fromRow; row <= toRow; row++) {
                board[row][startCol] = "O";
            }
        }
    }

    public boolean validCoords(int startRow, int startCol, int endRow, int endCol) {
        if (startRow != endRow && startCol != endCol) {
            System.out.println("Error!");
            return false;
        }
        if (startRow < 0 || startRow > 9 || endRow < 0 || endRow > 9
                || startCol < 0 || startCol > 9 || endCol < 0 || endCol > 9) {
            System.out.println("Error!");
            return false;
        }
        return true;
    }

    public boolean isTooClose(int startRow, int startCol, int endRow, int endCol) {
        int fromRow = Math.min(startRow, endRow);
        int toRow = Math.max(startRow, endRow);
        int fromCol = Math.min(startCol, endCol);
        int toCol = Math.max(startCol, endCol);
        for (int row = fromRow - 1; row <= toRow + 1; row++) {
            for (int col = fromCol - 1; col <= toCol + 1; col++) {
                if (row < 0 || row >= 10 || col < 0 || col >= 10) {
                    continue;
                }
                if (board[row][col].equals("O")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Processes a player's shot at the specified coordinates.
     * Updates the board state and provides feedback on the result.
     *
     * @param row The row coordinate (0-9, where 0 is 'A')
     * @param col The column coordinate (0-9, where 0 is '1')
     * @return true if the shot was valid and hit a ship, false if it missed or was invalid
     */
    public boolean takeShot(int row, int col) {
        if (row < 0 || row >= 10 || col < 0 || col >= 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        String cell = board[row][col];

        switch (cell) {
            case "O" -> {
                markHit(row, col);
                System.out.println("You hit a ship!");
                return true;
            }
            case "~" -> {
                markMiss(row, col);
                System.out.println("You missed!");
                return false;
            }
            case "X", "M" -> {
                System.out.println("You already fired at this cell! Try again:");
                return false;
            }
        }

        return false;
    }

    public int countHits() {
        int count = 0;
        for (String[] strings : board) {
            for (String string : strings) {
                if (string.equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }

    public void markHit(int row, int col) {
        board[row][col] = "X";
    }

    public void markMiss(int row, int col) {
        board[row][col] = "M";
    }

    /**
     * Checks if a ship at the specified coordinates has been completely sunk.
     * A ship is considered sunk when all of its cells have been hit.
     *
     * @param row The row coordinate of the hit cell to check
     * @param col The column coordinate of the hit cell to check
     * @return true if the ship at this position is completely sunk, false otherwise
     */
    public boolean isShipSunk(int row, int col) {
        if (!board[row][col].equals("X")) {
            return false;
        }

        if (isHorizontal(row, col)) {
            //left
            int leftCol = col;
            while (leftCol > 0 && (board[row][leftCol-1].equals("O") || board[row][leftCol-1].equals("X"))) {
                leftCol--;
            }

            // right
            int rightCol = col;
            while (rightCol < 9 && (board[row][rightCol+1].equals("O") || board[row][rightCol+1].equals("X"))) {
                rightCol++;
            }

            for (int c = leftCol; c <= rightCol; c++) {
                if (board[row][c].equals("O")) {
                    return false;
                }
            }
            return true;
        }


        if (isVertical(row, col)) {
            //up
            int topRow = row;
            while (topRow > 0 && (board[topRow-1][col].equals("O") || board[topRow-1][col].equals("X"))) {
                topRow--;
            }

            // down
            int bottomRow = row;
            while (bottomRow < 9 && (board[bottomRow+1][col].equals("O") || board[bottomRow+1][col].equals("X"))) {
                bottomRow++;
            }

            for (int r = topRow; r <= bottomRow; r++) {
                if (board[r][col].equals("O")) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public boolean isHorizontal(int row, int col) {
        //left
        int c = col - 1;
        while (c >= 0 && (board[row][c].equals("O") || board[row][c].equals("X"))) {
            if (board[row][c].equals("O")) return false;
            c--;
        }

        //right
        c = col + 1;
        while (c <= 9 && (board[row][c].equals("O") || board[row][c].equals("X"))) {
            if (board[row][c].equals("O")) return false;
            c++;
        }

        return true;
    }

    public boolean isVertical(int row, int col) {
        //up
        int r = row - 1;
        while (r >= 0 && (board[r][col].equals("O") || board[r][col].equals("X"))) {
            if (board[r][col].equals("O")) return false;
            r--;
        }

        //down
        r = row + 1;
        while ((r <= 9 && (board[r][col].equals("O") || board[r][col].equals("X")))) {
            if (board[r][col].equals("O")) return false;
            r++;
        }

        return true;
    }
}
