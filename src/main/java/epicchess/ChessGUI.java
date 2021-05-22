package epicchess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        menu = new JMenu("HeyHey");

        //Initialise Window
        gameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameUI.setSize(500, 500);
        gameUI.setLayout(new BorderLayout());

        //Add MenuBar to our Window
        menuBar.add(menu);
        gameUI.setJMenuBar(menuBar);

        //Initialise board
        darkTileColor = new Color(115, 77, 38);
        lightTileColor = new Color(223, 190, 160);
        board.setLayout(new GridLayout(0, 9));
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
     * @return A Button with the right description.
     */
    private JButton descriptionOfEdgeTiles(int i, JButton button) {
        switch (i) {
            case 0:
                button.setText("Chess");
                return button;
            case 1:
                button.setText("1");
                return button;
            case 2:
                button.setText("2");
                return button;
            case 3:
                button.setText("3");
                return button;
            case 4:
                button.setText("4");
                return button;
            case 5:
                button.setText("5");
                return button;
            case 6:
                button.setText("6");
                return button;
            case 7:
                button.setText("7");
                return button;
            case 8:
                button.setText("8");
                return button;
            case 9:
                button.setText("a");
                return button;
            case 18:
                button.setText("b");
                return button;
            case 27:
                button.setText("c");
                return button;
            case 36:
                button.setText("d");
                return button;
            case 45:
                button.setText("e");
                return button;
            case 54:
                button.setText("f");
                return button;
            case 63:
                button.setText("g");
                return button;
            case 72:
                button.setText("h");
                return button;
            default:
                return button;
        }

    }

}
