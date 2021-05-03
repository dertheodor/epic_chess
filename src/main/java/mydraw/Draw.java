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
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    Color color;
    BufferedImage bufferImg;
    Graphics bufferG;
    JPanel drawingPanel;
    int windowWidth;
    int windowHeight;

    // init ShapeManager
    ShapeManager shapeManager;

    /**
     * The GUI constructor does all the work of creating the GUI and setting
     * up event listeners.  Note the use of local and anonymous classes.
     */
    public DrawGUI(Draw application) {
        super("Draw");        // Create the window
        app = application;    // Remember the application reference
        color = Color.black;  // the current drawing color
        windowWidth = 550;
        windowHeight = 440;

        // instantiate ShapeManager
        shapeManager = new ShapeManager(this);

        // Set a LayoutManager, and add the choosers and buttons to the window.
        this.setLayout(new BorderLayout());

        // drawing modes and its JComboBox
        String[] shapes = {"Scribble", "Rectangle", "Oval"};
        JComboBox<String> comboBoxDrawingModes = new JComboBox<>(shapes);
        comboBoxDrawingModes.addItemListener(shapeManager);

        // drawing colors and its JComboBox
        String[] colors = {
                "Black", "White", "Orange", "Cyan", "Yellow", "Green", "Red", "Blue", "Dark Gray", "Gray", "Light Gray"};
        JComboBox<String> comboBoxDrawingColors = new JComboBox<>(colors);
        class ColorItemListener implements ItemListener {

            // user selected new color => store new color in DrawGUIs
            public void itemStateChanged(ItemEvent e) {
                color = colorSwitchHelper(e.getItem().toString());
            }
        }
        comboBoxDrawingColors.addItemListener(new ColorItemListener());

        // header JButtons
        JButton clear = new JButton("Clear");
        JButton autoDraw = new JButton("Auto");
        JButton save = new JButton("Save");
        JButton quit = new JButton("Quit");

        // create header panel and add components to it
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        headerPanel.add(new JLabel("Shape:"));
        headerPanel.add(comboBoxDrawingModes);
        headerPanel.add(new JLabel("Color:"));
        headerPanel.add(comboBoxDrawingColors);
        headerPanel.add(clear);
        headerPanel.add(autoDraw);
        headerPanel.add(save);
        headerPanel.add(quit);

        // add header panel to JFrame
        this.add(headerPanel, BorderLayout.PAGE_START);

        // create drawing panel and add components to it
        drawingPanel = new JPanel();
        this.add(drawingPanel, BorderLayout.CENTER);

        // Create BufferedImage
        bufferImg = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        bufferG = bufferImg.getGraphics();
        bufferG.fillRect(0, 0, bufferImg.getWidth(), bufferImg.getHeight());

        // Define mouseListener for saving
        // init imageCounter so multiple files can be saved
        final int[] imageCounter = {0};
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    imageCounter[0]++;
                    writeImage(getDrawing(), "drawing" + imageCounter[0] + ".bmp");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // Define mouseListener for autoDrawing
        autoDraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                autoDraw();
            }
        });

        // Define mouseListener for clear
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clear();
            }
        });

        // Define mouseListener for quitting
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
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

    public void updateCanvas(Graphics bufferG) {
        drawingPanel.getGraphics();

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
            //TODO Add update for Buffer
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
            //TODO Add update for Buffer
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
        color = colorSwitchHelper(new_color_lowercase);
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
        return colorHashMapHelper(this.color.toString());
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
        Graphics bufferImgGraphics = bufferImg.getGraphics();
        bufferImgGraphics.setColor(Color.white);
        bufferImgGraphics.fillRect(0, 0, bufferImg.getWidth(), bufferImg.getHeight());
        drawingPanel.getGraphics().drawImage(bufferImg, -9, -67, null);
    }

    /**
     * API - test method: automatically paint an image
     */
    public void autoDraw() {
        // auto draw rectangle
        drawRectangle(new Point(100, 100), new Point(250, 250));
        // auto draw oval
        drawOval(new Point(100, 100), new Point(250, 250));

        List<Point> pointList = new ArrayList<>();
        // auto-fill pointList
        for (int i = 100; i < 250; i++) {
            pointList.add(new Point(i, i));
        }
        // auto draw points
        drawPolyLine(pointList);
    }


    /**
     * API: paint a rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawRectangle(Point upper_left, Point lower_right) {
        // calculate width/height of rectangle
        int w = lower_right.x - upper_left.x;
        int h = lower_right.y - upper_left.y;
        // set color
        bufferG.setColor(this.color);
        // draw rectangle
        bufferG.drawRect(upper_left.x, upper_left.y, w, h);
        // draw image from buffer to gui
        drawingPanel.getGraphics().drawImage(bufferImg, -9, -67, null);
    }

    /**
     * API: paint an oval automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawOval(Point upper_left, Point lower_right) {
        // calculate width/height of rectangle
        int w = lower_right.x - upper_left.x;
        int h = lower_right.y - upper_left.y;
        // set color
        bufferG.setColor(this.color);
        // draw rectangle
        bufferG.drawOval(upper_left.x, upper_left.y, w, h);
        // draw image from buffer to gui
        drawingPanel.getGraphics().drawImage(bufferImg, -9, -67, null);
    }

    /**
     * API: paint a polyline/scribble on canvas
     *
     * @param points List of points to draw
     */
    public void drawPolyLine(java.util.List<Point> points) {
        // iterate over all points
        for (int i = 0; i < points.size() - 1; i++) {
            bufferG.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
        }
        // set color
        bufferG.setColor(this.color);
        // draw image from buffer to gui
        drawingPanel.getGraphics().drawImage(bufferImg, -9, -67, null);
    }
}
