package epicchess;

public class ArrayPosition {
    private int row;
    private int column;

    public ArrayPosition(int rowPos, int columnPos) {
        row = rowPos;
        column = columnPos;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
