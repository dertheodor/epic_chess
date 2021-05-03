package mydraw;

import java.awt.event.*;
import java.util.HashMap;


abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener {
    public void mouseMoved(MouseEvent e) { /* ignore */ }
}

// this class determines how mouse events are to be interpreted,
// depending on the shape mode currently set
class ShapeManager implements ItemListener {
    DrawGUI gui;
    CommandQueue cQ;
    ScribbleDrawer scribbleDrawer;
    RectDrawer rectDrawer;
    OvalDrawer ovalDrawer;
    ShapeDrawer currentDrawer;

    // constructor
    public ShapeManager(DrawGUI itsGui) {
        // create new CommandQueue
        cQ = new CommandQueue();

        scribbleDrawer = new ScribbleDrawer(itsGui);
        rectDrawer = new RectDrawer(itsGui, cQ);
        ovalDrawer = new OvalDrawer(itsGui, cQ);

        gui = itsGui;
        // default: scribble mode
        currentDrawer = scribbleDrawer;
        // activate scribble drawer
        gui.addMouseListener(currentDrawer);
        gui.addMouseMotionListener(currentDrawer);
    }

    // reset the shape drawer
    public void setCurrentDrawer(ShapeDrawer l) {
        if (currentDrawer == l)
            return;

        // deactivate previous drawer
        gui.removeMouseListener(currentDrawer);
        gui.removeMouseMotionListener(currentDrawer);
        // activate new drawer
        currentDrawer = l;
        gui.addMouseListener(currentDrawer);
        gui.addMouseMotionListener(currentDrawer);
    }

    // user selected new shape => reset the shape mode
    public void itemStateChanged(ItemEvent e) {
        // Key-Value storage for possible colors
        HashMap<String, ShapeDrawer> itemToDrawer = new HashMap<>();
        itemToDrawer.put("Scribble", scribbleDrawer);
        itemToDrawer.put("Rectangle", rectDrawer);
        itemToDrawer.put("Oval", ovalDrawer);
        setCurrentDrawer(itemToDrawer.get(e.getItem().toString()));
    }
}
