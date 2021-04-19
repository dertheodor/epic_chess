package mydraw;
// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

// *** modified by PTP 04/2020
// *** minimal changes from AWT to Swing -> replace elements/classes
// *** behavior is similiar but not equal ! (Why?)

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;  //++

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

    protected JFrame window; //chg

    /**
     * This is the application method that processes commands sent by the GUI
     */
    public void doCommand(String command) {
        if (command.equals("clear")) {          // clear the GUI window
            // It would be more modular to include this functionality in the GUI
            // class itself.  But for demonstration purposes, we do it here.
            Graphics g = window.getGraphics();
            g.setColor(window.getBackground());
            g.fillRect(0, 0, window.getSize().width, window.getSize().height);
        } else if (command.equals("quit")) {      // quit the application
            window.dispose();                         // close the GUI
            System.exit(0);                           // and exit.
        }
    }
}

/**
 * This class implements the GUI for our application
 */
class DrawGUI extends JFrame {
    Draw app;      // A reference to the application, to send commands to.
    Color color;

    /**
     * The GUI constructor does all the work of creating the GUI and setting
     * up event listeners.  Note the use of local and anonymous classes.
     */
    public DrawGUI(Draw application) {
        super("Draw");        // Create the window
        app = application;    // Remember the application reference
        color = Color.black;  // the current drawing color

        // selector for drawing modes
        Choice shape_chooser = new Choice();
        shape_chooser.add("Scribble");
        shape_chooser.add("Rectangle");
        shape_chooser.add("Oval");

        // selector for drawing colors
        Choice color_chooser = new Choice();
        color_chooser.add("Black");
        color_chooser.add("Green");
        color_chooser.add("Red");
        color_chooser.add("Blue");

        // Create two buttons
        JButton clear = new JButton("Clear");
        JButton quit = new JButton("Quit");

        // Set a LayoutManager, and add the choosers and buttons to the window.
        this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        this.add(new JLabel("Shape:"));
        this.add(shape_chooser);
        this.add(new JLabel("Color:"));
        this.add(color_chooser);
        this.add(clear);
        this.add(quit);

        // Here's a local class used for action listeners for the buttons
        class DrawActionListener implements ActionListener {
            private String command;

            public DrawActionListener(String cmd) {
                command = cmd;
            }

            public void actionPerformed(ActionEvent e) {
                app.doCommand(command);
            }
        }

        // Define action listener adapters that connect the  buttons to the app
        clear.addActionListener(new DrawActionListener("clear"));
        quit.addActionListener(new DrawActionListener("quit"));

        // this class determines how mouse events are to be interpreted,
        // depending on the shape mode currently set
        class ShapeManager implements ItemListener {
            DrawGUI gui;

            abstract class ShapeDrawer
                    extends MouseAdapter implements MouseMotionListener {
                public void mouseMoved(MouseEvent e) { /* ignore */ }
            }

            // if this class is active, the mouse is interpreted as a pen
            class ScribbleDrawer extends ShapeDrawer {
                int lastx, lasty;

                public void mousePressed(MouseEvent e) {
                    lastx = e.getX();
                    lasty = e.getY();
                }

                public void mouseDragged(MouseEvent e) {
                    Graphics g = gui.getGraphics();
                    int x = e.getX(), y = e.getY();
                    g.setColor(gui.color);
                    g.setPaintMode();
                    g.drawLine(lastx, lasty, x, y);
                    lastx = x;
                    lasty = y;
                }
            }

            // if this class is active, rectangles are drawn
            class RectangleDrawer extends ShapeDrawer {
                int pressx, pressy;
                int lastx = -1, lasty = -1;

                // mouse pressed => fix first corner of rectangle
                public void mousePressed(MouseEvent e) {
                    pressx = e.getX();
                    pressy = e.getY();
                }

                // mouse released => fix second corner of rectangle
                // and draw the resulting shape
                public void mouseReleased(MouseEvent e) {
                    Graphics g = gui.getGraphics();
                    if (lastx != -1) {
                        // first undraw a rubber rect
                        g.setXORMode(gui.color);
                        g.setColor(gui.getBackground());
                        doDraw(pressx, pressy, lastx, lasty, g);
                        lastx = -1;
                        lasty = -1;
                    }
                    // these commands finish the rubberband mode
                    g.setPaintMode();
                    g.setColor(gui.color);
                    // draw the finel rectangle
                    doDraw(pressx, pressy, e.getX(), e.getY(), g);
                }

                // mouse released => temporarily set second corner of rectangle
                // draw the resulting shape in "rubber-band mode"
                public void mouseDragged(MouseEvent e) {
                    Graphics g = gui.getGraphics();
                    // these commands set the rubberband mode
                    g.setXORMode(gui.color);
                    g.setColor(gui.getBackground());
                    if (lastx != -1) {
                        // first undraw previous rubber rect
                        doDraw(pressx, pressy, lastx, lasty, g);

                    }
                    lastx = e.getX();
                    lasty = e.getY();
                    // draw new rubber rect
                    doDraw(pressx, pressy, lastx, lasty, g);
                }

                public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
                    // calculate upperleft and width/height of rectangle
                    int x = Math.min(x0, x1);
                    int y = Math.min(y0, y1);
                    int w = Math.abs(x1 - x0);
                    int h = Math.abs(y1 - y0);
                    // draw rectangle
                    g.drawRect(x, y, w, h);
                }
            }

            // if this class is active, ovals are drawn
            class OvalDrawer extends RectangleDrawer {
                public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
                    int x = Math.min(x0, x1);
                    int y = Math.min(y0, y1);
                    int w = Math.abs(x1 - x0);
                    int h = Math.abs(y1 - y0);
                    // draw oval instead of rectangle
                    g.drawOval(x, y, w, h);
                }
            }

