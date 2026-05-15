package dk.sdu.cbse.asteroidsfx.collision;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;

public class Collision implements IGamePlugin {
    private CollisionSystem collisionSystem;

    @Override
    public void start(IGameContext context) {
        collisionSystem = new CollisionSystem(context);
        context.getWorld().addEntity(collisionSystem);
        System.out.println("Collision system started");
    }

    @Override
    public void stop(IGameContext context) {
        if (collisionSystem != null) {
            context.getWorld().removeEntity(collisionSystem);
            collisionSystem = null;
        }
        System.out.println("Collision system stopping!");
    }
}
