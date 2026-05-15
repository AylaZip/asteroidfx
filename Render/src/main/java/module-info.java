/**
 * This module draws everything on the screen using JavaFX.
 */
module Render {
    requires transitive Common;
    requires transitive javafx.graphics;
    
    exports dk.sdu.cbse.asteroidsfx.render;

    provides dk.sdu.cbse.asteroidsfx.common.IRendererProvider with dk.sdu.cbse.asteroidsfx.render.FxRendererProvider;
}
