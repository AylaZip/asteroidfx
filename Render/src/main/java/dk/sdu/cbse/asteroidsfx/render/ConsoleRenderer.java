package dk.sdu.cbse.asteroidsfx.render;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IUpdatable;

import dk.sdu.cbse.asteroidsfx.common.IRenderer;

public final class ConsoleRenderer implements IRenderer {

    @Override 
    public void beginFrame() {

    }

    @Override 
    public void endFrame() {

    }

    @Override 
    public void drawLine(double x1, double y1, double x2, double y2) {
        System.out.println("LINE " + x1 + "," + y1 + " -> " + x2 + "," + y2);
    }

    @Override 
    public void drawCircle(double cx, double cy, double radius) {
        System.out.println("CIRCLE " + cx + "," + cy + " r=" + radius);
    }

    @Override 
    public void drawText(double x, double y, String text) {
        System.out.println("TEXT (" + x + "," + y + "): " + text);
    }

    @Override 
    public void drawText(double x, double y, String text, String color) {
        System.out.println("TEXT (" + x + "," + y + "," + color + "): " + text);
    }

    @Override 
    public void drawText(double x, double y, String text, String color, boolean centered) {
        System.out.println("TEXT (" + x + "," + y + "," + color + ",centered=" + centered + "): " + text);
    }

    @Override
    public void drawPolygon(double[] xPoints, double[] yPoints, int numPoints) {
        System.out.println("POLYGON");
    }
}