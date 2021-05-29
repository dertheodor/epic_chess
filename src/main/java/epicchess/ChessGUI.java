package epicchess;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ChessGUI {

    ChessEngine engine;
    ChessBoard board;

    JFrame gameUI;
    JPanel boardPanel;

    JMenuBar menuBar;
    JMenu menu;

    Color darkTileColor;
    Color lightTileColor;

    JButton[][] buttonArray;

    public ChessGUI(ChessEngine engineReference, ChessBoard boardReference) {
        //Initialise Components
        engine = engineReference;
        gameUI = new JFrame();
        boardPanel = new JPanel();
        board = boardReference;
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        buttonArray = new JButton[8][8];

        //Initialise Window
        gameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameUI.setSize(1200, 1000);
        gameUI.setLayout(new BorderLayout());

        //Add MenuBar to our Window
        menuBar.add(menu);
        gameUI.setJMenuBar(menuBar);

        //Initialise board
        darkTileColor = new Color(118, 150, 86);
        lightTileColor = new Color(238, 238, 210);

        // TODO grey board option
        //darkTileColor = new Color(125, 135, 150);
        //lightTileColor = new Color(232, 235, 239);

        //Set layout
        boardPanel.setLayout(new GridLayout(0, 9));

        //Fill board
        for (int i = 0; i < 81; i++) {
            JButton newButton = new JButton();
            if (i > 72 || i % 9 == 0) {
                descriptionOfEdgeTiles(i, newButton);
                // make edge-buttons un-clickable
                newButton.setEnabled(false);
                // set edge-buttons size
                newButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 30));
            } else {
                // fill buttonArray for the actual board
                if (i < 9) { // first row
                    buttonArray[0][i - 1] = newButton;
                } else if (i < 18) { // second row
                    buttonArray[1][i - 10] = newButton;
                } else if (i < 27) { // third row
                    buttonArray[2][i - 19] = newButton;
                } else if (i < 36) { // fourth row
                    buttonArray[3][i - 28] = newButton;
                } else if (i < 45) { // fifth row
                    buttonArray[4][i - 37] = newButton;
                } else if (i < 54) { // sixth row
                    buttonArray[5][i - 46] = newButton;
                } else if (i < 63) { // seventh row
                    buttonArray[6][i - 55] = newButton;
                } else { // eight row
                    buttonArray[7][i - 64] = newButton;
                }

                // set dark tiles
                if (i % 2 == 0) {
                    newButton.setBackground(darkTileColor);
                } else { // set light tiles
                    newButton.setBackground(lightTileColor);
                }
            }
            boardPanel.add(newButton);
        }

        // init starting formation in board
        board.initStartingFigures();

        // fill correct buttons with black figures from board
        initFillButtonsWithPieces(0, 1);
        // fill correct buttons with white figures from board
        initFillButtonsWithPieces(6, 7);

        //Add board to Window and make window visible
        gameUI.add(boardPanel);
        gameUI.setVisible(true);
    }

    /**
     * Helping Method. Correctly describes the Edge Tiles with the numbers 1 - 8 and the Letters a - h.
     *
     * @param i      the Position of the Tile that needs to be described.
     * @param button the button that needs to be described.
     */
    private void descriptionOfEdgeTiles(int i, JButton button) {
        //TODO Change back to real Tile Description.
        switch (i) {
            case 0:
                button.setText("0");
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
                button.setText("");
                break;
            case 73:
                button.setText("0");
                break;
            case 74:
                button.setText("1");
                break;
            case 75:
                button.setText("2");
                break;
            case 76:
                button.setText("3");
                break;
            case 77:
                button.setText("4");
                break;
            case 78:
                button.setText("5");
                break;
            case 79:
                button.setText("6");
                break;
            case 80:
                button.setText("7");
                break;
            default:
        }
    }

    /**
     * Fills the buttons with the initial pieces
     *
     * @param startingRow row for which filling should start
     * @param endingRow   row for which filling should end
     */
    private void initFillButtonsWithPieces(int startingRow, int endingRow) {
        for (int row = startingRow; row <= endingRow; row++) {
            for (int column = 0; column < 8; column++) {
                // set "text" (picture) and font size
                buttonArray[row][column].setText(board.getTile(row, column).getCurrentFigure().getUniCodePicture());
                buttonArray[row][column].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
            }
        }
    }

}
