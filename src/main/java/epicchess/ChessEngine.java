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
            //pawn has moved now so he/she will never be able to move two tiles at once again.
        } else {
            if (gameBoard[position.getRow() - 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() - 1, position.getColumn(), false));
            }
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
            //pawn has moved now so he/she will never be able to move two tiles at once again.
        } else {
            if (gameBoard[position.getRow() + 1][position.getColumn()].getTileState() == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow() + 1, position.getColumn(), false));
            }
        }
        return validMovesList;
    }
}
