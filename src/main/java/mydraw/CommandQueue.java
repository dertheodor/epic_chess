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
    DrawGUI drawGUI;

    public CommandQueue(DrawGUI itsGui) {
        requestQueue = new LinkedList<Drawable>();
        undoList = new ArrayList<Drawable>();
        drawGUI = itsGui;
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
}
