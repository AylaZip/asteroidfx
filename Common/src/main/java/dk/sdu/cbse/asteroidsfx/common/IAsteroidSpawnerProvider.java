package dk.sdu.cbse.asteroidsfx.common;

public interface IAsteroidSpawnerProvider {
    IAsteroidSpawner create(IGameContext context);
}
