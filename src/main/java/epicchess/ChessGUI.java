package epicchess;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessGUI {

    ChessEngine engine;
    ChessBoard board;

    JFrame gameUI;
    JPanel boardPanel;

    JMenuBar menuBar;

    JMenu fileMenu;
    JMenuItem openItem;
    JFileChooser openFileChooser;
    JMenuItem saveItem;
    JFileChooser saveFileChooser;
    JMenuItem exitItem;

    JMenu editMenu;
    JMenuItem greenBoard;
    JMenuItem greyBoard;

    JMenu helpMenu;
    JMenuItem aboutItem;

    Color darkTileColor;
    Color lightTileColor;
    Color captureColor;

    JButton[][] buttonArray;

    ArrayPosition currentlySelectedPiecePosition;

    List<ArrayPosition> currentlyHighlightedArrayPositionList;

    public ChessGUI(ChessEngine engineReference, ChessBoard boardReference) {
        //Initialise Components
        engine = engineReference;
        gameUI = new JFrame();
        boardPanel = new JPanel();
        board = boardReference;
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        // opening past games
        openItem = new JMenuItem("Open...");
        openFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // set default name
        openFileChooser.setSelectedFile(new File("chess_game.txt"));
        // only show txt files
        openFileChooser.setFileFilter(new FileNameExtensionFilter("Text file (TXT)", "txt"));

        // saving present game
        saveItem = new JMenuItem("Save As...");
        saveFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // set default name
        File saveModalSelectedFile = new File("chess_game.txt");
        saveFileChooser.setSelectedFile(saveModalSelectedFile);
        // only show txt files
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Text file (TXT)", "txt"));
        final FileWriter[] fileWriter = new FileWriter[1];

        exitItem = new JMenuItem("Exit");
        editMenu = new JMenu("Edit");
        greenBoard = new JMenuItem("Green themed board");
        greyBoard = new JMenuItem("Grey themed board");
        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        buttonArray = new JButton[8][8];
        currentlySelectedPiecePosition = null;
        currentlyHighlightedArrayPositionList = new ArrayList<>();

        //Initialise Window
        gameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameUI.setSize(1200, 1000);
        gameUI.setLayout(new BorderLayout());

        //Add menuBar to our Window
        gameUI.setJMenuBar(menuBar);
        //Add menuBar components
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(editMenu);
        editMenu.add(greenBoard);
        editMenu.add(greyBoard);
        menuBar.add(helpMenu);
        helpMenu.add(aboutItem);

        // add listener for open submenu-entry
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readOnMousePressed(openFileChooser);
            }
        });

        // add listener for save submenu-entry
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOnMousePressed(fileWriter, saveFileChooser);
            }
        });

        // add listener for exit submenu-entry
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // add listener for board theme change
        greenBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darkTileColor = new Color(118, 150, 86);
                lightTileColor = new Color(238, 238, 210);
                initBoardColorChange();
            }
        });

        // add listener for board theme change
        greyBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darkTileColor = new Color(125, 135, 150);
                lightTileColor = new Color(232, 235, 239);
                initBoardColorChange();
            }
        });

        // add listener for about submenu-entry
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameUI,
                        "This chess game has been made by Lars Penning(8penning) and Theodor Bajusz(8bajusz) " +
                                "as part of PTP_2021 at the University of Hamburg",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // color for highlighting possible capture of enemy figure
        captureColor = new Color(245, 160, 147);

        //Initialise board
        darkTileColor = new Color(118, 150, 86);
        lightTileColor = new Color(238, 238, 210);

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
     * logic for opening past saved game
     *
     * @param openFileChooser JFileChooser responsible for selecting the directory and file name of to be read file
     */
    private void readOnMousePressed(JFileChooser openFileChooser) {
        // init modal on button press and save its return value for further processing
        int returnValue = openFileChooser.showOpenDialog(gameUI);

        // triggered when save button is pressed
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File toBeReadFile = openFileChooser.getSelectedFile();
            try {
                Scanner input = new Scanner(toBeReadFile);
                // clear board internally
                board.clearBoard();

                // clear pieces and listeners from gui
                removeAllListenersAfterMove(true);

                // first-line
                String firstLine = input.nextLine();
                boolean isWhitesTurn = figureOutWhoseTurnItIs(firstLine);


                // all other lines after first
                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    reconstructChessGame(line, isWhitesTurn);
                }
                input.close();

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    /**
     * @param line the first line of the txt file
     * @return true if it was whites turn, false if it was black
     */
    public boolean figureOutWhoseTurnItIs(String line) {
        // separated lines
        String[] separatedEntries = line.split("#");

        // all of piece values
        String[] separatedPieceValues = separatedEntries[0].split(";");

        // first line says true so it was whites move
        if (separatedPieceValues[0].equals("true")) {
            return true;
        }
        return false;
    }

    /**
     * reconstructs game from saved file
     *
     * @param line         the line to read
     * @param isWhitesTurn information whose turn it is
     */
    public void reconstructChessGame(String line, boolean isWhitesTurn) {
        // separated lines
        String[] separatedEntries = line.split("#");

        // all of piece values
        String[] separatedPieceValues = separatedEntries[0].split(";");


        // first line says true so it was whites move
        if (separatedPieceValues[0].equals("true")) {
            return;
        } else
            // first line says false so it was blacks move
            if (separatedPieceValues[0].equals("false")) {
                return;
            }


        // custom logic not free tiles
        if (!separatedPieceValues[0].equals("free")) {
            Figure figureEnum;
            // map string figures to enum figures
            switch (separatedPieceValues[1]) {
                case "PAWN":
                    figureEnum = Figure.PAWN;
                    break;
                case "KNIGHT":
                    figureEnum = Figure.KNIGHT;
                    break;
                case "BISHOP":
                    figureEnum = Figure.BISHOP;
                    break;
                case "ROOK":
                    figureEnum = Figure.ROOK;
                    break;
                case "QUEEN":
                    figureEnum = Figure.QUEEN;
                    break;
                case "KING":
                    figureEnum = Figure.KING;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + separatedPieceValues[1]);
            }

            boolean hasBeenMovedBoolean;
            // map string boolean to actual boolean
            if (separatedPieceValues[4].equals("true")) {
                hasBeenMovedBoolean = true;
            } else {
                hasBeenMovedBoolean = false;
            }

            int pieceRow = Integer.parseInt(separatedPieceValues[5]);
            int pieceColumn = Integer.parseInt(separatedPieceValues[6]);

            // put pieces from saveFile again onto the board
            board.initPiecesOnReadGame(
                    separatedPieceValues[0], // colorOfPiece
                    figureEnum, // figure
                    separatedPieceValues[2], // figurePicture
                    separatedPieceValues[3], // figureID
                    hasBeenMovedBoolean, // hasBeenMoved
                    pieceRow, // row
                    pieceColumn); // column

            // show pieces again in gui
            // set "text" (picture) and font size
            buttonArray[pieceRow][pieceColumn].setText(board.getTile(pieceRow, pieceColumn).getCurrentPiece().getUniCodePicture());
            buttonArray[pieceRow][pieceColumn].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
            // add initial mousePressed-listeners to color whose turn it was
            // white
            if (board.getTile(pieceRow, pieceColumn).getCurrentPiece().getColor().equals("white") && isWhitesTurn) {
                addActionListenerForMoveablePieceButton(buttonArray[pieceRow][pieceColumn], pieceRow, pieceColumn);
            } else
                // black
                if (board.getTile(pieceRow, pieceColumn).getCurrentPiece().getColor().equals("black") && !isWhitesTurn) {
                    addActionListenerForMoveablePieceButton(buttonArray[pieceRow][pieceColumn], pieceRow, pieceColumn);
                }
        }
    }

    /**
     * logic for saving current game
     *
     * @param fileWriter      fileWriter responsible for writing file to disk
     * @param saveFileChooser JFileChooser responsible for selecting the directory and file name
     */
    private void saveOnMousePressed(FileWriter[] fileWriter, JFileChooser saveFileChooser) {
        // init modal on button press and save its return value for further processing
        int returnValue = saveFileChooser.showSaveDialog(gameUI);

        // triggered when save button is pressed
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = saveFileChooser.getSelectedFile().toString();

            // ensure that the file is saved as txt
            if (filename.endsWith(".txt")) {
                File file = new File(filename);
                try {
                    fileWriter[0] = new FileWriter(file, true);
                    fileWriter[0].write(String.valueOf(board.saveBoardContents()));
                    fileWriter[0].close();
                    JOptionPane.showMessageDialog(gameUI,
                            "File successfully saved!",
                            "Success",
                            JOptionPane.WARNING_MESSAGE);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(gameUI,
                        "Game can only be saved as .txt file",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
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
                    addActionListenerForMoveablePieceButton(buttonArray[row][column], row, column);
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
    private void addActionListenerForMoveablePieceButton(JButton button, int row, int column) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save currentlyHighlightedPosition of selected piece
                currentlySelectedPiecePosition = new ArrayPosition(row, column, true);
                // call further logic for moving
                setActionListenerForPossibleMoves(board.highlightNextValidMoves(new ArrayPosition(row, column, true)));
            }
        });
    }

    /**
     * adds mousePressed-listeners for next possible moves so we know where the user wants to move
     *
     * @param arrayPositionList the positions of next possible moves
     */
    private void setActionListenerForPossibleMoves(List<ArrayPosition> arrayPositionList) {
        // revert old possible moves
        if (currentlyHighlightedArrayPositionList != null) {
            removeOldHighlightedButtons();
        }

        // highlight in gui to where a move is possible
        highlightPossibleMoves(arrayPositionList);

        // listener for turn-taking
        arrayPositionList.forEach(arrayPosition ->
                buttonArray[arrayPosition.getRow()][arrayPosition.getColumn()].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionListenerBodyForPossibleMoves(arrayPosition);
                    }
                }));
    }

    /**
     * helper method for reverting next possible moves highlight
     */
    private void removeOldHighlightedButtons() {
        for (ArrayPosition aP : currentlyHighlightedArrayPositionList) {
            // create button out of its arrayPosition
            JButton button = buttonArray[aP.getRow()][aP.getColumn()];

            // check if button is not highlighted
            if (button.getBackground().equals(captureColor)) {
                // set button color to initial color
                setButtonColorToDefault(aP.getRow(), aP.getColumn());
                // remove all previously set listeners for possible moves
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
            }

            // \u2B24 stands for the black dot we use to indicate a piece can move to this position
            if (button.getText().equals("\u2B24")) {
                button.setText("");
                button.setForeground(Color.black);
                // remove all previously set listeners for possible moves
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
            }
        }
        currentlyHighlightedArrayPositionList = new ArrayList<>();
    }

    /**
     * highlights next possible moves
     *
     * @param arrayPositionList list of possible moves
     */
    private void highlightPossibleMoves(List<ArrayPosition> arrayPositionList) {
        for (ArrayPosition position : arrayPositionList) {
            // variable for button at hand
            JButton currentButton = buttonArray[position.getRow()][position.getColumn()];

            // position of possible move is occupied
            if (position.getIsOccupied()) {
                // set color to light-red so user knows that enemy piece can be taken
                currentButton.setBackground(captureColor);
            } else /*position is not occupied*/ {
                // set text to dot, resize font and add to highlightedButtonList
                currentButton.setText("\u2B24");
                currentButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 30));
                currentButton.setForeground(Color.darkGray);
            }
            // button is now highlighted (dot or lightly red colored)
            currentlyHighlightedArrayPositionList.add(position);
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
        for (ActionListener al : oldButton.getActionListeners()) {
            oldButton.removeActionListener(al);
        }

        // make moved piece visible on new position
        JButton newPositionButton = buttonArray[newPosition.getRow()][newPosition.getColumn()];
        newPositionButton.setText(board.getTile(newPosition.getRow(), newPosition.getColumn()).getCurrentPiece().getUniCodePicture());
        newPositionButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 90));

        // set button color to initial color
        setButtonColorToDefault(newPosition.getRow(), newPosition.getColumn());

        // add button-listeners for turn-changing
        addAllListenersAfterMove(newPosition);
    }

    /**
     * sets color back to current default tile color
     *
     * @param row    row of the button
     * @param column column of the button
     */
    private void setButtonColorToDefault(int row, int column) {
        // light field
        if ((row + column) % 2 == 0) {
            buttonArray[row][column].setBackground(lightTileColor);
        } else /*dark field*/ {
            buttonArray[row][column].setBackground(darkTileColor);
        }
    }

    /**
     * initializes the board color change by calling helper function, tile colors need to be set before function call
     */
    private void initBoardColorChange() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                setButtonColorToDefault(row, column);
            }
        }
    }

    /**
     * make pieces of next players who's turn it now is movable
     */
    private void addAllListenersAfterMove(ArrayPosition oldPiecePosition) {
        List<ArrayPosition> nextTurnPiecePositions = board.returnNextPlayerPiecePositions(oldPiecePosition);

        // check the games current state
        String gameState = board.gameState(nextTurnPiecePositions);

        // notify user when game is over
        gameStateAnnouncement(gameState, board.gameBoard[nextTurnPiecePositions.get(0).getRow()][nextTurnPiecePositions.get(0).getColumn()].getCurrentPiece().getColor());

        // add new button listeners after turn-changing
        for (ArrayPosition position : nextTurnPiecePositions) {
            addActionListenerForMoveablePieceButton(buttonArray[position.getRow()][position.getColumn()], position.getRow(), position.getColumn());
        }
    }

    //TODO option start new game when old one is over

    /**
     * Announces the games state
     *
     * @param gameState state of the current game
     */
    private void gameStateAnnouncement(String gameState, String colorOfOppositePlayer) {
        if (gameState.equals("staleMate")) {
            JOptionPane.showMessageDialog(gameUI,
                    "Stalemate.",
                    "Game over",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (gameState.equals("checkMate")) {
            JOptionPane.showMessageDialog(gameUI,
                    colorOfOppositePlayer + " loses by checkmate.",
                    "Game over",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * all listeners on board get removed after a turn has been made
     *
     * @param clearGUI true if GUI buttons should also be cleared, false otherwise
     */
    public void removeAllListenersAfterMove(boolean clearGUI) {
        // remove all previously set listeners from board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (ActionListener al : buttonArray[i][j].getActionListeners()) {
                    buttonArray[i][j].removeActionListener(al);
                    if (clearGUI) {
                        buttonArray[i][j].setText("");
                    }
                }
            }
        }
    }

    /**
     * Body of the ActionListeners for the Positions where a selected Piece could move.
     *
     * @param arrayPosition The Position of the Button, where the selected Piece could move.
     */
    private void actionListenerBodyForPossibleMoves(ArrayPosition arrayPosition) {
        // special case castling
        String castleInfo = board.castling(currentlySelectedPiecePosition, arrayPosition);
        switch (castleInfo) {
            case "no castle":
                break;
            case "long-castle black":
                movePieceToNewPosition(new ArrayPosition(0, 0, true), new ArrayPosition(0, 3, false));
                break;
            case "short-castle black":
                movePieceToNewPosition(new ArrayPosition(0, 7, true), new ArrayPosition(0, 5, false));
                break;
            case "long-castle white":
                movePieceToNewPosition(new ArrayPosition(7, 0, true), new ArrayPosition(7, 3, false));
                break;
            case "short-castle white":
                movePieceToNewPosition(new ArrayPosition(7, 7, true), new ArrayPosition(7, 5, false));
                break;
        }

        // make move
        board.makeMove(currentlySelectedPiecePosition, arrayPosition);

        // special case pawn promotion
        // promotion is possible
        if (board.promotionPossible(arrayPosition)) {
            // visualizes the promotion in the GUI
            promotionInGUI(arrayPosition);
        }

        // remove oldHighlightedButtons
        removeOldHighlightedButtons();
        // remove all listeners for current color for turn-changing
        removeAllListenersAfterMove(false);
        // move piece on board
        movePieceToNewPosition(currentlySelectedPiecePosition, arrayPosition);
        // reset currently currentlyHighlightedPosition
        currentlySelectedPiecePosition = null;
    }

    /**
     * visualizes the promotion in the GUI
     *
     * @param arrayPosition position of piece
     */
    private void promotionInGUI(ArrayPosition arrayPosition) {
        int row = arrayPosition.getRow();
        int column = arrayPosition.getColumn();
        Object[] pieces = {
                "\u2656", "\u2658", "\u2657", "\u2655"};

        JPanel panel = new JPanel();
        panel.add(new JLabel("Press button"));

        // 0 is rook, 1 is knight, 2 is bishop, 3 is queen, -1 if window closed
        int result = JOptionPane.showOptionDialog(gameUI, panel, "Choose a piece",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, pieces, null);

        // promote figure in board and show it on the GUI depending on what was chosen
        switch (result) {
            case -1:
            case 3:
                board.promotion(arrayPosition, Figure.QUEEN);
                buttonArray[row][column].setText(board.getTile(row, column).getCurrentPiece().getUniCodePicture());
                buttonArray[row][column].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
                break;
            case 0:
                board.promotion(arrayPosition, Figure.ROOK);
                buttonArray[row][column].setText(board.getTile(row, column).getCurrentPiece().getUniCodePicture());
                buttonArray[row][column].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
                break;
            case 1:
                board.promotion(arrayPosition, Figure.KNIGHT);
                buttonArray[row][column].setText(board.getTile(row, column).getCurrentPiece().getUniCodePicture());
                buttonArray[row][column].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
                break;
            case 2:
                board.promotion(arrayPosition, Figure.BISHOP);
                buttonArray[row][column].setText(board.getTile(row, column).getCurrentPiece().getUniCodePicture());
                buttonArray[row][column].setFont(new Font("Arial Unicode MS", Font.BOLD, 90));
                break;
        }
    }
}
