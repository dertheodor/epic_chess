package epicchess;

public class ChessTile {
    TileState tileState;
    private ChessPiece currentPiece;

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
        if (currentPiece.color.equals("black")) {
            tileState = TileState.BLACK;
        } else if (currentPiece.color.equals("white")) {
            tileState = TileState.WHITE;
        }
    }

    /**
     * Removes the Current Piece from this Tile and sets the TileState to free.
     */
    public void removeCurrentPiece() {
        currentPiece = null;
        tileState = TileState.FREE;
    }

    /**
     * Getter method for currentPiece
     *
     * @return the currentPiece
     */
    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }
}
