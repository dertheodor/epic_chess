package epicchess;

public class Chess {
    public static void main(String[] args) {
        new Chess();
    }

    ChessGUI window;
    ChessEngine engine;
    ChessBoard board;


    public Chess() {
        engine = new ChessEngine();
        board = new ChessBoard(engine);
        window = new ChessGUI(engine, board);
    }
}
