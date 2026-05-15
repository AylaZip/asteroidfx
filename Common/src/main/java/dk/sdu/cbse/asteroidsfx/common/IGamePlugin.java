package dk.sdu.cbse.asteroidsfx.common;

public interface IGamePlugin {

    void start(IGameContext context);

    void stop(IGameContext context);

    default String getName() {
        return getClass().getSimpleName();
    }
}