            ScribbleDrawer scribbleDrawer = new ScribbleDrawer();
            RectangleDrawer rectDrawer = new RectangleDrawer();
            OvalDrawer ovalDrawer = new OvalDrawer();
            ShapeDrawer currentDrawer;

            public ShapeManager(DrawGUI itsGui) {
                gui = itsGui;
                // default: scribble mode
                currentDrawer = scribbleDrawer;
                // activate scribble drawer
                gui.addMouseListener(currentDrawer);
                gui.addMouseMotionListener(currentDrawer);
            }

            // reset the shape drawer
            public void setCurrentDrawer(ShapeDrawer l) {
                if (currentDrawer == l)
                    return;

                // deactivate previous drawer
                gui.removeMouseListener(currentDrawer);
                gui.removeMouseMotionListener(currentDrawer);
                // activate new drawer
                currentDrawer = l;
                gui.addMouseListener(currentDrawer);
                gui.addMouseMotionListener(currentDrawer);
            }

            // user selected new shape => reset the shape mode
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem().equals("Scribble")) {
                    setCurrentDrawer(scribbleDrawer);
                } else if (e.getItem().equals("Rectangle")) {
                    setCurrentDrawer(rectDrawer);
                } else if (e.getItem().equals("Oval")) {
                    setCurrentDrawer(ovalDrawer);
                }
            }
        }

        shape_chooser.addItemListener(new ShapeManager(this));

        class ColorItemListener implements ItemListener {

            // user selected new color => store new color in DrawGUIs
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem().equals("Black")) {
                    color = Color.black;
                } else if (e.getItem().equals("Green")) {
                    color = Color.green;
                } else if (e.getItem().equals("Red")) {
                    color = Color.red;
                } else if (e.getItem().equals("Blue")) {
                    color = Color.blue;
                }
            }
        }

        color_chooser.addItemListener(new ColorItemListener());

        // Handle the window close request similarly
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                app.doCommand("quit");
            }
        });

        // Finally, set the size of the window, and pop it up
        this.setSize(500, 400);
        this.setBackground(Color.white);
        // this.show(); //chg
        this.setVisible(true); // ++
    }

    /* API method stubs to be imported, commented and implemented in Draw.java
    -- first part --
    AH, PTP 2020
    */

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
    public Color colorSwitchHelper(String color) throws ColorException {
        switch (color) {
            case "white":
                return Color.white;
            case "lightgray":
                return Color.lightGray;
            case "gray":
                return Color.gray;
            case "darkgray":
                return Color.darkGray;
            case "black":
                return Color.black;
            case "red":
                return Color.red;
            case "pink":
                return Color.pink;
            case "orange":
                return Color.orange;
            case "yellow":
                return Color.yellow;
            case "green":
                return Color.green;
            case "magenta":
                return Color.magenta;
            case "cyan":
                return Color.cyan;
            case "blue":
                return Color.blue;
            default:
                throw new ColorException(color + " not available, please choose another color.");
        }
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
     * Helper method for mapping strings to colors
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
     * @return the currently selected background color
     */
    public String getBGColor() {
        return colorHashMapHelper(this.getBackground().toString());
    }

    /**
     * API method: get current drawing from canvas
     *
     * @return BufferedImage of current canvas
     */
    // TODO is this correct?
    public Image getDrawing() {
        return new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
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
//

    /**
     * API method: clear ...
     */
    public void clear() {
        Graphics g = this.getGraphics();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    }
//
//    /**
//     * API - test method: paint every shape ...
//     */
//    public void autoDraw() {
//        // do it ...
//        // paint your testimage now using API methods
//    }
//
//
//    /**
//     * API: paint a rectangle ...
//     */
//    public void drawRectangle(Point upper_left, Point lower_right) {
//        // do it ...
//    }
//
//    /**
//     * API: paint an oval ...
//     */
//    public void drawOval(Point upper_left, Point lower_right) {
//        // do it ...
//    }
//
//    /**
//     * API: paint a polyline/scribble ...
//     */
//    public void drawPolyLine(java.util.List<Point> points) {
//        // do it ...
//    }
}
