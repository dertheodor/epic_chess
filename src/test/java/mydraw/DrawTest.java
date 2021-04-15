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
        Assertions.assertEquals(400, DrawTestGUI.getHeight());
    }

    @Test
    void setHeightTest() {
        DrawTestGUI.setHeight(800);
        Assertions.assertEquals(800, DrawTestGUI.getHeight());
    }

    @Test
    void getWidthTest() {
        Assertions.assertEquals(500, DrawTestGUI.getWidth());
    }

    @Test
    void setWidthTest() {
        DrawTestGUI.setWidth(800);
        Assertions.assertEquals(800, DrawTestGUI.getWidth());
    }

    @Test
    void setFGColorTestPostive() {
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
}
