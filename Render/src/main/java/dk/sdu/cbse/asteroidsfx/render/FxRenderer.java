package dk.sdu.cbse.asteroidsfx.render;

import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import javafx.scene.canvas.GraphicsContext;

public class FxRenderer implements IRenderer {
    private final GraphicsContext gc;

    public FxRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void beginFrame() {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setStroke(javafx.scene.paint.Color.WHITE);
        gc.setFill(javafx.scene.paint.Color.WHITE);
    }

    @Override
    public void endFrame() {

    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void drawCircle(double cx, double cy, double radius) {
        double d = radius * 2;
        gc.strokeOval(cx - radius, cy - radius, d, d);
    }

    @Override
    public void drawText(double x, double y, String text) {
        gc.fillText(text, x, y);
    }

    @Override
    public void drawText(double x, double y, String text, String color) {
        gc.setFill(javafx.scene.paint.Color.web(color));
        gc.fillText(text, x, y);
        gc.setFill(javafx.scene.paint.Color.WHITE);
    }

    @Override
    public void drawText(double x, double y, String text, String color, boolean centered) {
        gc.setFill(javafx.scene.paint.Color.web(color));
        if (centered) {
            gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
            gc.setTextBaseline(javafx.geometry.VPos.CENTER);
            gc.fillText(text, x, y);
            gc.setTextAlign(javafx.scene.text.TextAlignment.LEFT);
            gc.setTextBaseline(javafx.geometry.VPos.TOP);
        } else {
            gc.fillText(text, x, y);
        }
        gc.setFill(javafx.scene.paint.Color.WHITE);
    }

    @Override
    public void drawPolygon(double[] xPoints, double[] yPoints, int numPoints) {
        gc.strokePolygon(xPoints, yPoints, numPoints);
    }
}
