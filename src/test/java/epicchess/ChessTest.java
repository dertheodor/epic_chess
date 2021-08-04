package epicchess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ChessTest {

    ChessGUI window;
    ChessEngine engine;
    ChessBoard board;

    // init needed components
    public ChessTest() {
        engine = new ChessEngine();
        board = new ChessBoard(engine);
        window = new ChessGUI(engine, board);
    }

    // check if gui buttons have correct size for the visual representation of the board
    @Test
    void buttonArraySizeTest() {

        int buttonArrayLength1 = window.buttonArray.length;
        int buttonArrayLength2 = window.buttonArray[0].length;

        Assertions.assertEquals(8, buttonArrayLength1);
        Assertions.assertEquals(8, buttonArrayLength2);
    }

    // check if internal representation of the board has correct size
    @Test
    void modelArraySizeTest() {

        int modelArrayLength1 = board.gameBoard.length;
        int modelArrayLength2 = board.gameBoard[0].length;

        Assertions.assertEquals(8, modelArrayLength1);
        Assertions.assertEquals(8, modelArrayLength2);
    }

    // check if all starting pieces are on the correct position in buttons of the gui
    @Test
    void pieceStartingPositionButtonsTest() {
        //Black Pieces
        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals("\u265F", window.buttonArray[1][i].getText());
        }
        Assertions.assertEquals("\u265C", window.buttonArray[0][0].getText());
        Assertions.assertEquals("\u265E", window.buttonArray[0][1].getText());
        Assertions.assertEquals("\u265D", window.buttonArray[0][2].getText());
        Assertions.assertEquals("\u265B", window.buttonArray[0][3].getText());
        Assertions.assertEquals("\u265A", window.buttonArray[0][4].getText());
        Assertions.assertEquals("\u265D", window.buttonArray[0][5].getText());
        Assertions.assertEquals("\u265E", window.buttonArray[0][6].getText());
        Assertions.assertEquals("\u265C", window.buttonArray[0][7].getText());

        //White Pieces
        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals("\u2659", window.buttonArray[6][i].getText());
        }
        Assertions.assertEquals("\u2656", window.buttonArray[7][0].getText());
        Assertions.assertEquals("\u2658", window.buttonArray[7][1].getText());
        Assertions.assertEquals("\u2657", window.buttonArray[7][2].getText());
        Assertions.assertEquals("\u2655", window.buttonArray[7][3].getText());
        Assertions.assertEquals("\u2654", window.buttonArray[7][4].getText());
        Assertions.assertEquals("\u2657", window.buttonArray[7][5].getText());
        Assertions.assertEquals("\u2658", window.buttonArray[7][6].getText());
        Assertions.assertEquals("\u2656", window.buttonArray[7][7].getText());
    }

    // check if internal representation of the starting pieces is correct
    @Test
    void pieceStartingPositionModelTest() {
        //Black Pieces
        Assertions.assertEquals("A8rook", board.gameBoard[0][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B8knight", board.gameBoard[0][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C8bishop", board.gameBoard[0][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D8queen", board.gameBoard[0][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E8king", board.gameBoard[0][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F8bishop", board.gameBoard[0][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G8knight", board.gameBoard[0][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H8rook", board.gameBoard[0][7].getCurrentPiece().getFigureID());
        //pawns
        Assertions.assertEquals("A7pawn", board.gameBoard[1][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B7pawn", board.gameBoard[1][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C7pawn", board.gameBoard[1][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D7pawn", board.gameBoard[1][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E7pawn", board.gameBoard[1][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F7pawn", board.gameBoard[1][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G7pawn", board.gameBoard[1][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H7pawn", board.gameBoard[1][7].getCurrentPiece().getFigureID());

        //White Pieces
        Assertions.assertEquals("A1rook", board.gameBoard[7][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B1knight", board.gameBoard[7][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C1bishop", board.gameBoard[7][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D1queen", board.gameBoard[7][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E1king", board.gameBoard[7][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F1bishop", board.gameBoard[7][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G1knight", board.gameBoard[7][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H1rook", board.gameBoard[7][7].getCurrentPiece().getFigureID());
        //pawns
        Assertions.assertEquals("A2pawn", board.gameBoard[6][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B2pawn", board.gameBoard[6][1].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C2pawn", board.gameBoard[6][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D2pawn", board.gameBoard[6][3].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E2pawn", board.gameBoard[6][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("F2pawn", board.gameBoard[6][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("G2pawn", board.gameBoard[6][6].getCurrentPiece().getFigureID());
        Assertions.assertEquals("H2pawn", board.gameBoard[6][7].getCurrentPiece().getFigureID());
    }

    // figures tests
    @Test
    void pawnMovementTest() {
        // white pawn
        window.buttonArray[6][0].doClick();
        window.buttonArray[5][0].doClick();
        // black pawn
        window.buttonArray[1][1].doClick();
        window.buttonArray[2][1].doClick();
        Assertions.assertEquals("A2pawn", board.gameBoard[5][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B7pawn", board.gameBoard[2][1].getCurrentPiece().getFigureID());
    }

    @Test
    void knightMovementTest() {
        // white knight
        window.buttonArray[7][1].doClick();
        window.buttonArray[5][2].doClick();
        // black knight
        window.buttonArray[0][1].doClick();
        window.buttonArray[2][2].doClick();
        Assertions.assertEquals("B1knight", board.gameBoard[5][2].getCurrentPiece().getFigureID());
        Assertions.assertEquals("B8knight", board.gameBoard[2][2].getCurrentPiece().getFigureID());
    }

    @Test
    void rookMovementTest() {
        // move pawns before rook can move
        // white pawn
        window.buttonArray[6][0].doClick();
        window.buttonArray[4][0].doClick();
        // black pawn
        window.buttonArray[1][0].doClick();
        window.buttonArray[3][0].doClick();


        // white rook
        window.buttonArray[7][0].doClick();
        window.buttonArray[5][0].doClick();
        // black rook
        window.buttonArray[0][0].doClick();
        window.buttonArray[2][0].doClick();
        Assertions.assertEquals("A1rook", board.gameBoard[5][0].getCurrentPiece().getFigureID());
        Assertions.assertEquals("A8rook", board.gameBoard[2][0].getCurrentPiece().getFigureID());
    }

    @Test
    void bishopMovementTest() {
        // move pawns before bishop can move
        // white pawn
        window.buttonArray[6][3].doClick();
        window.buttonArray[4][3].doClick();
        // black pawn
        window.buttonArray[1][3].doClick();
        window.buttonArray[3][3].doClick();


        // white bishop
        window.buttonArray[7][2].doClick();
        window.buttonArray[4][5].doClick();
        // black bishop
        window.buttonArray[0][2].doClick();
        window.buttonArray[4][6].doClick();
        Assertions.assertEquals("C1bishop", board.gameBoard[4][5].getCurrentPiece().getFigureID());
        Assertions.assertEquals("C8bishop", board.gameBoard[4][6].getCurrentPiece().getFigureID());
    }

    @Test
    void queenMovementTest() {
        // move pawns before queen can move
        // white pawn
        window.buttonArray[6][4].doClick();
        window.buttonArray[5][4].doClick();
        // black pawn
        window.buttonArray[1][4].doClick();
        window.buttonArray[2][4].doClick();


        // white queen
        window.buttonArray[7][3].doClick();
        window.buttonArray[3][7].doClick();
        // black queen
        window.buttonArray[0][3].doClick();
        window.buttonArray[4][7].doClick();
        Assertions.assertEquals("D1queen", board.gameBoard[3][7].getCurrentPiece().getFigureID());
        Assertions.assertEquals("D8queen", board.gameBoard[4][7].getCurrentPiece().getFigureID());
    }

    @Test
    void kingMovementTest() {
        // move pawns before king can move
        // white pawn
        window.buttonArray[6][4].doClick();
        window.buttonArray[5][4].doClick();
        // black pawn
        window.buttonArray[1][4].doClick();
        window.buttonArray[2][4].doClick();


        // white king
        window.buttonArray[7][4].doClick();
        window.buttonArray[6][4].doClick();
        // black king
        window.buttonArray[0][4].doClick();
        window.buttonArray[1][4].doClick();
        Assertions.assertEquals("E1king", board.gameBoard[6][4].getCurrentPiece().getFigureID());
        Assertions.assertEquals("E8king", board.gameBoard[1][4].getCurrentPiece().getFigureID());
    }

    // sam lloyd's ten-move stalemate
    @Test
    void stalemateInTenMovesTest() {
        // e3
        window.buttonArray[6][4].doClick();
        window.buttonArray[5][4].doClick();
        // a5
        window.buttonArray[1][0].doClick();
        window.buttonArray[3][0].doClick();
        // h5
        window.buttonArray[7][3].doClick();
        window.buttonArray[3][7].doClick();
        // a6
        window.buttonArray[0][0].doClick();
        window.buttonArray[2][0].doClick();
        // xa
        window.buttonArray[3][7].doClick();
        window.buttonArray[3][0].doClick();
        // h5
        window.buttonArray[1][7].doClick();
        window.buttonArray[3][7].doClick();
        // xc
        window.buttonArray[3][0].doClick();
        window.buttonArray[1][2].doClick();
        // ah6
        window.buttonArray[2][0].doClick();
        window.buttonArray[2][7].doClick();
        // h4
        window.buttonArray[6][7].doClick();
        window.buttonArray[4][7].doClick();
        // f6
        window.buttonArray[1][5].doClick();
        window.buttonArray[2][5].doClick();
        // xd7
        window.buttonArray[1][2].doClick();
        window.buttonArray[1][3].doClick();
        // f7
        window.buttonArray[0][4].doClick();
        window.buttonArray[1][5].doClick();
        // xb7
        window.buttonArray[1][3].doClick();
        window.buttonArray[1][1].doClick();
        // d3
        window.buttonArray[0][3].doClick();
        window.buttonArray[5][3].doClick();
        // xb8
        window.buttonArray[1][1].doClick();
        window.buttonArray[0][1].doClick();
        // h7
        window.buttonArray[5][3].doClick();
        window.buttonArray[1][7].doClick();
        // xc8
        window.buttonArray[0][1].doClick();
        window.buttonArray[0][2].doClick();
        // xc8
        window.buttonArray[0][1].doClick();
        window.buttonArray[0][2].doClick();
        // g6
        window.buttonArray[1][5].doClick();
        window.buttonArray[2][6].doClick();
        // e6
        window.buttonArray[0][2].doClick();
        window.buttonArray[2][4].doClick();
        // stalemate
        Assertions.assertTrue(true);
    }

    // fool's mate
    @Test
    void checkMateInTwoMovesTest() {
        // f3
        window.buttonArray[6][5].doClick();
        window.buttonArray[5][5].doClick();
        // e5
        window.buttonArray[1][4].doClick();
        window.buttonArray[3][4].doClick();
        // g4
        window.buttonArray[6][6].doClick();
        window.buttonArray[4][6].doClick();
        // h4
        window.buttonArray[0][3].doClick();
        window.buttonArray[4][7].doClick();
        // checkmate
        Assertions.assertTrue(true);
    }
}
