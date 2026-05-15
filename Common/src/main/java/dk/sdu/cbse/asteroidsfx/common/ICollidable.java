package dk.sdu.cbse.asteroidsfx.common;

public interface ICollidable extends IEntity {

    double getX();
    double getY();
    double getRadius();

    default double getVelocityX() { return 0; }
    default double getVelocityY() { return 0; }
    default void setVelocityX(double vx) {}
    default void setVelocityY(double vy) {}
    default double getMass() { return 1.0; }

    CollisionType getCollisionType();

    void onCollision(ICollidable other);
}
