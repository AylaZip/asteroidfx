package dk.sdu.cbse.asteroidsfx.asteroid;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IUpdatable;

public enum AsteroidSize {
    SMALL(12, 90, 140, 100, null),
    MEDIUM(22, 55, 95, 50, SMALL),
    LARGE(34, 25, 60, 20, MEDIUM);

    private final double radius;
    private final double minSpeed;
    private final double maxSpeed;
    private final int points;
    private final AsteroidSize nextSize;

    AsteroidSize(double radius, double minSpeed, double maxSpeed, int points, AsteroidSize nextSize) {
        this.radius = radius;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.points = points;
        this.nextSize = nextSize;
    }

    public double getRadius() {
        return radius;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public int getPoints() {
        return points;
    }

    public AsteroidSize getNextSize() {
        return nextSize;
    }

    public boolean canSplit() {
        return nextSize != null;
    }

    public double randomSpeed(double randomValue0to1) {
        return minSpeed + (maxSpeed - minSpeed) * randomValue0to1;
    }
}
