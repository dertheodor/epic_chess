package epicchess;


import java.util.ArrayList;
import java.util.List;

public class ChessEngine {

    /**
     * Tests which Piece wants to move and correctly calls the ...Movement method of Piece.
     *
     * @param position  The Position of the currently selected Piece.
     * @param piece     The Piece which is currently selected.
     * @param gameBoard The current State of our Gameboard.
     * @return A list with all the possible moves for the Selected Piece.
     */
    public List<ArrayPosition> showNextValidMoves(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList;
        validMovesList = new ArrayList<>();
        if (piece.getType() == Figure.PAWN) {
            if (piece.getColor().equals("black")) {
                validMovesList = blackPawnMovement(position, piece, gameBoard);
            } else {
                validMovesList = whitePawnMovement(position, piece, gameBoard);
            }

        } else if (piece.getType() == Figure.KNIGHT) {
            validMovesList = knightMovement(position, piece, gameBoard);
        } else if (piece.getType() == Figure.ROOK) {
            validMovesList = rookMovement(position, piece, gameBoard);
        }
        return validMovesList;
    }

    //TODO fix it so pawns cant move past the border of the gameBoard
    private List<ArrayPosition> whitePawnMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        //check if pawn has moved before
        if (!piece.getMovedBefore()) {
            //if pawn has not moved before and both of the tiles in front of him/her are free he/she can move two tiles at once.
            if (gameBoard[position.getRow() - 2][position.getColumn()].getTileState() == TileState.FREE &&
                    gameBoard[position.getRow() - 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() - 2, position.getColumn(), false));
            }
            if (gameBoard[position.getRow() - 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() - 1, position.getColumn(), false));
            }
            //Checking if the Pawn can capture anything
            validMovesList.addAll(whitePawnCapturing(position, gameBoard));
            //pawn has moved now so he/she will never be able to move two tiles at once again.
        } else {
            if (gameBoard[position.getRow() - 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() - 1, position.getColumn(), false));
            }
            //Checking if the Pawn can capture anything
            validMovesList.addAll(whitePawnCapturing(position, gameBoard));
        }
        return validMovesList;
    }

    private List<ArrayPosition> blackPawnMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        //check if pawn has moved before
        if (!piece.getMovedBefore()) {
            //if pawn has not moved before and both of the tiles in front of him/her are free he/she can move two tiles at once.
            if (gameBoard[position.getRow() + 2][position.getColumn()].getTileState() == TileState.FREE &&
                    gameBoard[position.getRow() + 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() + 2, position.getColumn(), false));
            }
            if (gameBoard[position.getRow() + 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() + 1, position.getColumn(), false));
            }
            //Checking if the Pawn can capture anything
            validMovesList.addAll(blackPawnCapturing(position, gameBoard));
            //pawn has moved now so he/she will never be able to move two tiles at once again.
        } else {
            if (gameBoard[position.getRow() + 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() + 1, position.getColumn(), false));
            }
            //Checking if the Pawn can capture anything
            validMovesList.addAll(blackPawnCapturing(position, gameBoard));
        }
        return validMovesList;
    }

    /**
     * @param position  the Position of the Pawn
     * @param gameBoard the current GameState
     * @return all the Positions of the Pieces the Pawn could possibly capture.
     */
    private List<ArrayPosition> whitePawnCapturing(ArrayPosition position, ChessTile[][] gameBoard) {
        List<ArrayPosition> validCaptureMovesList = new ArrayList<>();
        //Capturing with Pawn to the Left.
        if (position.getColumn() - 1 >= 0) {
            if (gameBoard[position.getRow() - 1][position.getColumn() - 1].getTileState() == TileState.BLACK) {
                validCaptureMovesList.add(new ArrayPosition(position.getRow() - 1, position.getColumn() - 1, true));
            }
        }
        //Capturing with Pawn to the Right.
        if (position.getColumn() + 1 <= 7) {
            if (gameBoard[position.getRow() - 1][position.getColumn() + 1].getTileState() == TileState.BLACK) {
                validCaptureMovesList.add(new ArrayPosition(position.getRow() - 1, position.getColumn() + 1, true));
            }
        }
        return validCaptureMovesList;
    }

    /**
     * @param position  the Position of the Pawn
     * @param gameBoard the current GameState
     * @return all the Positions of the Pieces the Pawn could possibly capture.
     */
    private List<ArrayPosition> blackPawnCapturing(ArrayPosition position, ChessTile[][] gameBoard) {
        List<ArrayPosition> validCaptureMovesList = new ArrayList<>();
        //Capturing with Pawn to the Left.
        if (position.getColumn() - 1 >= 0) {
            if (gameBoard[position.getRow() + 1][position.getColumn() - 1].getTileState() == TileState.WHITE) {
                validCaptureMovesList.add(new ArrayPosition(position.getRow() + 1, position.getColumn() - 1, true));
            }
        }
        //Capturing with Pawn to the Right.
        if (position.getColumn() + 1 <= 7) {
            if (gameBoard[position.getRow() + 1][position.getColumn() + 1].getTileState() == TileState.WHITE) {
                validCaptureMovesList.add(new ArrayPosition(position.getRow() + 1, position.getColumn() + 1, true));
            }
        }
        return validCaptureMovesList;
    }


    private List<ArrayPosition> knightMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        int column = position.getColumn();
        int row = position.getRow();
        String pieceColor = piece.getColor();

        //test if move is out of bounds
        if (row >= 2 && column >= 1) {
            //moving
            if (gameBoard[row - 2][column - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 2, column - 1, false));
                //capturing
            } else if (gameBoard[row - 2][column - 1].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row - 2][column - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 2, column - 1, true));
            }
        }
        //test if move is out of bounds
        if (row >= 2 && column <= 6) {
            //moving
            if (gameBoard[row - 2][column + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 2, column + 1, false));
                //capturing
            } else if (gameBoard[row - 2][column + 1].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row - 2][column + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 2, column + 1, true));
            }
        }
        //test if move is out of bounds
        if (row <= 5 && column >= 1) {
            //moving
            if (gameBoard[row + 2][column - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 2, column - 1, false));
                //capturing
            } else if (gameBoard[row + 2][column - 1].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row + 2][column - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 2, column - 1, true));
            }
        }

        //test if move is out of bounds
        if (row <= 5 && column <= 6) {
            //moving
            if (gameBoard[row + 2][column + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 2, column + 1, false));
                //capturing
            } else if (gameBoard[row + 2][column + 1].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row + 2][column + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 2, column + 1, true));
            }
        }

        //test if move is out of bounds
        if (row >= 1 && column >= 2) {
            //moving
            if (gameBoard[row - 1][column - 2].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 1, column - 2, false));
                //capturing
            } else if (gameBoard[row - 1][column - 2].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row - 1][column - 2].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 1, column - 2, true));
            }
        }

        //test if move is out of bounds
        if (row <= 6 && column >= 2) {
            //moving
            if (gameBoard[row + 1][column - 2].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 1, column - 2, false));
                //capturing
            } else if (gameBoard[row + 1][column - 2].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row + 1][column - 2].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 1, column - 2, true));
            }
        }

        //test if move is out of bounds
        if (row >= 1 && column <= 5) {
            //moving
            if (gameBoard[row - 1][column + 2].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 1, column + 2, false));
                //capturing
            } else if (gameBoard[row - 1][column + 2].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row - 1][column + 2].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 1, column + 2, true));
            }
        }

        //test if move is out of bounds
        if (row <= 6 && column <= 5) {
            //moving
            if (gameBoard[row + 1][column + 2].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 1, column + 2, false));
                //capturing
            } else if (gameBoard[row + 1][column + 2].getTileState() == TileState.BLACK && pieceColor.equals("white")
                    || gameBoard[row + 1][column + 2].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 1, column + 2, true));
            }
        }
        return validMovesList;
    }

    private List<ArrayPosition> rookMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        int row = position.getRow();
        int column = position.getColumn();
        String pieceColor = piece.getColor();

        for (int i = row; i < 7; i++) {
            //moving
            if (gameBoard[i + 1][column].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(i + 1, column, false));
                //capturing
            } else if (gameBoard[i + 1][column].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[i + 1][column].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(i + 1, column, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[i + 1][column].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[i + 1][column].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        for (int i = row; i > 0; i--) {
            //moving
            if (gameBoard[i - 1][column].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(i - 1, column, false));
                //capturing
            } else if (gameBoard[i - 1][column].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[i - 1][column].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(i - 1, column, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[i - 1][column].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[i - 1][column].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        for (int i = column; i < 7; i++) {
            //moving
            if (gameBoard[row][i + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row, i + 1, false));
                //capturing
            } else if (gameBoard[row][i + 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row][i + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row, i + 1, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[row][i + 1].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[row][i + 1].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        for (int i = column; i > 0; i--) {
            //moving
            if (gameBoard[row][i - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row, i - 1, false));
                //capturing
            } else if (gameBoard[row][i - 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row][i - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row, i - 1, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[row][i - 1].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[row][i - 1].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        return validMovesList;
    }

}
