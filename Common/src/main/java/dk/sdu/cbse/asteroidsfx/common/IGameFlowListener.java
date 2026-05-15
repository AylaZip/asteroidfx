package dk.sdu.cbse.asteroidsfx.common;

public interface IGameFlowListener {
    void onAsteroidDestroyed(int points);
    void onAsteroidsSpawned(int count);
    void onPlayerDeath();
    void onWaveStart();
    boolean isWaveActive();
    void onEnemyDestroyed(int points);
    int getLevel();
}
