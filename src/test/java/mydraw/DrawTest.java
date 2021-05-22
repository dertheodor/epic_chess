package mydraw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DrawTest {
    // init Draw, DrawGUI and CommandQueue
    Draw drawTest = new Draw();
    DrawGUI drawTestGUI = new DrawGUI(drawTest);
    CommandQueue commandQueue = new CommandQueue(drawTestGUI);

    @Test
    void getHeightTest() {
        Assertions.assertEquals(drawTestGUI.getSize().height, drawTestGUI.getHeight());
    }

    // Negativtest durch absichtlich falsche Assertion nicht sinnvoll, ungültige Eingaben werden im Code abgedeckt
    @Test
    void setHeightTest() {
        drawTestGUI.setHeight(800);
        Assertions.assertEquals(800, drawTestGUI.getHeight());
    }

    @Test
    void getWidthTest() {
        Assertions.assertEquals(drawTestGUI.getSize().width, drawTestGUI.getWidth());
    }

    // Negativtest durch absichtlich falsche Assertion nicht sinnvoll, ungültige Eingaben werden im Code abgedeckt
    @Test
    void setWidthTest() {
        drawTestGUI.setWidth(800);
        Assertions.assertEquals(800, drawTestGUI.getWidth());
    }

    @Test
    void setFGColorTestPositive() {
        try {
            drawTestGUI.setFGColor("blue");
            Assertions.assertEquals(Color.blue, drawTestGUI.fgColor);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void setFGColorTestNegative() {
        try {
            drawTestGUI.setFGColor("blue1");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "blue1 not available, please choose another color.");
        }
    }

    @Test
    void getFGColorTestPositive() {
        try {
            drawTestGUI.setFGColor("pink");
            Assertions.assertEquals("pink", drawTestGUI.getFGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getFGColorTestNegative() {
        try {
            drawTestGUI.setFGColor("pink");
            Assertions.assertNotEquals("yellow", drawTestGUI.getFGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void setBGColorTestPositive() {
        try {
            drawTestGUI.setBGColor("blue");
            Assertions.assertEquals(Color.blue, drawTestGUI.getBackground());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void setBGColorTestNegative() {
        try {
            drawTestGUI.setBGColor("blue1");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "blue1 not available, please choose another color.");
        }
    }

    @Test
    void getBGColorTestPositive() {
        try {
            drawTestGUI.setBGColor("pink");
            Assertions.assertEquals("pink", drawTestGUI.getBGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getBGColorTestNegative() {
        try {
            drawTestGUI.setBGColor("pink");
            Assertions.assertNotEquals("yellow", drawTestGUI.getBGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    /**
     * Tests methods: autoDraw and all available shapes
     */
    @Test
    void autoDrawTestPositive() {
        // execute autoDraw()
        drawTestGUI.autoDraw();
        try {
            // read referenceImg from file
            Image referenceImg = drawTestGUI.readImage("DrawExternalFiles/reference.bmp");
            BufferedImage bufferedReferenceImg = imgToBufferedImageHelper(referenceImg);

            // create comparison image from current gui
            Image compareImg = drawTestGUI.getDrawing();
            BufferedImage bufferedCompareImg = imgToBufferedImageHelper(compareImg);

            // assert that they are the same
            Assertions.assertTrue(drawTestGUI.isImgSameAsReference(bufferedReferenceImg, bufferedCompareImg));
        } catch (IOException e) {
            Assertions.fail();

        }
    }

    /**
     * Tests methods: autoDraw and all available shapes
     */
    @Test
    void autoDrawTestNegative() {
        // execute autoDraw()
        drawTestGUI.autoDraw();
        try {
            // read referenceImg from file
            Image referenceImg = drawTestGUI.readImage("DrawExternalFiles/reference.bmp");
            BufferedImage bufferedReferenceImg = imgToBufferedImageHelper(referenceImg);

            // read differentDrawingReference from file
            Image differentDrawingReference = drawTestGUI.readImage("DrawExternalFiles/differentDrawingReference.bmp");
            BufferedImage bufferedCompareImg0 = imgToBufferedImageHelper(differentDrawingReference);

            // read differentSizeReference from file
            Image differentSizeReference = drawTestGUI.readImage("DrawExternalFiles/differentSizeReference.bmp");
            BufferedImage bufferedCompareImg1 = imgToBufferedImageHelper(differentSizeReference);

            // test wrong size AND wrong painting
            Assertions.assertFalse(drawTestGUI.isImgSameAsReference(bufferedReferenceImg, bufferedCompareImg0));
            Assertions.assertFalse(drawTestGUI.isImgSameAsReference(bufferedReferenceImg, bufferedCompareImg1));
        } catch (IOException e) {
            Assertions.fail();

        }
    }

    /**
     * Converts a given Image into a BufferedImage
     * Shamelessly Stolen from: https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage imgToBufferedImageHelper(Image img) {
        // Create a buffered image with transparency
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bImage;
    }

    @Test
        // TODO why does this test fail.. when saved both images are the same
    void undoTestPositive() {
        // create comparison image from current gui (empty canvas)
        drawTestGUI.clear();
        Image emptyImg = drawTestGUI.getDrawing();
        BufferedImage bufferedEmptyImg = imgToBufferedImageHelper(emptyImg);
        // auto draw rectangle
        drawTestGUI.shapeManager.rectangleDrawerLogic.drawForRealNow(100, 100, 200, 200, Color.black);
        // undo drawing of rectangle
        commandQueue.undoLastDrawingAction();

        // create image from current gui
        Image compareImg = drawTestGUI.getDrawing();
        BufferedImage bufferedCompareImg = imgToBufferedImageHelper(compareImg);

        // assert that they are the same
        Assertions.assertTrue(drawTestGUI.isImgSameAsReference(bufferedEmptyImg, bufferedCompareImg));
    }

    @Test
    void readPastDrawingPositive() throws IOException {
        // read txt file of reference_drawing
        File pastDrawing = new File("DrawExternalFiles/reference_drawing.txt");

        // redraw reference drawing from its txt file
        Scanner input = new Scanner(pastDrawing);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            drawTestGUI.readLineAndDrawDrawable(line);
        }
        input.close();

        // create redrawnImage from current gui
        Image redrawnImage = drawTestGUI.getDrawing();
        BufferedImage redrawnCompareImg = imgToBufferedImageHelper(redrawnImage);

        // read referenceImg from file
        Image referenceImg = drawTestGUI.readImage("DrawExternalFiles/reference.bmp");
        BufferedImage bufferedReferenceImg = imgToBufferedImageHelper(referenceImg);

        // assert that they are the same
        Assertions.assertTrue(drawTestGUI.isImgSameAsReference(redrawnCompareImg, bufferedReferenceImg));
    }

    @Test
    void readPastDrawingNegative() throws IOException {
        // read txt file of wrong_drawing
        File pastDrawing = new File("DrawExternalFiles/wrong_drawing.txt");

        // redraw wrong drawing from its txt file
        Scanner input = new Scanner(pastDrawing);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            drawTestGUI.readLineAndDrawDrawable(line);
        }
        input.close();

        // create redrawnImage from current gui
        Image redrawnImage = drawTestGUI.getDrawing();
        BufferedImage redrawnCompareImg = imgToBufferedImageHelper(redrawnImage);

        // read referenceImg from file
        Image referenceImg = drawTestGUI.readImage("DrawExternalFiles/reference.bmp");
        BufferedImage bufferedReferenceImg = imgToBufferedImageHelper(referenceImg);

        // assert that they are the same
        Assertions.assertFalse(drawTestGUI.isImgSameAsReference(redrawnCompareImg, bufferedReferenceImg));
    }
}
