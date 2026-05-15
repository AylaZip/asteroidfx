module Enemy {
    requires Common;

    uses dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;

    provides dk.sdu.cbse.asteroidsfx.common.IGamePlugin with dk.sdu.cbse.asteroidsfx.enemy.EnemyPlugin;
}
