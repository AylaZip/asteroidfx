package dk.sdu.cbse.asteroidsfx.bullet;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;

public class Bullet implements IGamePlugin {
    @Override
    public void start(IGameContext context) {
        BulletSpawnerService.setContext(context);
        System.out.println("Bullet plugin started");
    }

    @Override
    public void stop(IGameContext context) {
        BulletSpawnerService.setContext(null);
        System.out.println("Bullet plugin stopping!");
    }
}
