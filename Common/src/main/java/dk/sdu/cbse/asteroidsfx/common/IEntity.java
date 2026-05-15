package dk.sdu.cbse.asteroidsfx.common;

/**
 * The base for everything in the game world.
 */
public interface IEntity extends IUpdatable, IDrawable {
    /**
     * Who owns this entity?
     * @return the owner
     */
    default IEntity getOwner() {
        return this;
    }
}
