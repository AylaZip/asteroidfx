package dk.sdu.cbse.asteroidsfx.core;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IUpdatable;

import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawner;

public class NullAsteroidSpawner implements IAsteroidSpawner {
    @Override
    public void spawnAsteroid(String sizeString, double x, double y) {

    }

    @Override
    public void spawnAsteroid(String sizeString) {

    }
}
