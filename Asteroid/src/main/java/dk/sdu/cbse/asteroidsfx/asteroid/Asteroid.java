package dk.sdu.cbse.asteroidsfx.asteroid;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;

public class Asteroid implements IGamePlugin {

    @Override
    public void start(IGameContext context) {
        AsteroidSpawnerService.setContext(context);
        System.out.println("Asteroid plugin started");
    }

    @Override
    public void stop(IGameContext context) {
        AsteroidSpawnerService.setContext(null);
        System.out.println("Asteroid plugin stopping!");
    }
}
