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
    JButton undo;
    JButton redo;

    public CommandQueue(DrawGUI itsGui, JButton undoReference, JButton redoReference) {
        requestQueue = new LinkedList<Drawable>();
        undoList = new ArrayList<Drawable>();
        redoList = new ArrayList<Drawable>();
        drawGUI = itsGui;
        undo = undoReference;
        redo = redoReference;

        // Define mouseListener for undoing
        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                undoLastDrawingAction();
            }
        });

        // Define mouseListener for redoing
        redo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                redoLastDrawingAction();
            }
        });

    }


    /**
     * TODO
     */
    private void undoLastDrawingAction() {
        if (undoList.size() > 0) {
            Drawable undoOperation = undoList.get(undoList.size() - 1);
            undoOperation.setDrawingColor(drawGUI.getBackground());
            undoOperation.draw(drawGUI.bufferG);
            drawGUI.updateCanvas();
            redoList.add(undoOperation);
            undoList.remove(undoOperation);

        } else {
            System.out.println("Theo stinkt");
        }
    }

    /**
     *
     */
    private void redoLastDrawingAction() {
        if (redoList.size() > 0) {
            Drawable latestDrawable = redoList.get(redoList.size() - 1);
            latestDrawable.setDrawingColor(latestDrawable.getLegacyColor());
            latestDrawable.draw(drawGUI.bufferG);
            drawGUI.updateCanvas();
            undoList.add(latestDrawable);
            redoList.remove(latestDrawable);
        } else {
            System.out.println("Theo stinkt");
        }
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
}
