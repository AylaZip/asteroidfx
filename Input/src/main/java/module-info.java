/**
 * This module reads keyboard input.
 */
module Input {
    requires Common;
    requires javafx.graphics;

    exports dk.sdu.cbse.asteroidsfx.input;

    provides dk.sdu.cbse.asteroidsfx.common.IInputServiceProvider with dk.sdu.cbse.asteroidsfx.input.InputServiceProvider;
}
