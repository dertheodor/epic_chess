package epicchess;

public class Chess {
    public static void main(String[] args) {
        new Chess();
    }

    ChessGUI window;
    ChessEngine engine;
    ChessBoard board;


    public Chess() {
        board = new ChessBoard();
        engine = new ChessEngine(board);
        window = new ChessGUI(engine, board);
    }
}
