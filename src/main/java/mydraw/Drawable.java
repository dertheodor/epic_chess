package mydraw;


import java.awt.*;
import java.util.ArrayList;

interface Drawable {
    void draw(Graphics g);
}


class ScribbleDrawer implements Drawable {
    private ArrayList<Point> drawingPoints;
    private Color drawingColor;

    public ScribbleDrawer(ArrayList<Point> pointArrayList, Color color) {
        drawingColor = color;
        drawingPoints = pointArrayList;
    }

    public void draw(Graphics g) {
        g.setColor(drawingColor);

        // iterate trough drawn points and draw them
        for (int i = 0; i < drawingPoints.size() - 1; i++) {
            g.drawLine(drawingPoints.get(i).x, drawingPoints.get(i).y, drawingPoints.get(i+1).x, drawingPoints.get(i+1).y);
        }
    }
}

class RectangleDrawer implements Drawable {
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

class OvalDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;

    public OvalDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
    }

    public void draw(Graphics g) {
        // set drawing color
        g.setColor(drawingColor);
        // draw oval
        g.drawOval(startingPointX, startingPointY, rectangleWidth, rectangleHeight);
    }
}

class Filled3DRectDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;
    private boolean raisedBool;

    public Filled3DRectDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color, boolean raised) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
        raisedBool = raised;
    }

    public void draw(Graphics g) {
        // set drawing color
        g.setColor(drawingColor);
        // draw oval
        g.fill3DRect(startingPointX, startingPointY, rectangleWidth, rectangleHeight, raisedBool);
    }
}

class RoundRectDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;
    private int arcWidth;
    private int arcHeight;

    public RoundRectDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color, int arcW, int arcH) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
        arcWidth = arcW;
        arcHeight = arcH;
    }

    public void draw(Graphics g) {
        // set drawing color
        g.setColor(drawingColor);
        // draw oval
        g.drawRoundRect(startingPointX, startingPointY, rectangleWidth, rectangleHeight, arcWidth, arcHeight);
    }
}

