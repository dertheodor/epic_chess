package mydraw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class DrawTest {
    // init Draw and GUI
    Draw DrawTest = new Draw();
    DrawGUI DrawTestGUI = new DrawGUI(DrawTest);

    @Test
    void getHeightTest() {
        Assertions.assertEquals(DrawTestGUI.getSize().height, DrawTestGUI.getHeight());
    }

    // Negativtest durch absichtlich falsche Assertion nicht sinnvoll, ungültige Eingaben werden im Code abgedeckt
    @Test
    void setHeightTest() {
        DrawTestGUI.setHeight(800);
        Assertions.assertEquals(800, DrawTestGUI.getHeight());
    }

    @Test
    void getWidthTest() {
        Assertions.assertEquals(DrawTestGUI.getSize().width, DrawTestGUI.getWidth());
    }

    // Negativtest durch absichtlich falsche Assertion nicht sinnvoll, ungültige Eingaben werden im Code abgedeckt
    @Test
    void setWidthTest() {
        DrawTestGUI.setWidth(800);
        Assertions.assertEquals(800, DrawTestGUI.getWidth());
    }

    @Test
    void setFGColorTestPositive() {
        try {
            DrawTestGUI.setFGColor("blue");
            Assertions.assertEquals(Color.blue, DrawTestGUI.color);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void setFGColorTestNegative() {
        try {
            DrawTestGUI.setFGColor("blue1");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "blue1 not available, please choose another color.");
        }
    }

    @Test
    void getFGColorTestPositive() {
        try {
            DrawTestGUI.setFGColor("pink");
            Assertions.assertEquals("pink", DrawTestGUI.getFGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getFGColorTestNegative() {
        try {
            DrawTestGUI.setFGColor("pink");
            Assertions.assertNotEquals("yellow", DrawTestGUI.getFGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void setBGColorTestPositive() {
        try {
            DrawTestGUI.setBGColor("blue");
            Assertions.assertEquals(Color.blue, DrawTestGUI.getBackground());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void setBGColorTestNegative() {
        try {
            DrawTestGUI.setBGColor("blue1");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "blue1 not available, please choose another color.");
        }
    }

    @Test
    void getBGColorTestPositive() {
        try {
            DrawTestGUI.setBGColor("pink");
            Assertions.assertEquals("pink", DrawTestGUI.getBGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getBGColorTestNegative() {
        try {
            DrawTestGUI.setBGColor("pink");
            Assertions.assertNotEquals("yellow", DrawTestGUI.getBGColor());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
    //TODO test.bmp for getDrawing, writeImage and readImage
}
