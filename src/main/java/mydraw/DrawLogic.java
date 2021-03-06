package mydraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// if this class is active, the mouse is interpreted as a pen
class ScribbleDrawerLogic extends ShapeDrawer {
    int lastx, lasty;
    DrawGUI gui;
    CommandQueue cQ;
    ArrayList<Point> pointArrayList;

    public ScribbleDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        gui = itsGui;
        cQ = coQ;
    }

    public void mousePressed(MouseEvent e) {
        lastx = e.getX();
        lasty = e.getY();
        pointArrayList = new ArrayList<>();
        pointArrayList.add(new Point(lastx, lasty));
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        // draw points whilst in "rubberband" mode
        gui.bufferG.setColor(gui.fgColor);
        gui.bufferG.drawLine(lastx, lasty, x, y);
        gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);

        lastx = x;
        lasty = y;
        pointArrayList.add(new Point(lastx, lasty));
    }

    public void mouseReleased(MouseEvent e) {
        // undraw "rubberband" line which was drawn whilst dragging mouse
        gui.bufferG.setColor(gui.getBackground());
        drawForRealNow(pointArrayList, gui.fgColor);
    }

    public void drawForRealNow(ArrayList<Point> pointArrayList, Color fgColor) {
        // iterate trough drawn points and draw them
        for (int i = 0; i < pointArrayList.size() - 1; i++) {
            gui.bufferG.drawLine(pointArrayList.get(i).x, pointArrayList.get(i).y, pointArrayList.get(i + 1).x, pointArrayList.get(i + 1).y);
        }

        // create scribble
        Drawable scribble = new ScribbleDrawer(pointArrayList, fgColor);
        // add scribble to queue
        cQ.addToRequestQueue(scribble);
    }
}

// if this class is active, rectangles are drawn
class RectangleDrawerLogic extends ShapeDrawer {
    DrawGUI gui;
    CommandQueue cQ;
    int pressx, pressy;
    int lastx = -1, lasty = -1;

    public RectangleDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        gui = itsGui;
        cQ = coQ;
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
            gui.bufferG.setXORMode(gui.fgColor);
            gui.bufferG.setColor(gui.getBackground());
            doDraw(pressx, pressy, lastx, lasty);
            lastx = -1;
            lasty = -1;
        }
        // these commands finish the rubberband mode
        gui.bufferG.setPaintMode();
        gui.bufferG.setColor(gui.fgColor);
        // draw the final rectangle
        //doDraw(pressx, pressy, e.getX(), e.getY());
        drawForRealNow(pressx, pressy, e.getX(), e.getY(), gui.fgColor);
    }

    // mouse released => temporarily set second corner of rectangle
    // draw the resulting shape in "rubber-band mode"
    public void mouseDragged(MouseEvent e) {
        // these commands set the rubberband mode
        gui.bufferG.setXORMode(gui.fgColor);
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

    public void drawForRealNow(int x0, int y0, int x1, int y1, Color fgColor) {
        // create rectangle
        Drawable rectangle = new RectangleDrawer(x0, y0, x1, y1, fgColor);
        // add rectangle to queue
        cQ.addToRequestQueue(rectangle);
    }
}

// if this class is active, ovals are drawn
class OvalDrawerLogic extends RectangleDrawerLogic {
    DrawGUI gui;
    CommandQueue cQ;

    public OvalDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        super(itsGui, coQ);
        gui = itsGui;
        cQ = coQ;
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

    @Override
    public void drawForRealNow(int x0, int y0, int x1, int y1, Color fgColor) {
        // create oval
        Drawable oval = new OvalDrawer(x0, y0, x1, y1, fgColor);
        // add oval to queue
        cQ.addToRequestQueue(oval);
    }
}

// if this class is active, filled 3DRects are drawn
class Filled3DRectDrawerLogic extends RectangleDrawerLogic {
    DrawGUI gui;
    CommandQueue cQ;

