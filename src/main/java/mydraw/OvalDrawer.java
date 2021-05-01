package mydraw;

// if this class is active, ovals are drawn
class OvalDrawer extends RectangleDrawer {
    DrawGUI gui;

    public OvalDrawer(DrawGUI itsGui) {
        super(itsGui);
        gui = itsGui;
    }

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

