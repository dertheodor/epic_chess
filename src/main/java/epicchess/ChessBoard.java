package epicchess;

import java.util.List;

public class ChessBoard {
    ChessEngine engine;
    ChessTile[][] gameBoard;

    public ChessBoard(ChessEngine engineReference) {
        engine = engineReference;
        // init board
        gameBoard = new ChessTile[8][8];

        // fill board with tiles
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                gameBoard[row][column] = new ChessTile();
            }
        }
    }

    /**
     * initializes all figures on the board
     */
    public void initStartingFigures() {
        // black figures
        gameBoard[0][0].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "A8rook"));
        gameBoard[0][1].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "B8knight"));
        gameBoard[0][2].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "C8bishop"));
        gameBoard[0][3].setCurrentPiece(new ChessPiece("black", Figure.QUEEN, "\u265B", "D8queen"));
        gameBoard[0][4].setCurrentPiece(new ChessPiece("black", Figure.KING, "\u265A", "E8king"));
        gameBoard[0][5].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "F8bishop"));
        gameBoard[0][6].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "G8knight"));
        gameBoard[0][7].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "H8rook"));
        // pawns
        gameBoard[1][0].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "A7pawn"));
        gameBoard[1][1].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "B7pawn"));
        gameBoard[1][2].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "C7pawn"));
        gameBoard[1][3].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "D7pawn"));
        gameBoard[1][4].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "E7pawn"));
        gameBoard[1][5].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "F7pawn"));
        gameBoard[1][6].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "G7pawn"));
        gameBoard[1][7].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "H7pawn"));

        // white figures
        gameBoard[7][0].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "A1rook"));
        gameBoard[7][1].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "B1knight"));
        gameBoard[7][2].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "C1bishop"));
        gameBoard[7][3].setCurrentPiece(new ChessPiece("white", Figure.QUEEN, "\u2655", "D1queen"));
        gameBoard[7][4].setCurrentPiece(new ChessPiece("white", Figure.KING, "\u2654", "E1king"));
        gameBoard[7][5].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "F1bishop"));
        gameBoard[7][6].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "G1knight"));
        gameBoard[7][7].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "H1rook"));
        // pawns
        gameBoard[6][0].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "A7pawn"));
        gameBoard[6][1].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "B7pawn"));
        gameBoard[6][2].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "C7pawn"));
        gameBoard[6][3].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "D7pawn"));
        gameBoard[6][4].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "E7pawn"));
        gameBoard[6][5].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "F7pawn"));
        gameBoard[6][6].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "G7pawn"));
        gameBoard[6][7].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "H7pawn"));
    }

    /**
     * @param row    row of tile
     * @param column column of tile
     * @return the tile on given coordinate
     */
    public ChessTile getTile(int row, int column) {
        return gameBoard[row][column];
    }

    /**
     * @param position of the current to be moved piece
     * @return possible moves
     */
    public List<ArrayPosition> highlightNextValidMoves(ArrayPosition position) {
        return engine.showNextValidMoves(position, gameBoard[position.getRow()][position.getColumn()].getCurrentPiece(), gameBoard);
    }

    /**
     * making the actual move of the piece
     *
     * @param oldPosition old position of the piece
     * @param newPosition new position of the piece
     */
    public void makeMove(ArrayPosition oldPosition, ArrayPosition newPosition) {
        // memorize old piece
        ChessPiece piece = gameBoard[oldPosition.getRow()][oldPosition.getColumn()].getCurrentPiece();
        // remove piece from old position
        gameBoard[oldPosition.getRow()][oldPosition.getColumn()].removeCurrentPiece();
        // set piece to new position
        gameBoard[newPosition.getRow()][newPosition.getColumn()].setCurrentPiece(piece);
    }
}
