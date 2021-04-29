// Purpose.  Command design pattern - decoupling producer from consumer

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * simple test with command queue pattern
 * modified by ptp
 */
public class CommandQueue {

    interface Drawable {
        void draw(Graphics g);
    }

    static class ScribbleDrawer implements Drawable {
        public void draw(Graphics g) {
            System.out.println("take out the trash");
        }
    }

    static class RectangleDrawer implements Drawable {
        public void draw(Graphics g) {
            System.out.println("take money from the rich, take votes from the poor");
        }
    }

    static class OvalDrawer implements Drawable {
        public void draw(Graphics g) {
            System.out.println("sell the bugs, charge extra for the fixes");
        }
    }

    //   static class Mathematics implements Drawable {
    //       public void draw() {
    //           for (int i = 0; i < 5; i++) {
    //               System.out.println("sin (" + i + ") = " + Math.sin(i * 3.14));
    //           }
    //           System.out.println("finish");
    //       }
    //   }

    public static List<Drawable> produceRequests() {
        List<Drawable> queue = new ArrayList<Drawable>();
        queue.add(new ScribbleDrawer());
        queue.add(new RectangleDrawer());
        queue.add(new OvalDrawer());
        //queue.add(new Mathematics());

        return queue;
    }

    public static void workOffRequests(List<Drawable> queue) {
        for (Iterator<Drawable> it = queue.iterator(); it.hasNext(); ) break;
        //it.next().draw(g); //TODO Add the Graphics to correctly use this Method
    }

    public static void main(String[] args) {
        List<Drawable> queue = produceRequests();
        workOffRequests(queue);
    }
}

// take out the trash
// take money from the rich, take votes from the poor
// sell the bugs, charge extra for the fixes

