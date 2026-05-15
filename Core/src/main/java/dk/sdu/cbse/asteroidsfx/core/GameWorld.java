package dk.sdu.cbse.asteroidsfx.core;

import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GameWorld implements IGameWorld {

    private final List<IEntity> entities = new ArrayList<>();

    @Override
    public synchronized void addEntity(IEntity entity) {
        if (entity == null) return;
        entities.add(entity);
    }

    @Override
    public synchronized void removeEntity(IEntity entity) {
        entities.remove(entity);
    }

    @Override
    public synchronized List<IEntity> getEntities() {
        return Collections.unmodifiableList(new ArrayList<>(entities));
    }
}
