package mydraw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class DrawTest {
    // init Draw and GUI
    Draw drawTest = new Draw();
    DrawGUI drawTestGUI = new DrawGUI(drawTest);

    @Test
    void getHeightTest() {
        Assertions.assertEquals(400, drawTestGUI.getHeight());
    }

    // Negativtest durch absichtlich falsche Assertion nicht sinnvoll, ungültige Eingaben werden im Code abgedeckt
    @Test
    void setHeightTest() {
        drawTestGUI.setHeight(800);
        Assertions.assertEquals(800, drawTestGUI.getHeight());
    }

    @Test
    void getWidthTest() {
        Assertions.assertEquals(500, drawTestGUI.getWidth());
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
    void clearTest() {
        drawTestGUI.clear();
        Color c = drawTestGUI.getBackground();
        
    }
}
