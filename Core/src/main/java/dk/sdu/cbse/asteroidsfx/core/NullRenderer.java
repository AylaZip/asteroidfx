package dk.sdu.cbse.asteroidsfx.core;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IUpdatable;

import dk.sdu.cbse.asteroidsfx.common.IRenderer;

public class NullRenderer implements IRenderer {
    @Override public void drawLine(double x1, double y1, double x2, double y2) {}
    @Override public void drawCircle(double cx, double cy, double radius) {}
    @Override public void drawText(double x, double y, String text) {}
    @Override public void drawText(double x, double y, String text, String color) {}
    @Override public void drawText(double x, double y, String text, String color, boolean centered) {}
    @Override public void drawPolygon(double[] xPoints, double[] yPoints, int numPoints) {}
}
