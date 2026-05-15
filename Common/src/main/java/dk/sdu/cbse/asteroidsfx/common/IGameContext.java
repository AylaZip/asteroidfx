package dk.sdu.cbse.asteroidsfx.common;

import java.util.Collection;

public interface IGameContext {
    IGameWorld getWorld();
    IRenderer getRenderer();
    IInputService getInput();
    IGameFlowListener getGameFlowListener();
    IAsteroidSpawner getAsteroidSpawner();
    Collection<? extends IBulletSpawner> getBulletSpawners();
}
