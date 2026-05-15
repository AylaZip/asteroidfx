package dk.sdu.cbse.asteroidsfx.common;

public interface IInputService {
    boolean isDown(Key key);
    boolean wasPressed(Key key);
    void clearPressed();

    default void beginFrame() {};
}