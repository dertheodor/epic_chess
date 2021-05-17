package mydraw;


import java.awt.*;
import java.util.ArrayList;

interface Drawable {
    void draw(Graphics g);

    void setDrawingColor(Color newColor);
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
            g.drawLine(drawingPoints.get(i).x, drawingPoints.get(i).y, drawingPoints.get(i + 1).x, drawingPoints.get(i + 1).y);
        }
    }

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
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

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
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

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
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
        // draw filled 3d-rectangle
        g.fill3DRect(startingPointX, startingPointY, rectangleWidth, rectangleHeight, raisedBool);
    }

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
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
        // draw round rectangle
        g.drawRoundRect(startingPointX, startingPointY, rectangleWidth, rectangleHeight, arcWidth, arcHeight);
    }

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
    }
}

class TriangleDrawer implements Drawable {
    private int startX;
    private int startY;
    private int dragX;
    private int dragY;
    private int heightX;
    private int heightY;
    private Color drawingColor;

    public TriangleDrawer(int startingPointX, int startingPointY, int draggingPointX, int draggingPointY, Color color) {
        startX = startingPointX;
        startY = startingPointY;
        dragX = draggingPointX;
        dragY = draggingPointY;
        heightX = (startX + (dragX - startX) / 2);
        heightY = startY - dragX + startX;
        drawingColor = color;
    }

    public void draw(Graphics g) {
        // set drawing color
        g.setColor(drawingColor);
        // draw triangle
        g.drawPolygon(new int[]{startX, dragX, heightX}, new int[]{startY, dragY, heightY}, 3);
    }

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
    }
}

class IsoscelesTriangleDrawer implements Drawable {
    private int startX;
    private int startY;
    private int dragX;
    private int dragY;
    private int heightX;
    private int heightY;
    private Color drawingColor;

    public IsoscelesTriangleDrawer(int startingPointX, int startingPointY, int draggingPointX, int draggingPointY, Color color) {
        startX = startingPointX;
        startY = startingPointY;
        dragX = draggingPointX;
        dragY = draggingPointY;
        heightX = (startX + (dragX - startX) / 2);
        heightY = startY - dragX + startX;
        drawingColor = color;
    }

    public void draw(Graphics g) {
        // set drawing color
        g.setColor(drawingColor);
        // draw isosceles triangle
        g.drawPolygon(new int[]{startX, dragX, heightX}, new int[]{startY, startY, heightY}, 3);
    }

    public void setDrawingColor(Color newColor) {
        drawingColor = newColor;
    }
}
