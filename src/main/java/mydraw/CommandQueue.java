// Purpose.  Command design pattern - decoupling producer from consumer
package mydraw;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * simple test with command queue pattern
 * modified by ptp
 */
public class CommandQueue {
    List<Drawable> requestQueue;
    ArrayList<Drawable> undoList;

    public CommandQueue() {
        requestQueue = new LinkedList<Drawable>();
        undoList = new ArrayList<Drawable>();
    }

    public interface Drawable {
        void draw(Graphics g);
    }

    /**
     * Adds drawing to requests queue
     *
     * @param drawable Drawing
     */
    public void addToRequestQueue(Drawable drawable) {
        requestQueue.add(drawable);
        workOffRequests(requestQueue);
    }

    public static class ScribbleDrawer implements Drawable {
        private int startingPoint;
        private int endingPoint;


        public void draw(Graphics g) {
            System.out.println("take out the trash");
            //führ das aus
            //adde mich in die queue
        }
    }

    public static class RectangleDrawer implements Drawable {
        private int startingPointX;
        private int startingPointY;
        private int rectangleWidth;
        private int rectangleHeight;
        private Color drawingColor;

        public RectangleDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color) {
            startingPointX = Math.min(upperLeftX, lowerRightX);
            startingPointY = Math.min(upperLeftY, lowerRightY);
            rectangleWidth = Math.abs(lowerRightX - upperLeftX);
            rectangleHeight = Math.abs(lowerRightY - upperLeftY);
            drawingColor = color;
        }

        public void draw(Graphics g) {
            // set drawing color
            g.setColor(drawingColor);
            // draw rectangle
            g.drawRect(startingPointX, startingPointY, rectangleWidth, rectangleHeight);
        }
    }

    public static class OvalDrawer implements Drawable {
        public void draw(Graphics g) {
            System.out.println("sell the bugs, charge extra for the fixes");
        }
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
            drawable.draw(DrawGUI.bufferImg.getGraphics());

            //TODO DrawGUI.updateCanvas();

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
}
