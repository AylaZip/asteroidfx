package dk.sdu.cbse.asteroidsfx.common;

import java.util.List;

/**
 * This is where all game entities are kept.
 */
public interface IGameWorld {
    /**
     * Adds something to the game.
     * Pre-condition: entity is not null.
     * Post-condition: The entity is now in the game.
     * @param entity thing to add
     */
    void addEntity(IEntity entity);

    /**
     * Removes something from the game.
     * Pre-condition: entity is not null.
     * Post-condition: The entity is no longer in the game.
     * @param entity thing to remove
     */
    void removeEntity(IEntity entity);

    /**
     * Gets all things in the game.
     * @return list of entities
     */
    List<IEntity> getEntities();
}