    public Filled3DRectDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        super(itsGui, coQ);
        gui = itsGui;
        cQ = coQ;
    }

    public void doDraw(int x0, int y0, int x1, int y1) {
        int x = Math.min(x0, x1);
        int y = Math.min(y0, y1);
        int w = Math.abs(x1 - x0);
        int h = Math.abs(y1 - y0);
        // draw filled 3DRect instead of rectangle
        gui.bufferG.fill3DRect(x, y, w, h, true);
        // draw image from buffer to gui
        gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
    }

    @Override
    public void drawForRealNow(int x0, int y0, int x1, int y1, Color fgColor) {
        // create threeDRect
        Drawable threeDRect = new Filled3DRectDrawer(x0, y0, x1, y1, fgColor, true);
        // add threeDRect to queue
        cQ.addToRequestQueue(threeDRect);
    }
}

// if this class is active, round rectangles are drawn
class RoundRectDrawerLogic extends RectangleDrawerLogic {
    DrawGUI gui;
    CommandQueue cQ;

    public RoundRectDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        super(itsGui, coQ);
        gui = itsGui;
        cQ = coQ;
    }

    public void doDraw(int x0, int y0, int x1, int y1) {
        int x = Math.min(x0, x1);
        int y = Math.min(y0, y1);
        int w = Math.abs(x1 - x0);
        int h = Math.abs(y1 - y0);
        // draw roundRect instead of rectangle
        gui.bufferG.drawRoundRect(x, y, w, h, 50, 50);
        // draw image from buffer to gui
        gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
    }

    @Override
    public void drawForRealNow(int x0, int y0, int x1, int y1, Color fgColor) {
        // create roundRect
        Drawable threeDRect = new RoundRectDrawer(x0, y0, x1, y1, fgColor, 50, 50);
        // add roundRect to queue
        cQ.addToRequestQueue(threeDRect);
    }
}

// if this class is active, triangles are drawn
class TriangleDrawerLogic extends RectangleDrawerLogic {
    DrawGUI gui;
    CommandQueue cQ;

    public TriangleDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        super(itsGui, coQ);
        gui = itsGui;
        cQ = coQ;
    }

    public void doDraw(int x0, int y0, int x1, int y1) {
        // calculate height of triangle
        int heightX = (x0 + (x1 - x0) / 2);
        int heightY = y0 - x1 + x0;
        // first int-array represents the set of x values, second int-array represents the set of y values
        gui.bufferG.drawPolygon(new int[]{x0, x1, heightX}, new int[]{y0, y1, heightY}, 3);
        // draw image from buffer to gui
        gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
    }

    @Override
    public void drawForRealNow(int x0, int y0, int x1, int y1, Color fgColor) {
        // create oval
        Drawable triangle = new TriangleDrawer(x0, y0, x1, y1, fgColor);
        // add oval to queue
        cQ.addToRequestQueue(triangle);
    }
}

// if this class is active, isosceles triangles are drawn
class IsoscelesTriangleDrawerLogic extends RectangleDrawerLogic {
    DrawGUI gui;
    CommandQueue cQ;

    public IsoscelesTriangleDrawerLogic(DrawGUI itsGui, CommandQueue coQ) {
        super(itsGui, coQ);
        gui = itsGui;
        cQ = coQ;
    }

    public void doDraw(int x0, int y0, int x1, int y1) {
        // calculate height of triangle
        int heightX = (x0 + (x1 - x0) / 2);
        int heightY = y0 - x1 + x0;
        // first int-array represents the set of x values, second int-array represents the set of y values
        gui.bufferG.drawPolygon(new int[]{x0, x1, heightX}, new int[]{y0, y0, heightY}, 3);
        // draw image from buffer to gui
        gui.drawingPanel.getGraphics().drawImage(gui.bufferImg, -9, -67, null);
    }

    @Override
    public void drawForRealNow(int x0, int y0, int x1, int y1, Color fgColor) {
        // create oval
        Drawable isoscelesTriangle = new IsoscelesTriangleDrawer(x0, y0, x1, y1, fgColor);
        // add oval to queue
        cQ.addToRequestQueue(isoscelesTriangle);
    }
}
