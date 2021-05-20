package mydraw;


import java.awt.*;
import java.util.ArrayList;

interface Drawable {
    void draw(Graphics g);

    void setDrawingColor(Color newColor);

    Color getLegacyColor();

    String getRedrawMetaInfo();
}


class ScribbleDrawer implements Drawable {
    private ArrayList<Point> drawingPoints;
    private Color drawingColor;
    private Color legacyColor;
    private StringBuilder redrawMetaInfo;

    public ScribbleDrawer(ArrayList<Point> pointArrayList, Color color) {
        drawingColor = color;
        legacyColor = color;
        drawingPoints = pointArrayList;
        redrawMetaInfo = new StringBuilder()
                .append("scribble")
                .append("#");
        // add all points x-values
        pointArrayList.forEach(point -> redrawMetaInfo.append("x").append(point.x));

        // add all points y-values
        pointArrayList.forEach(point -> redrawMetaInfo.append("y").append(point.y));
        redrawMetaInfo.append("#").append(color);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
    }
}

class RectangleDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;
    private Color legacyColor;
    private StringBuilder redrawMetaInfo;

    public RectangleDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
        legacyColor = color;
        redrawMetaInfo = new StringBuilder()
                .append("rectangle")
                .append("#")
                .append(upperLeftX)
                .append("#")
                .append(upperLeftY)
                .append("#")
                .append(lowerRightX)
                .append("#")
                .append(lowerRightY)
                .append("#")
                .append(color);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
    }
}

class OvalDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;
    private Color legacyColor;
    private StringBuilder redrawMetaInfo;

    public OvalDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
        legacyColor = color;
        redrawMetaInfo = new StringBuilder()
                .append("oval")
                .append("#")
                .append(upperLeftX)
                .append("#")
                .append(upperLeftY)
                .append("#")
                .append(lowerRightX)
                .append("#")
                .append(lowerRightY)
                .append("#")
                .append(color);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
    }
}

class Filled3DRectDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;
    private Color legacyColor;
    private boolean raisedBool;
    private StringBuilder redrawMetaInfo;

    public Filled3DRectDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color, boolean raised) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
        legacyColor = color;
        raisedBool = raised;
        redrawMetaInfo = new StringBuilder()
                .append("filled3drect")
                .append("#")
                .append(upperLeftX)
                .append("#")
                .append(upperLeftY)
                .append("#")
                .append(lowerRightX)
                .append("#")
                .append(lowerRightY)
                .append("#")
                .append(color)
                .append("#")
                .append(raised);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
    }
}

class RoundRectDrawer implements Drawable {
    private int startingPointX;
    private int startingPointY;
    private int rectangleWidth;
    private int rectangleHeight;
    private Color drawingColor;
    private Color legacyColor;
    private int arcWidth;
    private int arcHeight;
    private StringBuilder redrawMetaInfo;

    public RoundRectDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY, Color color, int arcW, int arcH) {
        startingPointX = Math.min(upperLeftX, lowerRightX);
        startingPointY = Math.min(upperLeftY, lowerRightY);
        rectangleWidth = Math.abs(lowerRightX - upperLeftX);
        rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        drawingColor = color;
        legacyColor = color;
        arcWidth = arcW;
        arcHeight = arcH;
        redrawMetaInfo = new StringBuilder()
                .append("roundrect")
                .append("#")
                .append(upperLeftX)
                .append("#")
                .append(upperLeftY)
                .append("#")
                .append(lowerRightX)
                .append("#")
                .append(lowerRightY)
                .append("#")
                .append(color)
                .append("#")
                .append(arcW)
                .append("#")
                .append(arcH);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
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
    private Color legacyColor;
    private StringBuilder redrawMetaInfo;

    public TriangleDrawer(int startingPointX, int startingPointY, int draggingPointX, int draggingPointY, Color color) {
        startX = startingPointX;
        startY = startingPointY;
        dragX = draggingPointX;
        dragY = draggingPointY;
        heightX = (startX + (dragX - startX) / 2);
        heightY = startY - dragX + startX;
        drawingColor = color;
        legacyColor = color;
        redrawMetaInfo = new StringBuilder()
                .append("triangle")
                .append("#")
                .append(startingPointX)
                .append("#")
                .append(startingPointY)
                .append("#")
                .append(draggingPointX)
                .append("#")
                .append(draggingPointY)
                .append("#")
                .append(color);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
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
    private Color legacyColor;
    private StringBuilder redrawMetaInfo;

    public IsoscelesTriangleDrawer(int startingPointX, int startingPointY, int draggingPointX, int draggingPointY, Color color) {
        startX = startingPointX;
        startY = startingPointY;
        dragX = draggingPointX;
        dragY = draggingPointY;
        heightX = (startX + (dragX - startX) / 2);
        heightY = startY - dragX + startX;
        drawingColor = color;
        legacyColor = color;
        redrawMetaInfo = new StringBuilder()
                .append("isoscelestriangle")
                .append("#")
                .append(startingPointX)
                .append("#")
                .append(startingPointY)
                .append("#")
                .append(draggingPointX)
                .append("#")
                .append(draggingPointY)
                .append("#")
                .append(color);
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

    public Color getLegacyColor() {
        return legacyColor;
    }

    public String getRedrawMetaInfo() {
        return redrawMetaInfo.toString();
    }
}
