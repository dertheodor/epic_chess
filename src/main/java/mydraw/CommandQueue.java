// Purpose.  Command design pattern - decoupling producer from consumer
package mydraw;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * simple test with command queue pattern
 * modified by ptp
 */
public class CommandQueue {

    public interface Drawable {
        void draw(Graphics g);
    }

    public static class ScribbleDrawer implements Drawable {
        private int startingPoint;
        private int endingPoint;


        public void draw(Graphics g) {
            System.out.println("take out the trash");
            //führ das aus
            //adde mich in die queue
        }
    }

    public static class RectangleDrawer implements Drawable {
        private int startingPointX;
        private int startingPointY;
        private int rectangleWidth;
        private int rectangleHeight;

        public RectangleDrawer(int upperLeftX, int upperLeftY, int lowerRightX, int lowerRightY) {
            startingPointX = Math.min(upperLeftX, lowerRightX);
            startingPointY = Math.min(upperLeftY, lowerRightY);
            rectangleWidth = Math.abs(lowerRightX - upperLeftX);
            rectangleHeight = Math.abs(lowerRightY - upperLeftY);
        }

        public void draw(Graphics g) {
            // draw rectangle
            g.drawRect(startingPointX, startingPointY, rectangleWidth, rectangleHeight);


        }
    }

    public static class OvalDrawer implements Drawable {
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

    //  public static List<Drawable> produceRequests() {
    //      List<Drawable> queue = new ArrayList<Drawable>();
    //      queue.add(new ScribbleDrawer());
    //      queue.add(new RectangleDrawer());
    //      queue.add(new OvalDrawer());
    //      //queue.add(new Mathematics());
//
    //      return queue;
    //  }

    //  public static void workOffRequests(List<Drawable> queue) {
    //      for (Iterator<Drawable> it = queue.iterator(); it.hasNext(); ) break;
    //      //it.next().draw(g); //TODO Add the Graphics to correctly use this Method
    //  }
//
    //  public static void main(String[] args) {
    //      List<Drawable> queue = produceRequests();
    //      workOffRequests(queue);
    //  }
}

// take out the trash
// take money from the rich, take votes from the poor
// sell the bugs, charge extra for the fixes

