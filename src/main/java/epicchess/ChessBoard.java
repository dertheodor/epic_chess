package epicchess;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    ChessEngine engine;
    ChessTile[][] gameBoard;
    boolean whiteToMove;

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
        // white player is first to move
        whiteToMove = true;
    }

    /**
     * method used for turn changing, calling method results in change current player
     */
    public void turnChanging() {
        whiteToMove = !whiteToMove;
    }

    /**
     * clears the board in order for new game to initialize
     */
    public void clearBoard() {
        // fill board with new empty
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
        gameBoard[0][0].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "A8rook", false));
        gameBoard[0][1].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "B8knight", false));
        gameBoard[0][2].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "C8bishop", false));
        gameBoard[0][3].setCurrentPiece(new ChessPiece("black", Figure.QUEEN, "\u265B", "D8queen", false));
        gameBoard[0][4].setCurrentPiece(new ChessPiece("black", Figure.KING, "\u265A", "E8king", false));
        gameBoard[0][5].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "F8bishop", false));
        gameBoard[0][6].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "G8knight", false));
        gameBoard[0][7].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "H8rook", false));
        // pawns
        gameBoard[1][0].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "A7pawn", false));
        gameBoard[1][1].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "B7pawn", false));
        gameBoard[1][2].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "C7pawn", false));
        gameBoard[1][3].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "D7pawn", false));
        gameBoard[1][4].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "E7pawn", false));
        gameBoard[1][5].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "F7pawn", false));
        gameBoard[1][6].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "G7pawn", false));
        gameBoard[1][7].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "H7pawn", false));

        // white figures
        gameBoard[7][0].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "A1rook", false));
        gameBoard[7][1].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "B1knight", false));
        gameBoard[7][2].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "C1bishop", false));
        gameBoard[7][3].setCurrentPiece(new ChessPiece("white", Figure.QUEEN, "\u2655", "D1queen", false));
        gameBoard[7][4].setCurrentPiece(new ChessPiece("white", Figure.KING, "\u2654", "E1king", false));
        gameBoard[7][5].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "F1bishop", false));
        gameBoard[7][6].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "G1knight", false));
        gameBoard[7][7].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "H1rook", false));
        // pawns
        gameBoard[6][0].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "A2pawn", false));
        gameBoard[6][1].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "B2pawn", false));
        gameBoard[6][2].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "C2pawn", false));
        gameBoard[6][3].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "D2pawn", false));
        gameBoard[6][4].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "E2pawn", false));
        gameBoard[6][5].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "F2pawn", false));
        gameBoard[6][6].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "G2pawn", false));
        gameBoard[6][7].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "H2pawn", false));
    }

    /**
     * called on reading saved game, puts pieces into board again to their respective positions
     */
    public void initPiecesOnReadGame(String colorOfPiece, Figure figure, String figurePicture, String figureID, boolean hasBeenMoved, int row, int column) {
        gameBoard[row][column].setCurrentPiece(new ChessPiece(colorOfPiece, figure, figurePicture, figureID, hasBeenMoved));
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
     * returns the games current state after each move
     *
     * @param positionOfPieces positions of all pieces
     * @return the games current state. possible return values: "gameNotOver", "staleMate", "checkMate"
     */
    public String gameState(List<ArrayPosition> positionOfPieces) {
        // iterate through all pieces of players color
        for (ArrayPosition position : positionOfPieces) {
            List<ArrayPosition> possibleMoves = engine.showNextValidMoves(position, gameBoard[position.getRow()][position.getColumn()].getCurrentPiece(), gameBoard);

            // game is not over as there is a possible move
            if (possibleMoves.size() > 0) {
                return "gameNotOver";
            }
        }

        String colorOfNextPlayer = gameBoard[positionOfPieces.get(0).getRow()][positionOfPieces.get(0).getColumn()].getCurrentPiece().getColor();

        // code from here on executed only if there are no possible moves
        if (engine.checkForStaleMate(gameBoard, colorOfNextPlayer)) {
            return "staleMate";
        } else {
            return "checkMate";
        }
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
        // when piece has moved for first time, set movedBefore to true
        if (!piece.getMovedBefore()) {
            piece.setMovedBeforeTrue();
        }
        // change turn
        turnChanging();
    }

    /**
     * Handles the castling case.
     *
     * @param oldPosition oldPosition of moving Piece
     * @param newPosition new Position of moving Piece
     * @return returns a string that indicates if and what kind of castling has took place.
     */
    public String castling(ArrayPosition oldPosition, ArrayPosition newPosition) {
        String castleInfo = "no castle";
        //check if move that was just made is the long-castling of the black King.
        if (gameBoard[oldPosition.getRow()][oldPosition.getColumn()].getCurrentPiece().getType() ==
                Figure.KING && oldPosition.equals(new ArrayPosition(0, 4, true)) &&
                newPosition.equals(new ArrayPosition(0, 2, false))) {
            ChessPiece rookToBeMoved = gameBoard[0][0].getCurrentPiece();
            gameBoard[0][0].removeCurrentPiece();
            gameBoard[0][3].setCurrentPiece(rookToBeMoved);
            castleInfo = "long-castle black";
        } else         //check if move that was just made is the short-castling of the black King.
            if (gameBoard[oldPosition.getRow()][oldPosition.getColumn()].getCurrentPiece().getType() ==
                    Figure.KING && oldPosition.equals(new ArrayPosition(0, 4, true)) &&
                    newPosition.equals(new ArrayPosition(0, 6, false))) {
                ChessPiece rookToBeMoved = gameBoard[0][7].getCurrentPiece();
                gameBoard[0][7].removeCurrentPiece();
                gameBoard[0][5].setCurrentPiece(rookToBeMoved);
                castleInfo = "short-castle black";
            } else         //check if move that was just made is the long-castling of the white King.
                if (gameBoard[oldPosition.getRow()][oldPosition.getColumn()].getCurrentPiece().getType() ==
                        Figure.KING && oldPosition.equals(new ArrayPosition(7, 4, true)) &&
                        newPosition.equals(new ArrayPosition(7, 2, false))) {
                    ChessPiece rookToBeMoved = gameBoard[7][0].getCurrentPiece();
                    gameBoard[7][0].removeCurrentPiece();
                    gameBoard[7][3].setCurrentPiece(rookToBeMoved);
                    castleInfo = "long-castle white";
                } else         //check if move that was just made is the short-castling of the white King.
                    if (gameBoard[oldPosition.getRow()][oldPosition.getColumn()].getCurrentPiece().getType() ==
                            Figure.KING && oldPosition.equals(new ArrayPosition(7, 4, true)) &&
                            newPosition.equals(new ArrayPosition(7, 6, false))) {
                        ChessPiece rookToBeMoved = gameBoard[7][7].getCurrentPiece();
                        gameBoard[7][7].removeCurrentPiece();
                        gameBoard[7][5].setCurrentPiece(rookToBeMoved);
                        castleInfo = "short-castle white";
                    }

        return castleInfo;
    }

    /**
     * checks if promotion is possible
     *
     * @param newPosition new position of piece
     * @return return true if promotion is possible else false
     */
    public boolean promotionPossible(ArrayPosition newPosition) {
        if (gameBoard[newPosition.getRow()][newPosition.getColumn()].getCurrentPiece().getType() == Figure.PAWN) {
            // pawn is at the end of the board
            if (newPosition.getRow() == 7 || newPosition.getRow() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * executes the promotion internally in the board
     *
     * @param arrayPosition  new position of piece
     * @param promotedFigure promoted Figure
     */
    public void promotion(ArrayPosition arrayPosition, Figure promotedFigure) {
        String uniCodePicture = "";

        // white promotion
        if (arrayPosition.getRow() == 0) {
            if (promotedFigure == Figure.ROOK) {
                uniCodePicture = "\u2656";
            } else if (promotedFigure == Figure.KNIGHT) {
                uniCodePicture = "\u2658";
            } else if (promotedFigure == Figure.BISHOP) {
                uniCodePicture = "\u2657";
            } else {
                uniCodePicture = "\u2655";
            }

            // remove pawn from new position and save oldID to variable
            String oldID = gameBoard[arrayPosition.getRow()][arrayPosition.getColumn()].getCurrentPiece().getFigureID();
            gameBoard[arrayPosition.getRow()][arrayPosition.getColumn()].removeCurrentPiece();
            // set promoted piece to new position
            gameBoard[arrayPosition.getRow()][arrayPosition.getColumn()].setCurrentPiece(
                    new ChessPiece("white", promotedFigure, uniCodePicture, oldID, false));
        }

        // black promotion
        if (arrayPosition.getRow() == 7) {
            if (promotedFigure == Figure.ROOK) {
                uniCodePicture = "\u265c";
            } else if (promotedFigure == Figure.KNIGHT) {
                uniCodePicture = "\u265e";
            } else if (promotedFigure == Figure.BISHOP) {
                uniCodePicture = "\u265d";
            } else {
                uniCodePicture = "\u265b";
            }

            // remove pawn from new position and save oldID to variable
            String oldID = gameBoard[arrayPosition.getRow()][arrayPosition.getColumn()].getCurrentPiece().getFigureID();
            gameBoard[arrayPosition.getRow()][arrayPosition.getColumn()].removeCurrentPiece();
            // set promoted piece to new position
            gameBoard[arrayPosition.getRow()][arrayPosition.getColumn()].setCurrentPiece(
                    new ChessPiece("black", promotedFigure, uniCodePicture, oldID, false));
        }
    }

    /**
     * @param pieceThatWasJustMoved just moved piece
     * @return list of positions of the pieces that are now able to move
     */
    public List<ArrayPosition> returnNextPlayerPiecePositions(ArrayPosition pieceThatWasJustMoved) {
        String color = gameBoard[pieceThatWasJustMoved.getRow()][pieceThatWasJustMoved.getColumn()].getCurrentPiece().getColor();

        if (color.equals("black")) {
            return helperPiecePositions(color);
        } else {
            return helperPiecePositions("white");
        }
    }

    /**
     * @param oppositeColor opposite-color
     * @return list of positions of the pieces that are now able to move
     */
    private List<ArrayPosition> helperPiecePositions(String oppositeColor) {
        List<ArrayPosition> piecePositionList = new ArrayList<>();

        // introduce new variables so method makes sense
        String color;
        if (oppositeColor.equals("black")) {
            color = "white";
        } else {
            color = "black";
        }

        // iterate whole board and get needed pieces of "opposite" color
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // check that tile is not free and has correct color
                if (gameBoard[i][j].getTileState() != TileState.FREE && gameBoard[i][j].getCurrentPiece().getColor().equals(color)) {
                    piecePositionList.add(new ArrayPosition(i, j, true));
                }
            }
        }
        return piecePositionList;
    }

    /**
     * method which is used for saving the board contents
     *
     * @return board contents and all information which pieces hold
     */
    public StringBuilder saveBoardContents() {
        // init StringBuilder
        StringBuilder sB = new StringBuilder();

        // first line tells which colors turn it is (white if true, black if false)
        sB.append(whiteToMove);
        // line separator
        sB.append("#");
        sB.append(System.getProperty("line.separator"));


        // iterate over whole board
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                // custom logic for free tiles
                if (gameBoard[row][column].getTileState() == TileState.FREE) {
                    sB.append("free");
                    // value separator
                    sB.append(";");
                    sB.append(row);
                    // value separator
                    sB.append(";");
                    sB.append(column);
                    // line separator
                    sB.append("#");
                    // newline
                    sB.append(System.getProperty("line.separator"));
                } else {
                    // build string with all piece information
                    sB.append(gameBoard[row][column].getCurrentPiece().getColor());
                    // value separator
                    sB.append(";");
                    sB.append(gameBoard[row][column].getCurrentPiece().getType());
                    // value separator
                    sB.append(";");
                    sB.append(gameBoard[row][column].getCurrentPiece().getUniCodePicture());
                    // value separator
                    sB.append(";");
                    sB.append(gameBoard[row][column].getCurrentPiece().getFigureID());
                    // value separator
                    sB.append(";");
                    sB.append(gameBoard[row][column].getCurrentPiece().getMovedBefore());
                    // value separator
                    sB.append(";");
                    sB.append(row);
                    // value separator
                    sB.append(";");
                    sB.append(column);
                    // line separator
                    sB.append("#");
                    // newline
                    sB.append(System.getProperty("line.separator"));
                }
            }
        }
        return sB;
    }
}
