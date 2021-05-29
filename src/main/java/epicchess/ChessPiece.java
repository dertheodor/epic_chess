package epicchess;

public class ChessPiece {
    String color;
    Figure type;
    String uniCodePicture;
    String figureID;
    boolean movedBefore;

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

    public void setMovedBeforeTrue() {
        movedBefore = true;
    }
}
