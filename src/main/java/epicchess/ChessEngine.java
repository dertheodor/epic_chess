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
        // pawn movement
        if (piece.getType() == Figure.PAWN) {
            if (piece.getColor().equals("black")) {
                validMovesList = blackPawnMovement(position, piece, gameBoard);
            } else {
                validMovesList = whitePawnMovement(position, piece, gameBoard);
            }

            // knight movement
        } else if (piece.getType() == Figure.KNIGHT) {
            validMovesList = knightMovement(position, piece, gameBoard);
            // rook movement
        } else if (piece.getType() == Figure.ROOK) {
            validMovesList = rookMovement(position, piece, gameBoard);
            // bishop movement
        } else if (piece.getType() == Figure.BISHOP) {
            validMovesList = bishopMovement(position, piece, gameBoard);
            // queen movement
        } else if (piece.getType() == Figure.QUEEN) {
            // queen is a mix of rook and bishop so she can use their methods
            validMovesList = rookMovement(position, piece, gameBoard);
            validMovesList.addAll(bishopMovement(position, piece, gameBoard));
            // king movement
        } else if (piece.getType() == Figure.KING) {
            validMovesList = kingMovement(position, piece, gameBoard);
        }

        // check if next move would result in checking own king
        validMovesList = eradicateSelfChecking(position, piece, gameBoard, validMovesList);
        return validMovesList;
    }

    /**
     * movement logic for white pawns
     *
     * @param position  current position of the white pawn
     * @param piece     pawn
     * @param gameBoard gameBoard
     * @return validMovesList - list of valid moves
     */
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

    /**
     * movement logic for black pawns
     *
     * @param position  current position of the black pawn
     * @param piece     pawn
     * @param gameBoard gameBoard
     * @return validMovesList - list of valid moves
     */
    //TODO fix it so pawns cant move past the border of the gameBoard
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


    /**
     * movement logic for knights
     *
     * @param position  current position of the knight
     * @param piece     knight
     * @param gameBoard gameBoard
     * @return validMovesList - list of valid moves
     */
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

    /**
     * movement logic for rooks
     *
     * @param position  current position of the rook
     * @param piece     rook
     * @param gameBoard gameBoard
     * @return validMovesList - list of valid moves
     */
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

    /**
     * movement logic for bishops
     *
     * @param position  current position of the bishop
     * @param piece     bishop
     * @param gameBoard gameBoard
     * @return validMovesList - list of valid moves
     */
    private List<ArrayPosition> bishopMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        int row = position.getRow();
        int column = position.getColumn();
        String pieceColor = piece.getColor();

        //loop for right downwards diagonal movement
        for (int r = row, c = column; r < 7 && c < 7; r++, c++) {
            //moving
            if (gameBoard[r + 1][c + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(r + 1, c + 1, false));
                //capturing
            } else if (gameBoard[r + 1][c + 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[r + 1][c + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(r + 1, c + 1, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[r + 1][c + 1].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[r + 1][c + 1].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        //loop for right upwards diagonal movement
        for (int r = row, c = column; r < 7 && c > 0; r++, c--) {
            //moving
            if (gameBoard[r + 1][c - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(r + 1, c - 1, false));
                //capturing
            } else if (gameBoard[r + 1][c - 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[r + 1][c - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(r + 1, c - 1, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[r + 1][c - 1].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[r + 1][c - 1].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        //loop for left downwards movement
        for (int r = row, c = column; r > 0 && c < 7; r--, c++) {
            //moving
            if (gameBoard[r - 1][c + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(r - 1, c + 1, false));
                //capturing
            } else if (gameBoard[r - 1][c + 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[r - 1][c + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(r - 1, c + 1, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[r - 1][c + 1].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[r - 1][c + 1].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        //loop for left upwards diagonal movement
        for (int r = row, c = column; r > 0 && c > 0; r--, c--) {
            //moving
            if (gameBoard[r - 1][c - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(r - 1, c - 1, false));
                //capturing
            } else if (gameBoard[r - 1][c - 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[r - 1][c - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(r - 1, c - 1, true));
                break;
                //can not move past friendly piece
            } else if (gameBoard[r - 1][c - 1].getTileState() == TileState.BLACK && pieceColor.equals("black") ||
                    gameBoard[r - 1][c - 1].getTileState() == TileState.WHITE && pieceColor.equals("white")) {
                break;
            }
        }

        return validMovesList;
    }

    /**
     * movement Logic for Kings
     *
     * @param position  the current position of the King
     * @param piece     the King
     * @param gameBoard the current State of our Game
     * @return all the possible moves of the King
     */
    private List<ArrayPosition> kingMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        int row = position.getRow();
        int column = position.getColumn();
        String pieceColor = piece.getColor();

        //left upwards diagonal move
        if (row > 0 && column > 0) {
            if (gameBoard[row - 1][column - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 1, column - 1, false));
            }//capturing in the same direction
            else if (gameBoard[row - 1][column - 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row - 1][column - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 1, column - 1, true));
            }
        }

        //straight upwards move
        if (row > 0) {
            if (gameBoard[row - 1][column].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 1, column, false));
            }//capturing in the same direction
            else if (gameBoard[row - 1][column].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row - 1][column].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 1, column, true));
            }
        }

        //right upwards diagonal move
        if (row > 0 && column < 7) {
            if (gameBoard[row - 1][column + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row - 1, column + 1, false));
            }//capturing in the same direction
            else if (gameBoard[row - 1][column + 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row - 1][column + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row - 1, column + 1, true));
            }
        }

        //straight right move
        if (column < 7) {
            if (gameBoard[row][column + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row, column + 1, false));
            }//capturing in the same direction
            else if (gameBoard[row][column + 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row][column + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row, column + 1, true));
            }
        }

        //right downwards diagonal move
        if (row < 7 && column < 7) {
            if (gameBoard[row + 1][column + 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 1, column + 1, false));
            }//capturing in the same direction
            else if (gameBoard[row + 1][column + 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row + 1][column + 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 1, column + 1, true));
            }
        }

        //straight down move
        if (row < 7) {
            if (gameBoard[row + 1][column].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 1, column, false));
            }//capturing in the same direction
            else if (gameBoard[row + 1][column].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row + 1][column].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 1, column, true));
            }
        }

        //left downwards diagonal move
        if (row < 7 && column > 0) {
            if (gameBoard[row + 1][column - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row + 1, column - 1, false));
            }//capturing in the same direction
            else if (gameBoard[row + 1][column - 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row + 1][column - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row + 1, column - 1, true));
            }
        }

        //straight left move
        if (column > 0) {
            if (gameBoard[row][column - 1].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(row, column - 1, false));
            }//capturing in the same direction
            else if (gameBoard[row][column - 1].getTileState() == TileState.BLACK && pieceColor.equals("white") ||
                    gameBoard[row][column - 1].getTileState() == TileState.WHITE && pieceColor.equals("black")) {
                validMovesList.add(new ArrayPosition(row, column - 1, true));
            }
        }

        return validMovesList;
    }

    /**
     * stalemate is being checked by not being able to move anywhere only with the king
     *
     * @param gameBoard the gameboard
     * @param color     color of current player
     * @return true if stalemate, false if checkmate
     */
    public boolean checkForStaleMate(ChessTile[][] gameBoard, String color) {
        // initialize the tile of the king and tile of moving piece
        ArrayPosition tileOfKing = null;
        // initialize the king piece
        ChessPiece king = null;

        // iterate over board to find the king
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // find king
                if (gameBoard[r][c].getTileState() != TileState.FREE) {
                    if (gameBoard[r][c].getCurrentPiece().getType() == Figure.KING &&
                            gameBoard[r][c].getCurrentPiece().getColor().equals(color)) {
                        tileOfKing = new ArrayPosition(r, c, true);
                        king = gameBoard[r][c].getCurrentPiece();
                    }
                }
            }
        }

        // check that we have a king
        if (tileOfKing != null) {
            // create ArrayPosition List only for the king
            List<ArrayPosition> tileOfKingList = new ArrayList<>();
            tileOfKingList.add(tileOfKing);
            // use eradicateSelfChecking to find out whether the game is a stalemate
            if (eradicateSelfChecking(tileOfKing, king, gameBoard, tileOfKingList).size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes the list with all the available moves and deletes those, which would check the King of the Player
     * whose turn it currently is
     *
     * @param tileOfMovingPiece          the Tile where the Piece which is currently moved resides.
     * @param piece                      The Piece which is currently moved.
     * @param gameBoard                  Our current Gameboard state.
     * @param validMovesWithSelfChecking All moves which are available right now, for the piece that wants to move.
     * @return Returns a list with all allowed moves.
     */
    private List<ArrayPosition> eradicateSelfChecking(ArrayPosition tileOfMovingPiece, ChessPiece piece, ChessTile[][] gameBoard, List<ArrayPosition> validMovesWithSelfChecking) {
        // set color of current player
        String colorOfCurrentPlayer = piece.getColor();
        // variable to check if piece that is currently selected is in fact the king
        boolean isKingMoving = false;
        // initialize the tile of the king and tile of moving piece
        ArrayPosition tileOfKing = null;
        // init new validMovesList
        List<ArrayPosition> validMovesList = new ArrayList<>();
        //tests if Piece which is currently selected is in fact the King.
        if (piece.getType() == Figure.KING) {
            // when king is moving, the tile of the moving piece is the tile of the king
            tileOfKing = tileOfMovingPiece;
            // variable set to true for later check
            isKingMoving = true;
        } else {
            // iterate over board to find the king
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    // find king
                    if (gameBoard[r][c].getTileState() != TileState.FREE) {
                        if (gameBoard[r][c].getCurrentPiece().getType() == Figure.KING &&
                                gameBoard[r][c].getCurrentPiece().getColor().equals(colorOfCurrentPlayer)) {
                            tileOfKing = new ArrayPosition(r, c, true);
                        }
                    }
                }
            }
        }

        // check that we have a king
        if (tileOfKing != null) {
            // for every valid move call helper method to find out whether moving piece would result in a check of the own king
            for (ArrayPosition validMove : validMovesWithSelfChecking) {
                // create copy of current board to simulate move
                ChessTile[][] testBoard = new ChessTile[8][8];

                // fill board with tiles
                for (int row = 0; row < 8; row++) {
                    for (int column = 0; column < 8; column++) {
                        testBoard[row][column] = new ChessTile();
                        // copy all contents of actual gameBoard to testBoard
                        if (gameBoard[row][column].getTileState() != TileState.FREE) {
                            testBoard[row][column].setCurrentPiece(gameBoard[row][column].getCurrentPiece());
                        }
                    }
                }

                // remove piece from its current position
                testBoard[tileOfMovingPiece.getRow()][tileOfMovingPiece.getColumn()].removeCurrentPiece();
                // set piece to new possible position
                testBoard[validMove.getRow()][validMove.getColumn()].setCurrentPiece(piece);

                // when the king is moving, use its "new" hypothetical position to eliminate self checking
                if (isKingMoving) {
                    if (isMovePossibleWithoutCheckingOwnKing(validMove.getRow(), validMove.getColumn(), colorOfCurrentPlayer, testBoard)) {
                        validMovesList.add(validMove);
                    }
                    // normal case for all other figures except king
                } else if (isMovePossibleWithoutCheckingOwnKing(tileOfKing.getRow(), tileOfKing.getColumn(), colorOfCurrentPlayer, testBoard)) {
                    validMovesList.add(validMove);
                }
            }
        }
        return validMovesList;
    }

    /**
     * Tests if a move is allowed. It is not if the move checks the King of the Player currently moving.
     *
     * @param row                  The row of the Kings position
     * @param column               The column of the Kings position
     * @param colorOfCurrentPlayer the color of the player whose turn it currently is.
     * @param testBoard            A fictional board, where the move which needs to be checked is made.
     * @return Returns true if the tested move is allowed.
     */
    private boolean isMovePossibleWithoutCheckingOwnKing(int row, int column, String colorOfCurrentPlayer, ChessTile[][] testBoard) {
        // boolean used for eradicating king self-checking with enemy king and pawns
        boolean firstIteration = true;
        // BISHOP AND QUEEN

        //loop for right downwards diagonal movement, imitating the bishop
        for (int r = row, c = column; r < 7 && c < 7; r++, c++) {
            // skip free tiles
            if (testBoard[r + 1][c + 1].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[r + 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[r + 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // for pawns right downwards diagonal
                    if (testBoard[r + 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.PAWN) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[r + 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[r + 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[r + 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[r + 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[r + 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[r + 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r + 1][c + 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        //loop for left downwards diagonal movement, still imitating the bishop
        for (int r = row, c = column; r < 7 && c > 0; r++, c--) {
            // skip free tiles
            if (testBoard[r + 1][c - 1].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[r + 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[r + 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // for pawns left downwards diagonal
                    if (testBoard[r + 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.PAWN) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[r + 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[r + 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[r + 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[r + 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[r + 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[r + 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r + 1][c - 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        //loop for right upwards movement, still imitating the bishop
        for (int r = row, c = column; r > 0 && c < 7; r--, c++) {
            // skip free tiles
            if (testBoard[r - 1][c + 1].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[r - 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[r - 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // for pawns right upwards diagonal
                    if (testBoard[r - 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.PAWN) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[r - 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[r - 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[r - 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[r - 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[r - 1][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[r - 1][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r - 1][c + 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        //loop for left upwards diagonal movement, still imitating the bishop
        for (int r = row, c = column; r > 0 && c > 0; r--, c--) {
            // skip free tiles
            if (testBoard[r - 1][c - 1].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[r - 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[r - 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // for pawns left upwards diagonal
                    if (testBoard[r - 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.PAWN) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[r - 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[r - 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[r - 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[r - 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() != Figure.BISHOP &&
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[r - 1][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[r - 1][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.BISHOP ||
                        testBoard[r - 1][c - 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        // ROOK AND QUEEN

        //loop for left straight movement, imitating the rook
        for (int c = column; c > 0; c--) {
            // skip free tiles
            if (testBoard[row][c - 1].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[row][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[row][c - 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[row][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[row][c - 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[row][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[row][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[row][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[row][c - 1].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[row][c - 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[row][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[row][c - 1].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[row][c - 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[row][c - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[row][c - 1].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[row][c - 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[row][c - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[row][c - 1].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[row][c - 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        //loop for right straight movement, imitating the rook
        for (int c = column; c < 7; c++) {
            // skip free tiles
            if (testBoard[row][c + 1].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[row][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[row][c + 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[row][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[row][c + 1].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[row][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[row][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[row][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[row][c + 1].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[row][c + 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[row][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[row][c + 1].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[row][c + 1].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[row][c + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[row][c + 1].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[row][c + 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[row][c + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[row][c + 1].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[row][c + 1].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        //loop for upwards straight movement, imitating the rook
        for (int r = row; r > 0; r--) {
            // skip free tiles
            if (testBoard[r - 1][column].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[r - 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r - 1][column].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[r - 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r - 1][column].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[r - 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[r - 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[r - 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r - 1][column].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[r - 1][column].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[r - 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r - 1][column].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[r - 1][column].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[r - 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r - 1][column].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[r - 1][column].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[r - 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r - 1][column].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[r - 1][column].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // set firstIteration again to true for next loop
        firstIteration = true;

        //loop for downwards straight movement, imitating the rook
        for (int r = row; r < 7; r++) {
            // skip free tiles
            if (testBoard[r + 1][column].getTileState() != TileState.FREE) {
                if (firstIteration) {
                    // king self-checking first option
                    if (testBoard[r + 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                            testBoard[r + 1][column].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                    // king self-checking second option
                    if (testBoard[r + 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                            testBoard[r + 1][column].getCurrentPiece().getType() == Figure.KING) {
                        return false;
                    }
                }
                // own piece is in way
                if (testBoard[r + 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("black") ||
                        testBoard[r + 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("white")) {
                    break;
                }
                // enemy piece is in way (first option)
                if (testBoard[r + 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r + 1][column].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[r + 1][column].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // enemy piece is in way (second option)
                if (testBoard[r + 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r + 1][column].getCurrentPiece().getType() != Figure.ROOK &&
                        testBoard[r + 1][column].getCurrentPiece().getType() != Figure.QUEEN) {
                    break;
                }
                // king is in check
                else if (testBoard[r + 1][column].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                        testBoard[r + 1][column].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[r + 1][column].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
                // king is in check
                else if (testBoard[r + 1][column].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                        testBoard[r + 1][column].getCurrentPiece().getType() == Figure.ROOK ||
                        testBoard[r + 1][column].getCurrentPiece().getType() == Figure.QUEEN) {
                    return false;
                }
            }
            firstIteration = false;
        }

        // KNIGHT

        // knight movement type 1
        // test if move is out of bounds
        if (row > 1 && column > 0) {
            // king is in check, current player is white
            if (testBoard[row - 2][column - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row - 2][column - 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row - 2][column - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row - 2][column - 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 2
        // test if move is out of bounds
        if (row > 1 && column < 7) {
            // king is in check, current player is white
            if (testBoard[row - 2][column + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row - 2][column + 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row - 2][column + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row - 2][column + 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 3
        // test if move is out of bounds
        if (row < 6 && column > 0) {
            // king is in check, current player is white
            if (testBoard[row + 2][column - 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row + 2][column - 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row + 2][column - 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row + 2][column - 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 4
        // test if move is out of bounds
        if (row < 6 && column < 7) {
            // king is in check, current player is white
            if (testBoard[row + 2][column + 1].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row + 2][column + 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row + 2][column + 1].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row + 2][column + 1].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 5
        // test if move is out of bounds
        if (row > 0 && column > 1) {
            // king is in check, current player is white
            if (testBoard[row - 1][column - 2].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row - 1][column - 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row - 1][column - 2].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row - 1][column - 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 6
        // test if move is out of bounds
        if (row < 7 && column > 1) {
            // king is in check, current player is white
            if (testBoard[row + 1][column - 2].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row + 1][column - 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row + 1][column - 2].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row + 1][column - 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 7
        // test if move is out of bounds
        if (row > 0 && column < 6) {
            // king is in check, current player is white
            if (testBoard[row - 1][column + 2].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row - 1][column + 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row - 1][column + 2].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row - 1][column + 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // knight movement type 8
        // test if move is out of bounds
        if (row < 7 && column < 6) {
            // king is in check, current player is white
            if (testBoard[row + 1][column + 2].getTileState() == TileState.BLACK && colorOfCurrentPlayer.equals("white") &&
                    testBoard[row + 1][column + 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;

            }
            // king is in check, current player is black
            if (testBoard[row + 1][column + 2].getTileState() == TileState.WHITE && colorOfCurrentPlayer.equals("black") &&
                    testBoard[row + 1][column + 2].getCurrentPiece().getType() == Figure.KNIGHT) {
                return false;
            }
        }

        // king is not in check, so move is valid
        return true;
    }
}
