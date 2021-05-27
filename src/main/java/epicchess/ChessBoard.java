package epicchess;

public class ChessBoard {
    ChessTile[][] gameBoard;

    public ChessBoard() {
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
        // TODO init all figures
        // black figures
        gameBoard[0][0].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "A8rook"));

        // white figures
    }

    /**
     * @param row    row of tile
     * @param column column of tile
     * @return the tile on given coordinate
     */
    public ChessTile getTile(int row, int column) {
        return gameBoard[row][column];
    }
}
