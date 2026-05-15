package dk.sdu.cbse.asteroidsfx.core;

import dk.sdu.cbse.asteroidsfx.common.IInputService;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.IGameFlowListener;
import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawner;
import dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;

import java.util.Collection;
import java.util.Objects;

public final class GameContext implements IGameContext {

    private final IGameWorld world;
    private final IRenderer renderer;
    private final IInputService input;
    private final IGameFlowListener gameFlowListener;
    private final IAsteroidSpawner asteroidSpawner;
    private final Collection<? extends IBulletSpawner> bulletSpawners;

    public GameContext(IGameWorld world, IRenderer renderer, IInputService input, IGameFlowListener gameFlowListener, IAsteroidSpawner asteroidSpawner, Collection<? extends IBulletSpawner> bulletSpawners) {
        this.world = Objects.requireNonNull(world);
        this.renderer = Objects.requireNonNull(renderer);
        this.input = Objects.requireNonNull(input);
        this.gameFlowListener = Objects.requireNonNull(gameFlowListener);
        this.asteroidSpawner = Objects.requireNonNull(asteroidSpawner);
        this.bulletSpawners = Objects.requireNonNull(bulletSpawners);
    }

    @Override public IGameWorld getWorld() { return world; }
    @Override public IRenderer getRenderer() { return renderer; }
    @Override public IInputService getInput() { return input; }
    @Override public IGameFlowListener getGameFlowListener() { return gameFlowListener; }
    @Override public IAsteroidSpawner getAsteroidSpawner() { return asteroidSpawner; }
    @Override public Collection<? extends IBulletSpawner> getBulletSpawners() { return bulletSpawners; }
}
