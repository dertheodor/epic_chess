package mydraw;

import java.awt.event.MouseEvent;

// if this class is active, the mouse is interpreted as a pen
class ScribbleDrawer extends ShapeDrawer {
    int lastx, lasty;
    DrawGUI gui;

    public ScribbleDrawer(DrawGUI itsGui) {
        gui = itsGui;
    }

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
