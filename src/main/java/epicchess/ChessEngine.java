package epicchess;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {


    public List<ArrayPosition> showNextValidMoves(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList;
        validMovesList = new ArrayList<>();
        if (piece.type == Figure.PAWN) {
            if (piece.color.equals("black")) {
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
        if (!piece.movedBefore) {
            //if pawn has not moved before and both of the tiles in front of him/her are free he/she can move two tiles at once.
            if (gameBoard[position.getRow()][position.getColumn() - 2].tileState == TileState.FREE &&
                    gameBoard[position.getRow()][position.getColumn() - 1].tileState == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow(), position.getColumn() - 2));
            }
            if (gameBoard[position.getRow()][position.getColumn() - 1].tileState == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow(), position.getColumn() - 1));
            }
            //pawn has moved now so he/she will never be able to move two tiles at once again.
            piece.setMovedBeforeTrue();
        } else {
            if (gameBoard[position.getRow()][position.getColumn() - 1].tileState == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow(), position.getColumn() - 1));
            }
        }
        return validMovesList;
    }

    private List<ArrayPosition> blackPawnMovement(ArrayPosition position, ChessPiece piece, ChessTile[][] gameBoard) {
        List<ArrayPosition> validMovesList = new ArrayList<>();
        //check if pawn has moved before
        if (!piece.movedBefore) {
            //if pawn has not moved before and both of the tiles in front of him/her are free he/she can move two tiles at once.
            if (gameBoard[position.getRow()][position.getColumn() + 2].tileState == TileState.FREE &&
                    gameBoard[position.getRow()][position.getColumn() + 1].tileState == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow(), position.getColumn() + 2));
            }
            if (gameBoard[position.getRow()][position.getColumn() + 1].tileState == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow(), position.getColumn() + 1));
            }
            //pawn has moved now so he/she will never be able to move two tiles at once again.
            piece.setMovedBeforeTrue();
        } else {
            if (gameBoard[position.getRow()][position.getColumn() + 1].tileState == TileState.FREE) {
                validMovesList.add(new ArrayPosition(position.getRow(), position.getColumn() + 1));
            }
        }
        return validMovesList;
    }
}
