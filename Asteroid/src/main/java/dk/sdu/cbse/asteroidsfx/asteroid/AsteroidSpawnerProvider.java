package dk.sdu.cbse.asteroidsfx.asteroid;

import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawner;
import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawnerProvider;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;

public class AsteroidSpawnerProvider implements IAsteroidSpawnerProvider {
    @Override
    public IAsteroidSpawner create(IGameContext context) {
        AsteroidSpawnerService.setContext(context);
        return new AsteroidSpawnerService();
    }
}
