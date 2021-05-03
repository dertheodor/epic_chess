package mydraw;

import java.awt.event.MouseEvent;


// if this class is active, rectangles are drawn
class RectDrawer extends ShapeDrawer {
    DrawGUI gui;
    int pressx, pressy;
    int lastx = -1, lasty = -1;

    public RectDrawer(DrawGUI itsGui) {
        gui = itsGui;
    }

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
        //doDraw(pressx, pressy, e.getX(), e.getY());
        drawForRealNow(pressx, pressy, e.getX(), e.getY());
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

    public void drawForRealNow(int x0, int y0, int x1, int y1) {
        new CommandQueue.RectangleDrawer(x0, y0, x1, y1).draw(gui.bufferG);

    }
}

