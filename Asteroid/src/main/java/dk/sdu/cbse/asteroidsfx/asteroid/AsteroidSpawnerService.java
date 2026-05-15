package dk.sdu.cbse.asteroidsfx.asteroid;

import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawner;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;

import java.util.Random;

public class AsteroidSpawnerService implements IAsteroidSpawner {
    private static IGameContext context;
    private static final Random random = new Random();

    public AsteroidSpawnerService() {
    }

    public static void setContext(IGameContext ctx) {
        context = ctx;
    }

    @Override
    public void spawnAsteroid(String sizeString) {
        double x = random.nextDouble() * 800;
        double y = random.nextDouble() * 600;
        spawnAsteroid(sizeString, x, y);
    }

    @Override
    public void spawnAsteroid(String sizeString, double x, double y) {
        if (context == null) return;
        try {
            AsteroidSize size = AsteroidSize.valueOf(sizeString);
            context.getWorld().addEntity(new AsteroidEntity(context, size, x, y));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid asteroid size: " + sizeString);
        }
    }
}
