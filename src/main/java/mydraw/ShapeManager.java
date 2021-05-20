package mydraw;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;


abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener {
    public void mouseMoved(MouseEvent e) { /* ignore */ }
}

// this class determines how mouse events are to be interpreted,
// depending on the shape mode currently set
class ShapeManager implements ItemListener {
    DrawGUI gui;
    ScribbleDrawerLogic scribbleDrawerLogic;
    RectangleDrawerLogic rectangleDrawerLogic;
    OvalDrawerLogic ovalDrawerLogic;
    Filled3DRectDrawerLogic threeDRectDrawerLogic;
    RoundRectDrawerLogic roundRectDrawerLogic;
    TriangleDrawerLogic triangleDrawerLogic;
    IsoscelesTriangleDrawerLogic isoscelesTriangleDrawerLogic;
    ShapeDrawer currentDrawer;

    // constructor
    public ShapeManager(DrawGUI itsGui, CommandQueue cQ) {
        scribbleDrawerLogic = new ScribbleDrawerLogic(itsGui, cQ);
        rectangleDrawerLogic = new RectangleDrawerLogic(itsGui, cQ);
        ovalDrawerLogic = new OvalDrawerLogic(itsGui, cQ);
        threeDRectDrawerLogic = new Filled3DRectDrawerLogic(itsGui, cQ);
        roundRectDrawerLogic = new RoundRectDrawerLogic(itsGui, cQ);
        triangleDrawerLogic = new TriangleDrawerLogic(itsGui, cQ);
        isoscelesTriangleDrawerLogic = new IsoscelesTriangleDrawerLogic(itsGui, cQ);

        gui = itsGui;
        // default: scribble mode
        currentDrawer = scribbleDrawerLogic;
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
        itemToDrawer.put("Scribble", scribbleDrawerLogic);
        itemToDrawer.put("Rectangle", rectangleDrawerLogic);
        itemToDrawer.put("Oval", ovalDrawerLogic);
        itemToDrawer.put("Filled 3DRect", threeDRectDrawerLogic);
        itemToDrawer.put("Round Rectangle", roundRectDrawerLogic);
        itemToDrawer.put("Triangle", triangleDrawerLogic);
        itemToDrawer.put("Isosceles triangle", isoscelesTriangleDrawerLogic);
        setCurrentDrawer(itemToDrawer.get(e.getItem().toString()));
    }
}
