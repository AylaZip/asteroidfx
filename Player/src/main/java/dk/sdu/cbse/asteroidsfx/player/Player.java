package dk.sdu.cbse.asteroidsfx.player;

import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;

public class Player implements IGamePlugin {
    private IEntity playerEntity;

    @Override
    public void start(IGameContext context) {
        playerEntity = new PlayerEntity(context);
        context.getWorld().addEntity(playerEntity);
        System.out.println("Player entity spawned");
    }

    @Override
    public void stop(IGameContext context) {
        if (playerEntity != null) {
            context.getWorld().removeEntity(playerEntity);
            playerEntity = null;
        }
        System.out.println("Player plugin stopping!");
    }
}
