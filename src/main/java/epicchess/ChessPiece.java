package epicchess;

public class ChessPiece {
    private final String color;
    private final Figure type;
    private final String uniCodePicture;
    private final String figureID;
    private boolean movedBefore;

    public ChessPiece(String colorOfPiece, Figure pieceType, String picture, String id, boolean hasBeenMoved) {
        color = colorOfPiece;
        type = pieceType;
        uniCodePicture = picture;
        figureID = id;
        movedBefore = hasBeenMoved;
    }

    /**
     * getter method for uniCodePicture
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

    /**
     * @return color state
     */
    public String getColor() {
        return color;
    }

    /**
     * @return type
     */
    public Figure getType() {
        return type;
    }

    /**
     * getter method for figureID
     *
     * @return figureID
     */
    public String getFigureID() {
        return figureID;
    }
}
