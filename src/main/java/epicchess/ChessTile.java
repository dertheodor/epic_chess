package epicchess;

public class ChessTile {
    private TileState tileState;
    private ChessPiece currentPiece;

    public ChessTile() {
        tileState = TileState.FREE;
    }

    /**
     * setter method for chessPiece
     *
     * @param chessPiece the new chessPiece
     */
    public void setCurrentPiece(ChessPiece chessPiece) {
        currentPiece = chessPiece;
        if (currentPiece.getColor().equals("black")) {
            tileState = TileState.BLACK;
        } else if (currentPiece.getColor().equals("white")) {
            tileState = TileState.WHITE;
        }
    }

    /**
     * getter method for tileState
     *
     * @return tileState
     */
    public TileState getTileState() {
        return tileState;
    }

    /**
     * Removes the Current Piece from this Tile and sets the TileState to free.
     */
    public void removeCurrentPiece() {
        currentPiece = null;
        tileState = TileState.FREE;
    }

    /**
     * getter method for currentPiece
     *
     * @return the currentPiece
     */
    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }
}
