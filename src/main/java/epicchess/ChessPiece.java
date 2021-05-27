package epicchess;

public class ChessPiece {
    String color;
    Figure type;
    String uniCodePicture;
    String figureID;

    public ChessPiece(String colorOfPiece, Figure pieceType, String picture, String id) {
        color = colorOfPiece;
        type = pieceType;
        uniCodePicture = picture;
        figureID = id;
    }

    /**
     * Getter method
     *
     * @return the "image" of the chessPiece as unicode
     */
    public String getUniCodePicture() {
        return uniCodePicture;
    }
}
