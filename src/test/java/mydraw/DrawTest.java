package mydraw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class DrawTest {
    // init Draw and GUI
    Draw drawTest = new Draw();
    DrawGUI drawTestGUI = new DrawGUI(drawTest);

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
            Assertions.assertEquals(Color.blue, drawTestGUI.color);
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

    @Test
    void autoDrawTestPositive() {
        drawTestGUI.autoDraw();
        try {
            drawTestGUI.writeImage(drawTestGUI.bufferImg, "testing.bmp");
            Image referenceImg = drawTestGUI.readImage("reference.bmp");
            BufferedImage bufferedReferenceImg = imgToBufferedImageHelper(referenceImg);
            Image compareImg = drawTestGUI.readImage("testing.bmp");
            BufferedImage bufferedCompareImg = imgToBufferedImageHelper(compareImg);
            Assertions.assertTrue(drawTestGUI.isImgSameAsReference(bufferedReferenceImg, bufferedCompareImg));
        } catch (IOException e) {
            Assertions.fail();

        }
    }

    @Test
    void autoDrawTestNegative() {
        drawTestGUI.autoDraw();
        try {
            drawTestGUI.writeImage(drawTestGUI.bufferImg, "testing.bmp");
            Image referenceImg = drawTestGUI.readImage("wrongReference.bmp");
            BufferedImage bufferedReferenceImg = imgToBufferedImageHelper(referenceImg);
            Image compareImg = drawTestGUI.readImage("testing.bmp");
            BufferedImage bufferedCompareImg = imgToBufferedImageHelper(compareImg);
            Assertions.assertFalse(drawTestGUI.isImgSameAsReference(bufferedReferenceImg, bufferedCompareImg));
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
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
