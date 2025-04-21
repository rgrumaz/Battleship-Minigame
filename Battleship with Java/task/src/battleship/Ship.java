package battleship;

public class Ship {
    private static final int AIRCRAFT_CARRIER_LENGTH = 5;
    private static final int BATTLESHIP_LENGTH = 4;
    private static final int SUBMARINE_LENGTH = 3;
    private static final int CRUISER_LENGTH = 3;
    private static final int DESTROYER_LENGTH = 2;

    private final String shipName;
    private final int length;

    public Ship(String shipName, int length) {
        this.shipName = shipName;
        this.length = length;
    }

    public static Ship[] createFleet() {
        return new Ship[] {
                new Ship("Aircraft Carrier", AIRCRAFT_CARRIER_LENGTH),
                new Ship("Battleship", BATTLESHIP_LENGTH),
                new Ship("Submarine", SUBMARINE_LENGTH),
                new Ship("Cruiser", CRUISER_LENGTH),
                new Ship("Destroyer", DESTROYER_LENGTH)

        };
    }

    public void deploy(GameBoard board, int startRow, int startCol, int endRow, int endCol) {
            board.placeShip(startRow, endRow, startCol, endCol);
            board.printBoard();
    }

    boolean isCorrectLength(int startRow, int startCol, int endRow, int endCol) {
        int fromRow = Math.min(startRow, endRow);
        int toRow = Math.max(startRow, endRow);
        int fromCol = Math.min(startCol, endCol);
        int toCol = Math.max(startCol, endCol);
        if (fromRow == toRow) {
            return (toCol - fromCol + 1) == length;
        } else if (fromCol == toCol) {
            return (toRow - fromRow + 1) == length;
        }
        return false;
    }

    public String getShipName() {
        return shipName;
    }

    public int getLength() {
        return length;
    }
}
