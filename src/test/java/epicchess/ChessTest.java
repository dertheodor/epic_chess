package epicchess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class ChessTest {

    ChessGUI testGui;
    ChessEngine testEngine;
    ChessBoard testBoard;

    // init needed components
    public ChessTest() {
        testEngine = new ChessEngine();
        testBoard = new ChessBoard(testEngine);
        testGui = new ChessGUI(testEngine, testBoard);
    }

    // check if gui buttons have correct size for the visual representation of the board
    @Test
    void buttonArraySizeTest() {

        int buttonArrayLength1 = testGui.buttonArray.length;
        int buttonArrayLength2 = testGui.buttonArray[0].length;

        Assertions.assertEquals(8, buttonArrayLength1);
        Assertions.assertEquals(8, buttonArrayLength2);
    }

    // check if internal representation of the board has correct size
    @Test
    void modelArraySizeTest() {

        int modelArrayLength1 = testBoard.gameBoard.length;
        int modelArrayLength2 = testBoard.gameBoard[0].length;

        Assertions.assertEquals(8, modelArrayLength1);
        Assertions.assertEquals(8, modelArrayLength2);
    }

    // check if all starting pieces are on the correct position in buttons of the gui
    @Test
    void pieceStartingPositionButtonsTest() {
        //Black Pieces
        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals("\u265F", testGui.buttonArray[1][i].getText());
        }
        Assertions.assertEquals("\u265C", testGui.buttonArray[0][0].getText());
        Assertions.assertEquals("\u265E", testGui.buttonArray[0][1].getText());
        Assertions.assertEquals("\u265D", testGui.buttonArray[0][2].getText());
        Assertions.assertEquals("\u265B", testGui.buttonArray[0][3].getText());
        Assertions.assertEquals("\u265A", testGui.buttonArray[0][4].getText());
        Assertions.assertEquals("\u265D", testGui.buttonArray[0][5].getText());
        Assertions.assertEquals("\u265E", testGui.buttonArray[0][6].getText());
        Assertions.assertEquals("\u265C", testGui.buttonArray[0][7].getText());

        //White Pieces
        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals("\u2659", testGui.buttonArray[6][i].getText());
        }
        Assertions.assertEquals("\u2656", testGui.buttonArray[7][0].getText());
        Assertions.assertEquals("\u2658", testGui.buttonArray[7][1].getText());
        Assertions.assertEquals("\u2657", testGui.buttonArray[7][2].getText());
        Assertions.assertEquals("\u2655", testGui.buttonArray[7][3].getText());
        Assertions.assertEquals("\u2654", testGui.buttonArray[7][4].getText());
        Assertions.assertEquals("\u2657", testGui.buttonArray[7][5].getText());
        Assertions.assertEquals("\u2658", testGui.buttonArray[7][6].getText());
        Assertions.assertEquals("\u2656", testGui.buttonArray[7][7].getText());
    }

    // check if internal representation of the starting pieces is correct
    @Test
    void pieceStartingPositionModelTest() {
        //Black Pieces
        Assertions.assertEquals("A8rook", testBoard.gameBoard[0][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B8knight", testBoard.gameBoard[0][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C8bishop", testBoard.gameBoard[0][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D8queen", testBoard.gameBoard[0][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E8king", testBoard.gameBoard[0][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F8bishop", testBoard.gameBoard[0][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G8knight", testBoard.gameBoard[0][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H8rook", testBoard.gameBoard[0][7].getCurrentPiece().getFigureID());
        //pawns
        Assertions.assertEquals("A7pawn", testBoard.gameBoard[1][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B7pawn", testBoard.gameBoard[1][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C7pawn", testBoard.gameBoard[1][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D7pawn", testBoard.gameBoard[1][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E7pawn", testBoard.gameBoard[1][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F7pawn", testBoard.gameBoard[1][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G7pawn", testBoard.gameBoard[1][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H7pawn", testBoard.gameBoard[1][7].getCurrentPiece().getFigureID());

        //White Pieces
        Assertions.assertEquals("A1rook", testBoard.gameBoard[7][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B1knight", testBoard.gameBoard[7][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C1bishop", testBoard.gameBoard[7][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D1queen", testBoard.gameBoard[7][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E1king", testBoard.gameBoard[7][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F1bishop", testBoard.gameBoard[7][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G1knight", testBoard.gameBoard[7][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H1rook", testBoard.gameBoard[7][7].getCurrentPiece().getFigureID());
        //pawns
        Assertions.assertEquals("A2pawn", testBoard.gameBoard[6][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B2pawn", testBoard.gameBoard[6][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C2pawn", testBoard.gameBoard[6][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D2pawn", testBoard.gameBoard[6][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E2pawn", testBoard.gameBoard[6][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F2pawn", testBoard.gameBoard[6][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G2pawn", testBoard.gameBoard[6][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H2pawn", testBoard.gameBoard[6][7].getCurrentPiece().getFigureID());
    }

    // figures tests
    @Test
    void pawnMovementTest() {
        // white pawn
        testGui.buttonArray[6][0].doClick();
        testGui.buttonArray[5][0].doClick();
        // black pawn
        testGui.buttonArray[1][1].doClick();
        testGui.buttonArray[2][1].doClick();
        Assertions.assertEquals("A2pawn", testBoard.gameBoard[5][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B7pawn", testBoard.gameBoard[2][1].getCurrentPiece().getFigureID());
    }

    @Test
    void knightMovementTest() {
        // white knight
        testGui.buttonArray[7][1].doClick();
        testGui.buttonArray[5][2].doClick();
        // black knight
        testGui.buttonArray[0][1].doClick();
        testGui.buttonArray[2][2].doClick();
        Assertions.assertEquals("B1knight", testBoard.gameBoard[5][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B8knight", testBoard.gameBoard[2][2].getCurrentPiece().getFigureID());
    }

    @Test
    void rookMovementTest() {
        // move pawns before rook can move
        // white pawn
        testGui.buttonArray[6][0].doClick();
        testGui.buttonArray[4][0].doClick();
        // black pawn
        testGui.buttonArray[1][0].doClick();
        testGui.buttonArray[3][0].doClick();


        // white rook
        testGui.buttonArray[7][0].doClick();
        testGui.buttonArray[5][0].doClick();
        // black rook
        testGui.buttonArray[0][0].doClick();
        testGui.buttonArray[2][0].doClick();
        Assertions.assertEquals("A1rook", testBoard.gameBoard[5][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("A8rook", testBoard.gameBoard[2][0].getCurrentPiece().getFigureID());
    }

    @Test
    void bishopMovementTest() {
        // move pawns before bishop can move
        // white pawn
        testGui.buttonArray[6][3].doClick();
        testGui.buttonArray[4][3].doClick();
        // black pawn
        testGui.buttonArray[1][3].doClick();
        testGui.buttonArray[3][3].doClick();


        // white bishop
        testGui.buttonArray[7][2].doClick();
        testGui.buttonArray[4][5].doClick();
        // black bishop
        testGui.buttonArray[0][2].doClick();
        testGui.buttonArray[4][6].doClick();
        Assertions.assertEquals("C1bishop", testBoard.gameBoard[4][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C8bishop", testBoard.gameBoard[4][6].getCurrentPiece().getFigureID());
    }

    @Test
    void queenMovementTest() {
        // move pawns before queen can move
        // white pawn
        testGui.buttonArray[6][4].doClick();
        testGui.buttonArray[5][4].doClick();
        // black pawn
        testGui.buttonArray[1][4].doClick();
        testGui.buttonArray[2][4].doClick();


        // white queen
        testGui.buttonArray[7][3].doClick();
        testGui.buttonArray[3][7].doClick();
        // black queen
        testGui.buttonArray[0][3].doClick();
        testGui.buttonArray[4][7].doClick();
        Assertions.assertEquals("D1queen", testBoard.gameBoard[3][7].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D8queen", testBoard.gameBoard[4][7].getCurrentPiece().getFigureID());
    }

    @Test
    void kingMovementTest() {
        // move pawns before king can move
        // white pawn
        testGui.buttonArray[6][4].doClick();
        testGui.buttonArray[5][4].doClick();
        // black pawn
        testGui.buttonArray[1][4].doClick();
        testGui.buttonArray[2][4].doClick();


        // white king
        testGui.buttonArray[7][4].doClick();
        testGui.buttonArray[6][4].doClick();
        // black king
        testGui.buttonArray[0][4].doClick();
        testGui.buttonArray[1][4].doClick();
        Assertions.assertEquals("E1king", testBoard.gameBoard[6][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E8king", testBoard.gameBoard[1][4].getCurrentPiece().getFigureID());
    }

    @Test
    void castlingTest() {
        // move pieces out of the way before king can castle
        // white knight
        testGui.buttonArray[7][6].doClick();
        testGui.buttonArray[5][5].doClick();
        // black knight
        testGui.buttonArray[0][6].doClick();
        testGui.buttonArray[2][5].doClick();
        // white pawn so bishop can move
        testGui.buttonArray[6][6].doClick();
        testGui.buttonArray[5][6].doClick();
        // black pawn so bishop can move
        testGui.buttonArray[1][6].doClick();
        testGui.buttonArray[2][6].doClick();
        // white bishop
        testGui.buttonArray[7][5].doClick();
        testGui.buttonArray[5][7].doClick();
        // black bishop
        testGui.buttonArray[0][5].doClick();
        testGui.buttonArray[2][7].doClick();


        // white king castling
        testGui.buttonArray[7][4].doClick();
        testGui.buttonArray[7][6].doClick();
        // black king castling
        testGui.buttonArray[0][4].doClick();
        testGui.buttonArray[0][6].doClick();
        //testing if kings and rooks moved to the right spot
        Assertions.assertEquals("E1king", testBoard.gameBoard[7][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E8king", testBoard.gameBoard[0][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H1rook", testBoard.gameBoard[7][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H8rook", testBoard.gameBoard[0][5].getCurrentPiece().getFigureID());


    }

    @Test
    void gameStateTest() {
        //make one move
        testGui.buttonArray[6][0].doClick();
        testGui.buttonArray[4][0].doClick();
        // get positions of all the pieces that should be able to move next
        List<ArrayPosition> nowMovablePieces = testBoard.returnNextPlayerPiecePositions(new ArrayPosition(4, 0, true));
        String gameState = testBoard.gameState(nowMovablePieces);
        Assertions.assertEquals("gameNotOver", gameState);
    }

    // sam lloyd's ten-move stalemate
    @Test
    void stalemateInTenMovesTest() {
        // e3
        testGui.buttonArray[6][4].doClick();
        testGui.buttonArray[5][4].doClick();
        // a5
        testGui.buttonArray[1][0].doClick();
        testGui.buttonArray[3][0].doClick();
        // h5
        testGui.buttonArray[7][3].doClick();
        testGui.buttonArray[3][7].doClick();
        // a6
        testGui.buttonArray[0][0].doClick();
        testGui.buttonArray[2][0].doClick();
        // xa
        testGui.buttonArray[3][7].doClick();
        testGui.buttonArray[3][0].doClick();
        // h5
        testGui.buttonArray[1][7].doClick();
        testGui.buttonArray[3][7].doClick();
        // xc
        testGui.buttonArray[3][0].doClick();
        testGui.buttonArray[1][2].doClick();
        // ah6
        testGui.buttonArray[2][0].doClick();
        testGui.buttonArray[2][7].doClick();
        // h4
        testGui.buttonArray[6][7].doClick();
        testGui.buttonArray[4][7].doClick();
        // f6
        testGui.buttonArray[1][5].doClick();
        testGui.buttonArray[2][5].doClick();
        // xd7
        testGui.buttonArray[1][2].doClick();
        testGui.buttonArray[1][3].doClick();
        // f7
        testGui.buttonArray[0][4].doClick();
        testGui.buttonArray[1][5].doClick();
        // xb7
        testGui.buttonArray[1][3].doClick();
        testGui.buttonArray[1][1].doClick();
        // d3
        testGui.buttonArray[0][3].doClick();
        testGui.buttonArray[5][3].doClick();
        // xb8
        testGui.buttonArray[1][1].doClick();
        testGui.buttonArray[0][1].doClick();
        // h7
        testGui.buttonArray[5][3].doClick();
        testGui.buttonArray[1][7].doClick();
        // xc8
        testGui.buttonArray[0][1].doClick();
        testGui.buttonArray[0][2].doClick();
        // xc8
        testGui.buttonArray[0][1].doClick();
        testGui.buttonArray[0][2].doClick();
        // g6
        testGui.buttonArray[1][5].doClick();
        testGui.buttonArray[2][6].doClick();
        // e6
        testGui.buttonArray[0][2].doClick();
        testGui.buttonArray[2][4].doClick();
        // stalemate
        List<ArrayPosition> nowMovablePieces = testBoard.returnNextPlayerPiecePositions(new ArrayPosition(2, 4, true));
        String gameState = testBoard.gameState(nowMovablePieces);
        Assertions.assertEquals("staleMate", gameState);
    }

    // fool's mate
    @Test
    void checkMateInTwoMovesTest() {
        // f3
        testGui.buttonArray[6][5].doClick();
        testGui.buttonArray[5][5].doClick();
        // e5
        testGui.buttonArray[1][4].doClick();
        testGui.buttonArray[3][4].doClick();
        // g4
        testGui.buttonArray[6][6].doClick();
        testGui.buttonArray[4][6].doClick();
        // h4
        testGui.buttonArray[0][3].doClick();
        testGui.buttonArray[4][7].doClick();
        // checkmate
        List<ArrayPosition> nowMovablePieces = testBoard.returnNextPlayerPiecePositions(new ArrayPosition(4, 7, true));
        String gameState = testBoard.gameState(nowMovablePieces);
        Assertions.assertEquals("checkMate", gameState);
    }

    // TODO tests: bauer entwickeln, spielstand speichern/ laden, ein paar negativtests vllt. ?!

    // TODO Tests brauchen auch Kommentare

    @Test
    void readPastChessGamePositive() throws IOException {
        // read txt file of saved chess_game
        File pastChessGame = new File("ChessExternalFiles/chess_game_beginning.txt");

        Scanner input = new Scanner(pastChessGame);
        // clear board internally
        testBoard.clearBoard();

        // clear pieces and listeners from gui
        testGui.removeAllListenersAfterMove(true);

        // first-line
        String firstLine = input.nextLine();
        boolean isWhitesTurn = testGui.figureOutWhoseTurnItIs(firstLine);

        // all other lines after first
        while (input.hasNextLine()) {
            String line = input.nextLine();
            testGui.reconstructChessGame(line, isWhitesTurn);
        }
        input.close();

        // create new board which we will fill with the pieces we expect to read from the chess_game.txt file
        ChessTile[][] expectedGameBoard = new ChessTile[8][8];

        // fill board with tiles
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                expectedGameBoard[row][column] = new ChessTile();
            }
        }

        // black figures
        expectedGameBoard[0][0].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "A8rook", false));
        expectedGameBoard[0][1].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "B8knight", false));
        expectedGameBoard[0][2].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "C8bishop", false));
        expectedGameBoard[0][3].setCurrentPiece(new ChessPiece("black", Figure.QUEEN, "\u265B", "D8queen", false));
        expectedGameBoard[0][4].setCurrentPiece(new ChessPiece("black", Figure.KING, "\u265A", "E8king", false));
        expectedGameBoard[0][5].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "F8bishop", false));
        expectedGameBoard[0][6].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "G8knight", false));
        expectedGameBoard[0][7].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "H8rook", false));
        // pawns
        expectedGameBoard[1][0].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "A7pawn", false));
        expectedGameBoard[1][1].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "B7pawn", false));
        expectedGameBoard[1][2].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "C7pawn", false));
        expectedGameBoard[1][3].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "D7pawn", false));
        expectedGameBoard[1][4].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "E7pawn", false));
        expectedGameBoard[1][5].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "F7pawn", false));
        expectedGameBoard[1][6].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "G7pawn", false));
        expectedGameBoard[1][7].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "H7pawn", false));

        // white figures
        expectedGameBoard[7][0].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "A1rook", false));
        expectedGameBoard[7][1].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "B1knight", false));
        expectedGameBoard[7][2].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "C1bishop", false));
        expectedGameBoard[7][3].setCurrentPiece(new ChessPiece("white", Figure.QUEEN, "\u2655", "D1queen", false));
        expectedGameBoard[7][4].setCurrentPiece(new ChessPiece("white", Figure.KING, "\u2654", "E1king", false));
        expectedGameBoard[7][5].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "F1bishop", false));
        expectedGameBoard[7][6].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "G1knight", false));
        expectedGameBoard[7][7].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "H1rook", false));
        // pawns
        expectedGameBoard[6][0].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "A2pawn", false));
        expectedGameBoard[6][1].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "B2pawn", false));
        expectedGameBoard[6][2].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "C2pawn", false));
        expectedGameBoard[6][3].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "D2pawn", false));
        expectedGameBoard[6][4].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "E2pawn", false));
        expectedGameBoard[6][5].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "F2pawn", false));
        expectedGameBoard[6][6].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "G2pawn", false));
        expectedGameBoard[6][7].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "H2pawn", false));

        // assert that they are the same (all piece details checked)
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                // assert same tile state's
                Assertions.assertEquals(testBoard.getTile(row, column).getTileState(), expectedGameBoard[row][column].getTileState());

                // assert same figure id's for tiles which are not free
                if (testBoard.getTile(row, column).getTileState() != TileState.FREE &&
                        expectedGameBoard[row][column].getTileState() != TileState.FREE) {
                    // for easier reference and less code
                    ChessPiece testBoardPiece = testBoard.getTile(row, column).getCurrentPiece();
                    ChessPiece expectedGameBoardPiece = expectedGameBoard[row][column].getCurrentPiece();

                    // Assertions
                    Assertions.assertEquals(testBoardPiece.getUniCodePicture(), expectedGameBoardPiece.getUniCodePicture());
                    Assertions.assertEquals(testBoardPiece.getMovedBefore(), expectedGameBoardPiece.getMovedBefore());
                    Assertions.assertEquals(testBoardPiece.getColor(), expectedGameBoardPiece.getColor());
                    Assertions.assertEquals(testBoardPiece.getType(), expectedGameBoardPiece.getType());
                    Assertions.assertEquals(testBoardPiece.getFigureID(), expectedGameBoardPiece.getFigureID());
                }
            }
        }
    }

    @Test
    void readPastChessGameNegative() throws IOException {
        // read txt file of saved chess_game
        File pastChessGame = new File("ChessExternalFiles/chess_game_everything_moved.txt");

        Scanner input = new Scanner(pastChessGame);
        // clear board internally
        testBoard.clearBoard();

        // clear pieces and listeners from gui
        testGui.removeAllListenersAfterMove(true);

        // first-line
        String firstLine = input.nextLine();
        boolean isWhitesTurn = testGui.figureOutWhoseTurnItIs(firstLine);

        // all other lines after first
        while (input.hasNextLine()) {
            String line = input.nextLine();
            testGui.reconstructChessGame(line, isWhitesTurn);
        }
        input.close();

        // create new board which we will fill with the pieces we expect to read from the chess_game.txt file
        ChessTile[][] expectedGameBoard = new ChessTile[8][8];

        // fill board with tiles
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                expectedGameBoard[row][column] = new ChessTile();
            }
        }

        // black figures
        expectedGameBoard[0][0].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "A8rook", false));
        expectedGameBoard[0][1].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "B8knight", false));
        expectedGameBoard[0][2].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "C8bishop", false));
        expectedGameBoard[0][3].setCurrentPiece(new ChessPiece("black", Figure.QUEEN, "\u265B", "D8queen", false));
        expectedGameBoard[0][4].setCurrentPiece(new ChessPiece("black", Figure.KING, "\u265A", "E8king", false));
        expectedGameBoard[0][5].setCurrentPiece(new ChessPiece("black", Figure.BISHOP, "\u265D", "F8bishop", false));
        expectedGameBoard[0][6].setCurrentPiece(new ChessPiece("black", Figure.KNIGHT, "\u265E", "G8knight", false));
        expectedGameBoard[0][7].setCurrentPiece(new ChessPiece("black", Figure.ROOK, "\u265C", "H8rook", false));
        // pawns
        expectedGameBoard[1][0].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "A7pawn", false));
        expectedGameBoard[1][1].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "B7pawn", false));
        expectedGameBoard[1][2].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "C7pawn", false));
        expectedGameBoard[1][3].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "D7pawn", false));
        expectedGameBoard[1][4].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "E7pawn", false));
        expectedGameBoard[1][5].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "F7pawn", false));
        expectedGameBoard[1][6].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "G7pawn", false));
        expectedGameBoard[1][7].setCurrentPiece(new ChessPiece("black", Figure.PAWN, "\u265F", "H7pawn", false));

        // white figures
        expectedGameBoard[7][0].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "A1rook", false));
        expectedGameBoard[7][1].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "B1knight", false));
        expectedGameBoard[7][2].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "C1bishop", false));
        expectedGameBoard[7][3].setCurrentPiece(new ChessPiece("white", Figure.QUEEN, "\u2655", "D1queen", false));
        expectedGameBoard[7][4].setCurrentPiece(new ChessPiece("white", Figure.KING, "\u2654", "E1king", false));
        expectedGameBoard[7][5].setCurrentPiece(new ChessPiece("white", Figure.BISHOP, "\u2657", "F1bishop", false));
        expectedGameBoard[7][6].setCurrentPiece(new ChessPiece("white", Figure.KNIGHT, "\u2658", "G1knight", false));
        expectedGameBoard[7][7].setCurrentPiece(new ChessPiece("white", Figure.ROOK, "\u2656", "H1rook", false));
        // pawns
        expectedGameBoard[6][0].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "A2pawn", false));
        expectedGameBoard[6][1].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "B2pawn", false));
        expectedGameBoard[6][2].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "C2pawn", false));
        expectedGameBoard[6][3].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "D2pawn", false));
        expectedGameBoard[6][4].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "E2pawn", false));
        expectedGameBoard[6][5].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "F2pawn", false));
        expectedGameBoard[6][6].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "G2pawn", false));
        expectedGameBoard[6][7].setCurrentPiece(new ChessPiece("white", Figure.PAWN, "\u2659", "H2pawn", false));

        // assert that they are not the same
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                // assert same figure id's for tiles which are not free
                if (testBoard.getTile(row, column).getTileState() != TileState.FREE &&
                        expectedGameBoard[row][column].getTileState() != TileState.FREE) {
                    // for easier reference and less code
                    ChessPiece testBoardPiece = testBoard.getTile(row, column).getCurrentPiece();
                    ChessPiece expectedGameBoardPiece = expectedGameBoard[row][column].getCurrentPiece();

                    // Assertions
                    Assertions.assertNotEquals(testBoardPiece.getUniCodePicture(), expectedGameBoardPiece.getUniCodePicture());
                    Assertions.assertNotEquals(testBoardPiece.getMovedBefore(), expectedGameBoardPiece.getMovedBefore());
                    Assertions.assertNotEquals(testBoardPiece.getType(), expectedGameBoardPiece.getType());
                    Assertions.assertNotEquals(testBoardPiece.getFigureID(), expectedGameBoardPiece.getFigureID());
                }
            }
        }
    }
}
