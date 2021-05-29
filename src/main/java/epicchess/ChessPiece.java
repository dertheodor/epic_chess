package epicchess;

public class ChessPiece {
    String color;
    Figure type;
    private final String uniCodePicture;
    String figureID;
    private boolean movedBefore;

    public ChessPiece(String colorOfPiece, Figure pieceType, String picture, String id) {
        color = colorOfPiece;
        type = pieceType;
        uniCodePicture = picture;
        figureID = id;
        movedBefore = false;
    }

    /**
     * Getter method
     *
     * @return the "image" of the chessPiece as unicode
     */
    public String getUniCodePicture() {
        return uniCodePicture;
    }

    /**
     * getter method for movedBefore
     *
     * @return movedBefore state
     */
    public boolean getMovedBefore() {
        return movedBefore;
    }

    /**
     * sets movedBefore to true
     */
    public void setMovedBeforeTrue() {
        movedBefore = true;
    }
}
