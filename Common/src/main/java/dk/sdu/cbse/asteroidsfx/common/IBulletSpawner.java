package dk.sdu.cbse.asteroidsfx.common;

public interface IBulletSpawner {
    void spawnBullet(double x, double y, double angle, double velocityX, double velocityY, IEntity entity);
}
