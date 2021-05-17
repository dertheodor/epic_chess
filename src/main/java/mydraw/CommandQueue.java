// Purpose.  Command design pattern - decoupling producer from consumer
package mydraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * simple test with command queue pattern
 * modified by ptp
 */
public class CommandQueue {
    List<Drawable> requestQueue;
    ArrayList<Drawable> undoList;
    ArrayList<Drawable> redoList;
    DrawGUI drawGUI;

    public CommandQueue(DrawGUI itsGui) {
        requestQueue = new LinkedList<Drawable>();
        undoList = new ArrayList<Drawable>();
        redoList = new ArrayList<Drawable>();
        drawGUI = itsGui;

        // Define mouseListener for undoing
        drawGUI.undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                undoLastDrawingAction();
            }
        });

        // Define mouseListener for redoing
        drawGUI.redo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                redoLastDrawingAction();
            }
        });

        // Define mouseListener for clear
        drawGUI.clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // clear drawings TODO maybe make clear() so it also can be undone
                drawGUI.clear();
                // clear undo queue and set button to disabled
                undoList.clear();
                drawGUI.undo.setEnabled(false);
                // clear redo queue and set button to disabled
                redoList.clear();
                drawGUI.redo.setEnabled(false);
            }
        });

        // Define mouseListener for autoDrawing
        drawGUI.autoDraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                autoDraw();
            }
        });
    }


    /**
     * Undoes the last drawn Drawable
     */
    public void undoLastDrawingAction() {
        if (undoList.size() > 0) {
            Drawable undoOperation = undoList.get(undoList.size() - 1);
            undoOperation.setDrawingColor(drawGUI.bgColor);
            undoOperation.draw(drawGUI.bufferG);
            drawGUI.updateCanvas();

            redoList.add(undoOperation);
            // enable redo button as redoList has element in it now
            drawGUI.redo.setEnabled(true);

            undoList.remove(undoOperation);

            // disable undo button as undoList is empty now
            if (undoList.size() == 0) {
                drawGUI.undo.setEnabled(false);
            }
        } else {
            // disable undo button as undoList is empty now
            drawGUI.undo.setEnabled(false);
        }
    }

    /**
     * Redoes the last undo action
     */
    public void redoLastDrawingAction() {
        if (redoList.size() > 0) {
            Drawable latestDrawable = redoList.get(redoList.size() - 1);
            latestDrawable.setDrawingColor(latestDrawable.getLegacyColor());
            latestDrawable.draw(drawGUI.bufferG);
            drawGUI.updateCanvas();

            undoList.add(latestDrawable);
            // enable undo button as undoList has element in it now
            drawGUI.undo.setEnabled(true);

            redoList.remove(latestDrawable);

            // disable redo button as redoList is empty now
            if (redoList.size() == 0) {
                drawGUI.redo.setEnabled(false);
            }
        } else {
            // disable redo button as redList is empty now
            drawGUI.redo.setEnabled(false);
        }
    }


    /**
     * Adds drawing to requests queue
     *
     * @param drawable Drawing
     */
    public void addToRequestQueue(Drawable drawable) {
        // enable undo button when first drawable is created
        drawGUI.undo.setEnabled(true);
        requestQueue.add(drawable);
        workOffRequests(requestQueue);
    }

    //    public static List<Drawable> produceRequests() {
//        List<Drawable> queue = new ArrayList<Drawable>();
//        //queue.add(new ScribbleDrawer());
//        queue.add(new RectangleDrawer());
//        //queue.add(new OvalDrawer());
//
//        return queue;
//    }
//
    public void workOffRequests(List<Drawable> queue) {
        for (Drawable drawable : queue) {
            // draw drawable object
            drawable.draw(drawGUI.bufferG);
            drawGUI.updateCanvas();
            // add drawable to undoList
            undoList.add(drawable);
            // remove drawable from requestQueue
            requestQueue.remove(drawable);
        }
    }
//
//    public static void main(String[] args) {
//        List<Drawable> queue = produceRequests();
//        workOffRequests(queue);
//    }

    /**
     * API: paint a rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawRectangle(Point upper_left, Point lower_right) {
        Drawable rectangle = new RectangleDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, drawGUI.fgColor);
        addToRequestQueue(rectangle);
    }

    /**
     * API: paint a polyline/scribble on canvas
     *
     * @param points List of points to draw
     */
    public void drawPolyLine(java.util.List<Point> points) {
        ArrayList<Point> pointArrayList = (ArrayList<Point>) points;

        Drawable scribble = new ScribbleDrawer(pointArrayList, drawGUI.fgColor);
        addToRequestQueue(scribble);
    }


    /**
     * API: paint an oval automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawOval(Point upper_left, Point lower_right) {
        Drawable oval = new OvalDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, drawGUI.fgColor);
        addToRequestQueue(oval);
    }

    /**
     * API: paint a filled 3D-rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawFilled3DRectangle(Point upper_left, Point lower_right) {
        Drawable filled3DRect = new Filled3DRectDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, drawGUI.fgColor, true);
        addToRequestQueue(filled3DRect);
    }

    /**
     * API: paint a round rectangle automatically on canvas
     *
     * @param upper_left  the upper left point of the rectangle
     * @param lower_right the lower right point of the rectangle
     */
    public void drawRoundRectangle(Point upper_left, Point lower_right) {
        Drawable roundRect = new RoundRectDrawer(upper_left.x, upper_left.y, lower_right.x, lower_right.y, drawGUI.fgColor, 50, 50);
        addToRequestQueue(roundRect);
    }

    /**
     * API: paint a triangle automatically on canvas
     *
     * @param startingPoint  the starting point for drawing the triangle
     * @param finishingPoint the finishing point for drawing the triangle
     */
    public void drawTriangle(Point startingPoint, Point finishingPoint) {
        Drawable triangle = new TriangleDrawer(startingPoint.x, startingPoint.y, finishingPoint.x, finishingPoint.y, drawGUI.fgColor);
        addToRequestQueue(triangle);
    }

    /**
     * API: paint a triangle automatically on canvas
     *
     * @param startingPoint  the starting point for drawing the triangle
     * @param finishingPoint the finishing point for drawing the triangle
     */
    public void drawIsoscelesTriangle(Point startingPoint, Point finishingPoint) {
        Drawable isoscelesTriangle = new IsoscelesTriangleDrawer(startingPoint.x, startingPoint.y, finishingPoint.x, finishingPoint.y, drawGUI.fgColor);
        addToRequestQueue(isoscelesTriangle);
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
            drawGUI.fgColor = availableColorsArray[x];
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
        drawGUI.fgColor = Color.black;
    }
}
