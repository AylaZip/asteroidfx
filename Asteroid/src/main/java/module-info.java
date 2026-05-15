module Asteroid {
    requires Common;

    provides dk.sdu.cbse.asteroidsfx.common.IGamePlugin with dk.sdu.cbse.asteroidsfx.asteroid.Asteroid;
    provides dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawnerProvider with dk.sdu.cbse.asteroidsfx.asteroid.AsteroidSpawnerProvider;
}
