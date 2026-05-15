package dk.sdu.cbse.asteroidsfx.bullet;

import dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IEntity;

public class BulletSpawnerService implements IBulletSpawner {
    private static IGameContext context;

    public BulletSpawnerService() {

    }

    public static void setContext(IGameContext cxt) {
        context = cxt;
    }

    @Override
    public void spawnBullet(double x, double y, double angle, double velocityX, double velocityY, IEntity owner) {
        if (context != null) {
            BulletEntity bullet = new BulletEntity(context, x, y, angle, velocityX, velocityY, owner);
            context.getWorld().addEntity(bullet);
        }
    }
}
