package mydraw;

import java.awt.event.*;
import java.util.HashMap;

// this class determines how mouse events are to be interpreted,
// depending on the shape mode currently set
class ShapeManager implements ItemListener {
    DrawGUI gui;

    abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener {
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
            int x = e.getX(), y = e.getY();
            gui.bufferG.setColor(gui.color);
            gui.bufferG.setPaintMode();
            gui.bufferG.drawLine(lastx, lasty, x, y);
            lastx = x;
            lasty = y;
            // draw image from buffer to gui
            gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
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
            if (lastx != -1) {
                // first undraw a rubber rect
                gui.bufferG.setXORMode(gui.color);
                gui.bufferG.setColor(gui.getBackground());
                doDraw(pressx, pressy, lastx, lasty);
                lastx = -1;
                lasty = -1;
            }
            // these commands finish the rubberband mode
            gui.bufferG.setPaintMode();
            gui.bufferG.setColor(gui.color);
            // draw the final rectangle
            doDraw(pressx, pressy, e.getX(), e.getY());
        }

        // mouse released => temporarily set second corner of rectangle
        // draw the resulting shape in "rubber-band mode"
        public void mouseDragged(MouseEvent e) {
            // these commands set the rubberband mode
            gui.bufferG.setXORMode(gui.color);
            gui.bufferG.setColor(gui.getBackground());
            if (lastx != -1) {
                // first undraw previous rubber rect
                doDraw(pressx, pressy, lastx, lasty);
            }
            lastx = e.getX();
            lasty = e.getY();
            // draw new rubber rect
            doDraw(pressx, pressy, lastx, lasty);
        }

        public void doDraw(int x0, int y0, int x1, int y1) {
            // calculate upperleft and width/height of rectangle
            int x = Math.min(x0, x1);
            int y = Math.min(y0, y1);
            int w = Math.abs(x1 - x0);
            int h = Math.abs(y1 - y0);
            // draw rectangle
            gui.bufferG.drawRect(x, y, w, h);
            // draw image from buffer to gui
            gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
        }
    }

    // if this class is active, ovals are drawn
    class OvalDrawer extends RectangleDrawer {
        public void doDraw(int x0, int y0, int x1, int y1) {
            int x = Math.min(x0, x1);
            int y = Math.min(y0, y1);
            int w = Math.abs(x1 - x0);
            int h = Math.abs(y1 - y0);
            // draw oval instead of rectangle
            gui.bufferG.drawOval(x, y, w, h);
            // draw image from buffer to gui
            gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
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
        // Key-Value storage for possible colors
        HashMap<String, ShapeDrawer> itemToDrawer = new HashMap<>();
        itemToDrawer.put("Scribble", scribbleDrawer);
        itemToDrawer.put("Rectangle", rectDrawer);
        itemToDrawer.put("Oval", ovalDrawer);
        setCurrentDrawer(itemToDrawer.get(e.getItem().toString()));
    }
}
