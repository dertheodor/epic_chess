package epicchess;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {


    public List<ArrayPosition> showNextValidMoves(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList;
        validMovesList = new ArrayList<>();
        if (piece.getType() == Figure.PAWN) {
            if (piece.getColor().equals("black")) {
                validMovesList = blackPawnMovement(position, piece, gameBoard);
            } else {
                validMovesList = whitePawnMovement(position, piece, gameBoard);
            }
        }
        return validMovesList;
    }

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


}
