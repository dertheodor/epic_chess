package mydraw;

import java.awt.event.MouseEvent;

// if this class is active, the mouse is interpreted as a pen
class ScribDrawer extends ShapeDrawer {
    int lastx, lasty;
    DrawGUI gui;
    CommandQueue cQ;

    public ScribDrawer(DrawGUI itsGui, CommandQueue coQ) {
        gui = itsGui;
        cQ = coQ;
    }

    public void mousePressed(MouseEvent e) {
        lastx = e.getX();
        lasty = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        Drawable scribble = new ScribbleDrawer(lastx, lasty, x, y, gui.color);
        lastx = x;
        lasty = y;
        // create rectangle
        // add rectangle to queue
        cQ.addToRequestQueue(scribble);
        // draw image from buffer to gui
        //gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
    }
}
