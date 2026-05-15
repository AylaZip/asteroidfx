package dk.sdu.cbse.asteroidsfx.common;

/**
 * This interface is for game parts (plugins) that can be started and stopped.
 */
public interface IGamePlugin {

    /**
     * Starts the plugin.
     * Pre-condition: context is not null.
     * Post-condition: The plugin has added its things to the world.
     * @param context the game context
     */
    void start(IGameContext context);

    /**
     * Stops the plugin.
     * Pre-condition: context is not null.
     * Post-condition: The plugin has removed its things from the world.
     * @param context the game context
     */
    void stop(IGameContext context);

    default String getName() {
        return getClass().getSimpleName();
    }
}
