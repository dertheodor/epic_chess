package epicchess;

public class ArrayPosition {
    private int row;
    private int column;
    private boolean isOccupied;

    public ArrayPosition(int rowPos, int columnPos, boolean occupation) {
        row = rowPos;
        column = columnPos;
        isOccupied = occupation;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public boolean equals(ArrayPosition otherPosition) {
        if (row == otherPosition.getRow() && column == otherPosition.getColumn() && isOccupied == otherPosition.getIsOccupied()) {
            return true;
        }
        return false;
    }
}
