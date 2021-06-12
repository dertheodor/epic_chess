package epicchess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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

    ArrayPosition currentlySelectedPiecePosition;

    List<JButton> currentlyHighlightedButtonList;

    public ChessGUI(ChessEngine engineReference, ChessBoard boardReference) {
        //Initialise Components
        engine = engineReference;
        gameUI = new JFrame();
        boardPanel = new JPanel();
        board = boardReference;
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        buttonArray = new JButton[8][8];
        currentlySelectedPiecePosition = null;
        currentlyHighlightedButtonList = new ArrayList<>();

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
                buttonArray[row][column].setText(board.getTile(row, column).getCurrentPiece().getUniCodePicture());
                buttonArray[row][column].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
                // add initial mousePressed-listeners to only the white pieces
                if (board.getTile(row, column).getCurrentPiece().getColor().equals("white")) {
                    addMouseListenerForMoveablePieceButton(buttonArray[row][column], row, column);
                }
            }
        }
    }

    /**
     * makes button where the piece is clickable
     *
     * @param button the button
     * @param row    row of the button
     * @param column column of the button
     */
    private void addMouseListenerForMoveablePieceButton(JButton button, int row, int column) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // save currentlyHighlightedPosition of selected piece
                currentlySelectedPiecePosition = new ArrayPosition(row, column, true);
                // call further logic for moving
                setMouseListenerForPossibleMoves(board.highlightNextValidMoves(new ArrayPosition(row, column, true)));
            }
        });
    }

    /**
     * adds mousePressed-listeners for next possible moves so we know where the user clicked
     *
     * @param arrayPositionList the positions of next possible moves
     */
    private void setMouseListenerForPossibleMoves(List<ArrayPosition> arrayPositionList) {
        // revert old possible moves
        if (currentlyHighlightedButtonList != null) {
            removeOldHighlightedButtons();
        }

        // highlight in gui to where a move is possible
        highlightPossibleMoves(arrayPositionList);

        arrayPositionList.forEach(arrayPosition ->
                buttonArray[arrayPosition.getRow()][arrayPosition.getColumn()].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        mouseListenerBodyForPossibleMoves(arrayPosition);
                    }
                }));
    }

    /**
     * helper method for reverting next possible moves highlight
     */
    private void removeOldHighlightedButtons() {
        for (JButton button : currentlyHighlightedButtonList) {
            // \u2B24 stands for the black dot we use to indicate a piece can move to this position
            if (button.getText().equals("\u2B24")) {
                button.setText("");
                button.setForeground(Color.black);
                // remove all previously set listeners for possible moves
                for (MouseListener ml : button.getMouseListeners()) {
                    button.removeMouseListener(ml);
                }
            }
        }
        currentlyHighlightedButtonList = new ArrayList<>();
    }

    /**
     * highlights next possible moves
     *
     * @param arrayPositionList list of possible moves
     */
    private void highlightPossibleMoves(List<ArrayPosition> arrayPositionList) {
        for (ArrayPosition position : arrayPositionList) {
            JButton currentButton = buttonArray[position.getRow()][position.getColumn()];
            // set text to dot, resize font and add to highlightedButtonList
            currentButton.setText("\u2B24");
            currentButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 30));
            currentButton.setForeground(Color.darkGray);
            currentlyHighlightedButtonList.add(currentButton);
        }
    }

    /**
     * moves piece to new position
     *
     * @param oldPosition old position of piece
     * @param newPosition new position of piece
     */
    private void movePieceToNewPosition(ArrayPosition oldPosition, ArrayPosition newPosition) {
        // clear text of oldPositionButton
        JButton oldButton = buttonArray[oldPosition.getRow()][oldPosition.getColumn()];
        oldButton.setText("");

        // remove all previously set listeners for old position
        for (MouseListener ml : oldButton.getMouseListeners()) {
            oldButton.removeMouseListener(ml);
        }

        // make moved piece visible on new position
        JButton newPositionButton = buttonArray[newPosition.getRow()][newPosition.getColumn()];
        newPositionButton.setText(board.getTile(newPosition.getRow(), newPosition.getColumn()).getCurrentPiece().getUniCodePicture());
        newPositionButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 90));

        // add button-listeners for turn-changing
        addAllListenersAfterMove(newPosition);
    }

    /**
     * make pieces of next players who's turn it now is movable
     */
    private void addAllListenersAfterMove(ArrayPosition oldPiecePosition) {
        List<ArrayPosition> nextTurnPiecePositions = board.returnNextPlayerPiecePositions(oldPiecePosition);

        // add new button listeners after turn-changing
        for (ArrayPosition position : nextTurnPiecePositions) {
            addMouseListenerForMoveablePieceButton(buttonArray[position.getRow()][position.getColumn()], position.getRow(), position.getColumn());
        }
    }

    /**
     * all listeners on board get removed after a turn has been made
     */
    private void removeAllListenersAfterMove() {
        // remove all previously set listeners from board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (MouseListener ml : buttonArray[i][j].getMouseListeners()) {
                    buttonArray[i][j].removeMouseListener(ml);
                }
            }
        }
    }

    /**
     * Body of the MouseListeners for the Positions where a selected Piece could move.
     *
     * @param arrayPosition The Position of the Button, where the selected Piece could move.
     */
    private void mouseListenerBodyForPossibleMoves(ArrayPosition arrayPosition) {
        // make move
        board.makeMove(currentlySelectedPiecePosition, arrayPosition);
        // remove oldHighlightedButtons
        removeOldHighlightedButtons();
        // remove all listeners for current color for turn-changing
        removeAllListenersAfterMove();
        // move piece on board
        movePieceToNewPosition(currentlySelectedPiecePosition, arrayPosition);
        // reset currently currentlyHighlightedPosition
        currentlySelectedPiecePosition = null;
    }
}
