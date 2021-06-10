package epicchess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ChessTest {

    ChessGUI window;
    ChessEngine engine;
    ChessBoard board;


    public ChessTest() {
        engine = new ChessEngine();
        board = new ChessBoard(engine);
        window = new ChessGUI(engine, board);
    }

    @Test
    void buttonArraySizeTest() {

        int buttonArrayLength1 = window.buttonArray.length;
        int buttonArrayLength2 = window.buttonArray[0].length;

        Assertions.assertEquals(8, buttonArrayLength1);
        Assertions.assertEquals(8, buttonArrayLength2);
    }

    @Test
    void modelArraySizeTest() {

        int modelArrayLength1 = board.gameBoard.length;
        int modelArrayLength2 = board.gameBoard[0].length;

        Assertions.assertEquals(8, modelArrayLength1);
        Assertions.assertEquals(8, modelArrayLength2);
    }

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


    @Test
    void pieceStartingPositionModelTest() {
        //White Pieces
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

        //Black Pieces
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
}
