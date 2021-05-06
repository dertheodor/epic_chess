package mydraw;


import java.awt.*;

interface Drawable {
    void draw(Graphics g);
}


class ScribbleDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int endingPointX;
    private int endingPointY;
    private Color drawingColor;

    public ScribbleDrawer(int startX, int startY, int endX, int endY, Color color) {
        startingPointX = startX;
        startingPointY = startY;
        endingPointX = endX;
        endingPointY = endY;
        drawingColor = color;
    }


    public void draw(Graphics g) {
        g.setColor(drawingColor);
        g.drawLine(startingPointX, startingPointY, endingPointX, endingPointY);
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

