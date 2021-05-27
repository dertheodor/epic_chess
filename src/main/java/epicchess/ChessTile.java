package epicchess;

public class ChessTile {
    TileState tileState;
    ChessPiece currentPiece;

    public ChessTile() {
        tileState = TileState.FREE;
    }

    /**
     * Setter method for chessPiece
     *
     * @param chessPiece the new chessPiece
     */
    public void setCurrentPiece(ChessPiece chessPiece) {
        currentPiece = chessPiece;
    }

    /**
     * Getter method for currentPiece
     *
     * @return the currentPiece
     */
    public ChessPiece getCurrentFigure() {
        return currentPiece;
    }
}
