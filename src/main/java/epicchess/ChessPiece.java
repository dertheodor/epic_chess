package epicchess;

public class ChessPiece {
    boolean color;
    Figure type;
    String uniCodePicture;

    public ChessPiece(boolean colorOfPiece, Figure pieceType, String picture) {
        color = colorOfPiece;
        type = pieceType;
        uniCodePicture = picture;
    }


}
