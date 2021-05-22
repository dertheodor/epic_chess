package epicchess;

public class Chess {
    public static void main(String[] args) {
        new Chess();
    }

    ChessGUI window;
    ChessEngine engine;


    public Chess() {
        engine = new ChessEngine();
        window = new ChessGUI(engine);
    }
}
