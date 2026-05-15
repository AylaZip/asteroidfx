package dk.sdu.cbse.asteroidsfx.common;

public interface IRenderer {
    void drawLine(double x1, double y1, double x2, double y2);
    void drawCircle(double cx, double cy, double radius);
    void drawText(double x, double y, String text);
    void drawText(double x, double y, String text, String color);
    void drawText(double x, double y, String text, String color, boolean centered);
    void drawPolygon(double[] xPoints, double[] yPoints, int numPoints);

    default void beginFrame() {

    }

    default void endFrame() {

    }
}