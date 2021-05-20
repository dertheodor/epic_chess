package mydraw;
// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

// *** modified by PTP 04/2020
// *** minimal changes from AWT to Swing -> replace elements/classes
// *** behavior is similiar but not equal ! (Why?)

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The application class.  Processes high-level commands sent by GUI
 */
public class Draw {
    /**
     * main entry point.  Just create an instance of this application class
     */
    public static void main(String[] args) {
        new Draw();
    }

    DrawGUI window;

    /**
     * Application constructor:  create an instance of our GUI class
     */
    public Draw() {
        window = new DrawGUI(this);
    }

}

/**
 * This class implements the GUI for our application
 */
class DrawGUI extends JFrame {
    Draw app;      // A reference to the application, to send commands to.
    CommandQueue cQ;
    Color fgColor;
    Color bgColor;
    BufferedImage bufferImg;
    Graphics bufferG;
    JPanel drawingPanel;
    int windowWidth;
    int windowHeight;
    public JButton undo;
    public JButton redo;

    // init ShapeManager
    ShapeManager shapeManager;

    class DrawingPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bufferImg, -9, -67, null);
            // check if height or width changed
            if (windowHeight != getHeight() || windowWidth != getWidth()) {
                BufferedImage newBufferImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics newBufferG = newBufferImg.getGraphics();
                newBufferG.fillRect(0, 0, newBufferImg.getWidth(), newBufferImg.getHeight());
                newBufferG.drawImage(bufferImg, 0, 0, null);
                bufferImg = newBufferImg;
                bufferG = newBufferG;
                windowWidth = getWidth();
                windowHeight = getHeight();
            }
        }

    }

    /**
     * The GUI constructor does all the work of creating the GUI and setting
     * up event listeners.  Note the use of local and anonymous classes.
     */
    public DrawGUI(Draw application) {
        super("Draw");        // Create the window
        app = application;    // Remember the application reference
        fgColor = Color.black;  // the current drawing color
        bgColor = Color.white;  // the current background color
        windowWidth = 950;
        windowHeight = 550;
        // instantiate public undo and redo buttons
        undo = new JButton("Undo");
        redo = new JButton("Redo");
        // instantiate CommandQueue
        cQ = new CommandQueue(this);
        // instantiate ShapeManager
        shapeManager = new ShapeManager(this, cQ);
        final FileWriter[] fileWriter = new FileWriter[1];

        // Set a LayoutManager, and add the choosers and buttons to the window.
        this.setLayout(new BorderLayout());

        // drawing modes and its JComboBox
        String[] shapes = {"Scribble", "Rectangle", "Oval", "Filled 3DRect", "Round Rectangle", "Triangle", "Isosceles triangle"};
        JComboBox<String> comboBoxDrawingModes = new JComboBox<>(shapes);
        comboBoxDrawingModes.addItemListener(shapeManager);

        // drawing colors and its JComboBox
        String[] colors = {
                "Black", "White", "Orange", "Cyan", "Yellow", "Green", "Red", "Blue", "Dark Gray", "Gray", "Light Gray"};
        JComboBox<String> comboBoxDrawingColors = new JComboBox<>(colors);
        class ColorItemListener implements ItemListener {

            // user selected new color => store new color in DrawGUIs
            public void itemStateChanged(ItemEvent e) {
                fgColor = colorSwitchHelper(e.getItem().toString());
            }
        }
        // set default drawing color
        comboBoxDrawingColors.getModel().setSelectedItem("Black");
        comboBoxDrawingColors.addItemListener(new ColorItemListener());

        // background colors JComboBox
        JComboBox<String> comboBoxBackgroundColors = new JComboBox<>(colors);
        class BackgroundColorItemListener implements ItemListener {

            // user selected new color => store new color in DrawGUIs
            public void itemStateChanged(ItemEvent e) {
                // set bg color so clearing sets background to selected color
                bgColor = colorSwitchHelper(e.getItem().toString());
                bufferG.setColor(colorSwitchHelper(e.getItem().toString()));
                bufferG.fillRect(0, 0, bufferImg.getWidth(), bufferImg.getHeight());
                // repaint previously drawn drawables on new BG
                repaintOnBackgroundChange();
                updateCanvas();
            }
        }
        // set default background color
        comboBoxBackgroundColors.getModel().setSelectedItem("White");
        comboBoxBackgroundColors.addItemListener(new BackgroundColorItemListener());


        // header JButtons
        // save button
        JButton save = new JButton("Save");
        JFileChooser saveFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // set default name
        File saveModalSelectedFile = new File("drawing.txt");
        saveFileChooser.setSelectedFile(saveModalSelectedFile);
        // only show bmp files
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Bitmap (BMP)", "bmp"));
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("Text file (TXT)", "txt"));

        // open button
        JButton open = new JButton("Open");
        JFileChooser openFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // set default name
        openFileChooser.setSelectedFile(new File("drawing.txt"));
        // only show txt files
        openFileChooser.setFileFilter(new FileNameExtensionFilter("Text file (TXT)", "txt"));

        JButton quit = new JButton("Quit");
        JButton clear = new JButton("Clear");
        JButton autoDraw = new JButton("Auto");

        // create header panel and add components to it
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        headerPanel.add(new JLabel("Shape:"));
        headerPanel.add(comboBoxDrawingModes);
        headerPanel.add(new JLabel("Color:"));
        headerPanel.add(comboBoxDrawingColors);
        headerPanel.add(new JLabel("BG Color:"));
        headerPanel.add(comboBoxBackgroundColors);
        headerPanel.add(clear);
        headerPanel.add(autoDraw);
        headerPanel.add(save);
        headerPanel.add(open);
        headerPanel.add(quit);
        headerPanel.add(undo);
        headerPanel.add(redo);

        // Set undo and redo buttons to disabled by default
        undo.setEnabled(false);
        redo.setEnabled(false);

        // add header panel to JFrame
        this.add(headerPanel, BorderLayout.PAGE_START);

        // create drawing panel and add components to it
        drawingPanel = new DrawingPanel();
        this.add(drawingPanel, BorderLayout.CENTER);

        // Create BufferedImage
        bufferImg = new BufferedImage(windowWidth - (windowWidth / 46), windowHeight - (windowHeight / 7), BufferedImage.TYPE_INT_RGB);
        bufferG = bufferImg.getGraphics();
        bufferG.fillRect(0, 0, bufferImg.getWidth(), bufferImg.getHeight());

        // Define mouseListener for clear
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // clear drawings
                clear();
                // clear undo queue and set button to disabled
                cQ.undoList.clear();
                undo.setEnabled(false);
                // clear redo queue and set button to disabled
                cQ.redoList.clear();
                redo.setEnabled(false);
                // clear repaintList
                cQ.repaintList.clear();
            }
        });

        // Define mouseListener for autoDrawing
        autoDraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                autoDraw();
            }
        });

        // Define mouseListener for saving
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // init modal on button press and save its return value for further processing
                int returnValue = saveFileChooser.showSaveDialog(drawingPanel);

                // triggered when save button is pressed
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filename = saveFileChooser.getSelectedFile().toString();

                    // ensure that the file is saved as a bitmap or txt
                    if (filename.endsWith(".bmp") || filename.endsWith(".txt")) {
                        if (filename.endsWith(".bmp")) {
                            try {
                                writeImage(getDrawing(), filename);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        if (filename.endsWith(".txt")) {
                            File file = new File(filename);
                            try {
                                fileWriter[0] = new FileWriter(file, true);
                                fileWriter[0].write(String.valueOf(cQ.stringBuffer));
                                fileWriter[0].close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(drawingPanel,
                                "Drawings can only be saved as .bmp and .txt files",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        // Define mouseListener for opening past drawing
        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // init modal on button press and save its return value for further processing
                int returnValue = openFileChooser.showOpenDialog(drawingPanel);

                // triggered when save button is pressed
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File toBeReadFile = openFileChooser.getSelectedFile();
                    try {
                        Scanner input = new Scanner(toBeReadFile);

                        while (input.hasNextLine()) {
                            String line = input.nextLine();
                            readLineAndDrawDrawable(line);
                        }
                        input.close();

                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });

        // Define mouseListener for quitting
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        // Define mouseListener for undoing
        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cQ.undoLastDrawingAction();
            }
        });

        // Define mouseListener for redoing
        redo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cQ.redoLastDrawingAction();
            }
        });

        // Handle the window close request similarly
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Finally, set the size of the window, and pop it up
        this.setSize(windowWidth, windowHeight);
        this.setBackground(Color.white);
        // this.show(); //chg
        this.setVisible(true); // ++
    }

    public void updateCanvas() {
        drawingPanel.getGraphics().drawImage(bufferImg, -9, -67, null);
    }

    /**
     * API method: gets the height of the current window.
     *
     * @return height of window
     */
    public int getHeight() {
        return this.getSize().height;
    }

    /**
     * API method: change height of the window
     *
     * @param height to be set height greater than 0
     */
    public void setHeight(int height) {
        if (height > 0) {
            this.setSize(this.getWidth(), height);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Height not valid",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * API method: gets the width of the current window.
     *
     * @return width of window
     */
    public int getWidth() {
        return this.getSize().width;
    }

    /**
     * API method: change width of the window
     *
     * @param width to be set width greater than 0
     */
    public void setWidth(int width) {
        if (width > 0) {
            this.setSize(width, this.getHeight());
        } else {
            JOptionPane.showMessageDialog(null,
                    "Height not valid",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // custom exception
    private static class ColorException extends Exception {
        ColorException(String str) {
            super(str);
        }
    }

    /**
     * Helper method for mapping strings to colors
     *
     * @param color as String
     * @return color as Color
     */
    public Color colorSwitchHelper(String color) {
        String toLowerCaseColor = color.toLowerCase();
        String eliminatedWhiteSpaceColor = toLowerCaseColor.replace(" ", "");
        // Key-Value storage for possible colors
        HashMap<String, Color> stringToColorMap = new HashMap<>();
        stringToColorMap.put("white", Color.white);
        stringToColorMap.put("lightgray", Color.lightGray);
        stringToColorMap.put("gray", Color.gray);
        stringToColorMap.put("darkgray", Color.darkGray);
        stringToColorMap.put("black", Color.black);
        stringToColorMap.put("red", Color.red);
        stringToColorMap.put("pink", Color.pink);
        stringToColorMap.put("orange", Color.orange);
        stringToColorMap.put("yellow", Color.yellow);
        stringToColorMap.put("green", Color.green);
        stringToColorMap.put("magenta", Color.magenta);
        stringToColorMap.put("cyan", Color.cyan);
        stringToColorMap.put("blue", Color.blue);

        return stringToColorMap.get(eliminatedWhiteSpaceColor);
    }

    /**
     * API method: set current drawing color
     *
     * @param new_color the new drawing color to be set
     */
    public void setFGColor(String new_color) throws ColorException {
        String new_color_lowercase = new_color.toLowerCase();
        fgColor = colorSwitchHelper(new_color_lowercase);
    }

    /**
     * Helper method for mapping awt.Color.toStrings to colors
     *
     * @param awtColorRGB toString version of java.awt.Color
     * @return actual String of color
     */
    private String colorHashMapHelper(String awtColorRGB) {
        // Key-Value storage for possible java.awt colors
        HashMap<String, String> colorRGBValues = new HashMap<>();
        colorRGBValues.put("java.awt.Color[r=255,g=255,b=255]", "white");
        colorRGBValues.put("java.awt.Color[r=192,g=192,b=192]", "lightgray");
        colorRGBValues.put("java.awt.Color[r=128,g=128,b=128]", "gray");
        colorRGBValues.put("java.awt.Color[r=64,g=64,b=64]", "darkgray");
        colorRGBValues.put("java.awt.Color[r=0,g=0,b=0]", "black");
        colorRGBValues.put("java.awt.Color[r=255,g=0,b=0]", "red");
        colorRGBValues.put("java.awt.Color[r=255,g=175,b=175]", "pink");
        colorRGBValues.put("java.awt.Color[r=255,g=200,b=0]", "orange");
        colorRGBValues.put("java.awt.Color[r=255,g=255,b=0]", "yellow");
        colorRGBValues.put("java.awt.Color[r=0,g=255,b=0]", "green");
        colorRGBValues.put("java.awt.Color[r=255,g=0,b=255]", "magenta");
        colorRGBValues.put("java.awt.Color[r=0,g=255,b=255]", "cyan");
        colorRGBValues.put("java.awt.Color[r=0,g=0,b=255]", "blue");

        return colorRGBValues.get(awtColorRGB);
    }

    /**
     * A Method which compares two Images and returns a boolean.
     *
     * @param referenceImg Our Reference Image.
     * @param compareImg   The Image which is compared to the Reference Image.
     * @return returns true, if both images are the same Size and have the same colored Pixels at the same Points.
     */
    public boolean isImgSameAsReference(BufferedImage referenceImg, BufferedImage compareImg) {
        int referenceImgWidth = referenceImg.getWidth();
        int referenceImgHeight = referenceImg.getHeight();

        if (referenceImgHeight != compareImg.getHeight() || referenceImgWidth != compareImg.getWidth()) {
            return false;
        } else {
            for (int i = 0; i < referenceImgWidth; i++) {
                for (int j = 0; j < referenceImgHeight; j++) {
                    if (referenceImg.getRGB(i, j) != compareImg.getRGB(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * API method: get currently selected drawing color
     *
     * @return the currently selected drawing color
     */
    public String getFGColor() {
        return colorHashMapHelper(this.fgColor.toString());
    }

    /**
     * API method: set new background color
     *
     * @param new_color the new background color to be set
     */
    public void setBGColor(String new_color) throws ColorException {
        String new_color_lowercase = new_color.toLowerCase();
        setBackground(colorSwitchHelper(new_color_lowercase));
    }

    /**
     * API method: get currently selected background color
     *
     * @return String the currently selected background color
     */
    public String getBGColor() {
        return colorHashMapHelper(this.getBackground().toString());
    }

    /**
     * API method: get current drawing from canvas
     *
     * @return BufferedImage of current canvas
     */
    public Image getDrawing() {
        return bufferImg;
    }

    /**
     * API method: writeImage uses the write method of MYBMPFile to write the given image as a Windows bitmap file (*.bmp)
     *
     * @param img      the image to be written
     * @param filename the image's file name
     */
    public void writeImage(Image img, String filename) throws IOException {
        MyBMPFile.write(filename, img);
    }

    /**
     * API method: readImage uses the read method of MYBMPFile to read a Windows bitmap file (*.bmp)
     *
     * @param filename the image's file name
     * @return the image read
     */
    public Image readImage(String filename) throws IOException {
        return MyBMPFile.read(filename);
    }

    /**
     * API method: clears the contents of the bufferImg
     */
    public void clear() {
        bufferG.setColor(bgColor);
        bufferG.fillRect(0, 0, bufferImg.getWidth(), bufferImg.getHeight());
        drawingPanel.getGraphics().drawImage(bufferImg, -9, -67, null);
    }

    /**
     * API method: repaint the previously drawn drawables on BG color-change
     */
    public void repaintOnBackgroundChange() {
        // clear redoList so previously undone drawables are not considered
        cQ.repaintList.addAll(cQ.redoList);
        cQ.redoList.clear();
        // add all drawables from redoList to undoList
        cQ.redoList.addAll(cQ.undoList);
        // can both be done in one forEach loop?
        cQ.redoList.forEach(drawable ->
                drawable.setDrawingColor(drawable.getLegacyColor()));
        cQ.redoList.forEach(drawable ->
                drawable.draw(bufferG));
        // clear redoList so its empty
        cQ.redoList.clear();
        // add all drawables from repaintList to redoList
        cQ.redoList.addAll(cQ.repaintList);
        // clear repaintList for further color-changes
        cQ.repaintList.clear();
    }

    /**
     * API: paint a rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawRectangle(Point upper_left, Point lower_right) {
        Drawable rectangle = new RectangleDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, fgColor);
        cQ.addToRequestQueue(rectangle);
    }

    /**
     * API: paint a polyline/scribble on canvas
     *
     * @param points List of points to draw
     */
    public void drawPolyLine(java.util.List<Point> points) {
        ArrayList<Point> pointArrayList = (ArrayList<Point>) points;

        Drawable scribble = new ScribbleDrawer(pointArrayList, fgColor);
        cQ.addToRequestQueue(scribble);
    }


    /**
     * API: paint an oval automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawOval(Point upper_left, Point lower_right) {
        Drawable oval = new OvalDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, fgColor);
        cQ.addToRequestQueue(oval);
    }

    /**
     * API: paint a filled 3D-rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawFilled3DRectangle(Point upper_left, Point lower_right) {
        Drawable filled3DRect = new Filled3DRectDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, fgColor, true);
        cQ.addToRequestQueue(filled3DRect);
    }

    /**
     * API: paint a round rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawRoundRectangle(Point upper_left, Point lower_right) {
        Drawable roundRect = new RoundRectDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, fgColor, 50, 50);
        cQ.addToRequestQueue(roundRect);
    }

    /**
     * API: paint a triangle automatically on canvas
     *
     * @param startingPoint  the starting point for drawing the triangle
     * @param finishingPoint the finishing point for drawing the triangle
     */
    public void drawTriangle(Point startingPoint, Point finishingPoint) {
        Drawable triangle = new TriangleDrawer(startingPoint.x, startingPoint.y, finishingPoint.x, finishingPoint.y, fgColor);
        cQ.addToRequestQueue(triangle);
    }

    /**
     * API: paint a triangle automatically on canvas
     *
     * @param startingPoint  the starting point for drawing the triangle
     * @param finishingPoint the finishing point for drawing the triangle
     */
    public void drawIsoscelesTriangle(Point startingPoint, Point finishingPoint) {
        Drawable isoscelesTriangle = new IsoscelesTriangleDrawer(startingPoint.x, startingPoint.y, finishingPoint.x, finishingPoint.y, fgColor);
        cQ.addToRequestQueue(isoscelesTriangle);
    }


    /**
     * API - test method: automatically paint an image
     */
    public void autoDraw() {
        Color[] availableColorsArray = new Color[]{
                Color.white, Color.lightGray, Color.gray, Color.darkGray,
                Color.black, Color.red, Color.pink, Color.orange, Color.yellow,
                Color.green, Color.magenta, Color.cyan, Color.blue};
        int fakePI = (int) Math.PI;

        // for every available color from java.awt.Color draw all available "shapes"
        for (int x = 0; x < availableColorsArray.length; x++) {
            fgColor = availableColorsArray[x];
            int offset = fakePI * x;

            // auto draw rectangle
            drawRectangle(new Point(100 + offset, 100 + offset), new Point(210 + offset, 210 + offset));
            // auto draw oval
            drawOval(new Point(100 + offset, 100 + offset), new Point(210 + offset, 210 + offset));
            // auto draw filled 3D-rectangle
            drawFilled3DRectangle(new Point(3 * 100 + offset, 2 * 100 + offset), new Point(2 * 210 + offset, 2 * 210 + offset));
            // auto draw round rectangle
            drawRoundRectangle(new Point(100 + offset, 3 * 100 + offset), new Point(210 + offset, 2 * 210 + offset));
            // auto draw triangle
            drawTriangle(new Point(5 * 100 + offset, 2 * 100 + offset), new Point(3 * 210 + offset, 210 + offset));
            // auto draw isosceles triangle
            drawIsoscelesTriangle(new Point(3 * 100 + offset, 2 * 100 + offset), new Point(2 * 210 + offset, 2 * 210 + offset));

            List<Point> pointList = new ArrayList<>();
            // auto-fill pointList
            for (int y = 100; y < 210; y++) {
                pointList.add(new Point(y + offset, y + offset));
            }
            // auto draw points
            drawPolyLine(pointList);
        }
        // after autoDraw set back color to default
        fgColor = Color.black;
    }

    /**
     * @param line the to be read line of the input-buffer
     */
    public void readLineAndDrawDrawable(String line) {
        // stringArray of comma separated values previously delimited by a ","
        String[] separatedEntries = line.split("#");

        // init params for later function calls
        int arg1 = 0;
        int arg2 = 0;
        int arg3 = 0;
        int arg4 = 0;
        Color drawingColor = Color.black;

        // this needs to be done all other shapes except scribble use the same params
        if (!separatedEntries[0].equals("scribble")) {
            // parse all arguments needed for drawings into Integers
            arg1 = Integer.parseInt(separatedEntries[1]);
            arg2 = Integer.parseInt(separatedEntries[2]);
            arg3 = Integer.parseInt(separatedEntries[3]);
            arg4 = Integer.parseInt(separatedEntries[4]);
            drawingColor = colorSwitchHelper(colorHashMapHelper(separatedEntries[5]));
        }

        switch (separatedEntries[0]) {
            case "scribble":
                System.out.println(separatedEntries);
                System.out.println(separatedEntries[1]);
                //TODO extract points and call shapeManager.scribbleDrawerLogic with points for redraw
                break;
            case "rectangle":
                shapeManager.rectangleDrawerLogic.drawForRealNow(arg1, arg2, arg3, arg4, drawingColor);
                break;
            case "oval":
                shapeManager.ovalDrawerLogic.drawForRealNow(arg1, arg2, arg3, arg4, drawingColor);
                break;
            case "filled3drect":
                shapeManager.threeDRectDrawerLogic.drawForRealNow(arg1, arg2, arg3, arg4, drawingColor);
                break;
            case "roundrect":
                shapeManager.roundRectDrawerLogic.drawForRealNow(arg1, arg2, arg3, arg4, drawingColor);
                break;
            case "triangle":
                shapeManager.triangleDrawerLogic.drawForRealNow(arg1, arg2, arg3, arg4, drawingColor);
                break;
            case "isoscelestriangle":
                shapeManager.isoscelesTriangleDrawerLogic.drawForRealNow(arg1, arg2, arg3, arg4, drawingColor);
                break;
        }
    }
}
