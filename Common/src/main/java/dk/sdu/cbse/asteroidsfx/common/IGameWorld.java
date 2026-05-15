package dk.sdu.cbse.asteroidsfx.common;

import java.util.List;

public interface IGameWorld {
    void addEntity(IEntity entity);
    void removeEntity(IEntity entity);
    List<IEntity> getEntities();
}
