package mydraw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}

