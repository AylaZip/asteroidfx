package dk.sdu.cbse.asteroidsfx.render;

import dk.sdu.cbse.asteroidsfx.common.IRendererProvider;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import javafx.scene.canvas.GraphicsContext;

public class FxRendererProvider implements IRendererProvider {
    @Override
    public IRenderer create(Object context) {
        GraphicsContext gc = (GraphicsContext) context;
        return new FxRenderer(gc);
    }
}
