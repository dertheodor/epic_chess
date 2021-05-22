package epicchess;

import javax.swing.*;
import java.awt.*;

public class ChessGUI {

    ChessEngine engine;

    JFrame gameUI;
    JPanel board;

    JMenuBar menuBar;
    JMenu menu;

    Color darkTileColor;
    Color lightTileColor;

    public ChessGUI(ChessEngine engineReference) {
        //Initialise Components
        engine = engineReference;
        gameUI = new JFrame();
        board = new JPanel();
        menuBar = new JMenuBar();
        menu = new JMenu("Options");

        //Initialise Window
        gameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameUI.setSize(500, 500);
        gameUI.setLayout(new BorderLayout());

        //Add MenuBar to our Window
        menuBar.add(menu);
        gameUI.setJMenuBar(menuBar);

        //Initialise board
        darkTileColor = new Color(115, 77, 38);
        lightTileColor = new Color(217, 177, 140);

        //Set layout
        board.setLayout(new GridLayout(0, 9));

        //Fill board
        for (int i = 0; i < 81; i++) {
            JButton newButton = new JButton();
            if (i < 10 || i % 9 == 0)
                descriptionOfEdgeTiles(i, newButton);
            else {
                if (i % 2 == 0) {
                    newButton.setBackground(darkTileColor);
                } else {
                    newButton.setBackground(lightTileColor);
                }
            }
            board.add(newButton);
        }

        //Add board to Window and make window visible
        gameUI.add(board);
        gameUI.setVisible(true);
    }

    /**
     * Helping Method. Correctly describes the Edge Tiles with the numbers 1 - 8 and the Letters a - h.
     *
     * @param i      the Position of the Tile that needs to be described.
     * @param button the button that needs to be described.
     */
    private void descriptionOfEdgeTiles(int i, JButton button) {
        switch (i) {
            case 0:
                button.setText("");
                break;
            case 1:
                button.setText("a");
                break;
            case 2:
                button.setText("b");
                break;
            case 3:
                button.setText("c");
                break;
            case 4:
                button.setText("d");
                break;
            case 5:
                button.setText("e");
                break;
            case 6:
                button.setText("f");
                break;
            case 7:
                button.setText("g");
                break;
            case 8:
                button.setText("h");
                break;
            case 9:
                button.setText("1");
                break;
            case 18:
                button.setText("2");
                break;
            case 27:
                button.setText("3");
                break;
            case 36:
                button.setText("4");
                break;
            case 45:
                button.setText("5");
                break;
            case 54:
                button.setText("6");
                break;
            case 63:
                button.setText("7");
                break;
            case 72:
                button.setText("8");
                break;
            default:
        }
    }

}
