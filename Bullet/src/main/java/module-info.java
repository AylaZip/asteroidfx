module Bullet {
    requires Common;

    provides dk.sdu.cbse.asteroidsfx.common.IGamePlugin with dk.sdu.cbse.asteroidsfx.bullet.Bullet;
    provides dk.sdu.cbse.asteroidsfx.common.IBulletSpawner with dk.sdu.cbse.asteroidsfx.bullet.BulletSpawnerService;
}
