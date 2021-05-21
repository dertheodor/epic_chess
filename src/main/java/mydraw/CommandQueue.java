// Purpose.  Command design pattern - decoupling producer from consumer
package mydraw;

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
    ArrayList<Drawable> repaintList;
    DrawGUI drawGUI;
    StringBuffer stringBuffer = new StringBuffer();

    public CommandQueue(DrawGUI itsGui) {
        requestQueue = new LinkedList<Drawable>();
        undoList = new ArrayList<Drawable>();
        redoList = new ArrayList<Drawable>();
        repaintList = new ArrayList<Drawable>();
        drawGUI = itsGui;
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
            // disable redo button as redoList is empty now
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

    /**
     * @param queue the to be worked off queue
     */
    public void workOffRequests(List<Drawable> queue) {
        for (Drawable drawable : queue) {
            // add drawable to stringBuffer
            stringBuffer.append(drawable.getRedrawMetaInfo());
            stringBuffer.append(System.getProperty("line.separator"));

            // draw drawable object
            drawable.draw(drawGUI.bufferG);
            drawGUI.updateCanvas();
            // add drawable to undoList
            undoList.add(drawable);
            // remove drawable from requestQueue
            requestQueue.remove(drawable);
        }
    }
}
