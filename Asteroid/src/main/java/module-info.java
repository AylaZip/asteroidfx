/**
 * This module handles the asteroids in the game.
 */
module Asteroid {
    requires Common;
    provides dk.sdu.cbse.asteroidsfx.common.IGamePlugin with dk.sdu.cbse.asteroidsfx.asteroid.AsteroidPlugin;
    provides dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawnerProvider with dk.sdu.cbse.asteroidsfx.asteroid.AsteroidSpawnerProvider;
}
