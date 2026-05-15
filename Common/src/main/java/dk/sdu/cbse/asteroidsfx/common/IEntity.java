package dk.sdu.cbse.asteroidsfx.common;

public interface IEntity extends IUpdatable, IDrawable {
    default IEntity getOwner() {
        return this;
    }
}
