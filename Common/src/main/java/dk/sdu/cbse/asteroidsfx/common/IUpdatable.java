package dk.sdu.cbse.asteroidsfx.common;

/**
 * Use this for things that need to be updated in the game loop.
 */
public interface IUpdatable {
    /**
     * Updates the object.
     * Pre-condition: dt is the time passed and should be positive.
     * Post-condition: The object has updated its position or state.
     * @param dt time since last update
     */
    void update(double dt);
}
