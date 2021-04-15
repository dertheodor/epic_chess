package mydraw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DrawTest {
    @Test
    void test1() {
        Draw DrawTest = new Draw();
        Assertions.assertEquals(1, DrawTest.window.getHeight());
    }
}

