package dk.sdu.cbse.asteroidsfx.asteroid;

import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.ICollidable;
import dk.sdu.cbse.asteroidsfx.common.CollisionType;

import java.util.Random;

public class AsteroidEntity implements IEntity, ICollidable {
    private final IGameContext context;
    private double x, y, velocityX, velocityY, angle, rotationSpeed;
    private final AsteroidSize size;
    private static final Random random = new Random();
    private final double[] shapeX;
    private final double[] shapeY;
    private static final int POINTS = 10;

    public AsteroidEntity(IGameContext context, AsteroidSize size, double x, double y) {
        this.context = context;
        this.size = size;
        this.x = x;
        this.y = y;
        
        double speed = 50 + random.nextDouble() * 50;
        double moveAngle = random.nextDouble() * Math.PI * 2;
        this.velocityX = Math.cos(moveAngle) * speed;
        this.velocityY = Math.sin(moveAngle) * speed;
        this.rotationSpeed = (random.nextDouble() - 0.5) * 2;

        this.shapeX = new double[POINTS];
        this.shapeY = new double[POINTS];
        double r = size.getRadius();
        for (int i = 0; i < POINTS; i++) {
            double a = i * 2 * Math.PI / POINTS;
            double noise = 0.7 + random.nextDouble() * 0.6;
            shapeX[i] = Math.cos(a) * r * noise;
            shapeY[i] = Math.sin(a) * r * noise;
        }
    }

    @Override
    public void update(double dt) {
        x += velocityX * dt;
        y += velocityY * dt;
        angle += rotationSpeed * dt;

        if (x < -getRadius() * 1.5) x = 800 + getRadius() * 1.5;
        else if (x > 800 + getRadius() * 1.5) x = -getRadius() * 1.5;
        
        if (y < -getRadius() * 1.5) y = 600 + getRadius() * 1.5;
        else if (y > 600 + getRadius() * 1.5) y = -getRadius() * 1.5;
    }

    @Override
    public void draw(IRenderer renderer) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        
        double[] transformedX = new double[POINTS];
        double[] transformedY = new double[POINTS];
        
        for (int i = 0; i < POINTS; i++) {
            transformedX[i] = x + (shapeX[i] * cos - shapeY[i] * sin);
            transformedY[i] = y + (shapeX[i] * sin + shapeY[i] * cos);
        }
        
        renderer.drawPolygon(transformedX, transformedY, POINTS);
    }

    @Override public double getX() { return x; }
    @Override public double getY() { return y; }
    @Override public double getRadius() { return size.getRadius(); }

    @Override public double getVelocityX() { return velocityX; }
    @Override public double getVelocityY() { return velocityY; }
    @Override public void setVelocityX(double vx) { this.velocityX = vx; }
    @Override public void setVelocityY(double vy) { this.velocityY = vy; }
    @Override public double getMass() { return size.getRadius() * 2.0; }

    @Override
    public void onCollision(ICollidable other) {
        if (other.getOwner() == this) return;
        if (other.getCollisionType() == CollisionType.BULLET) {
            
            if (size.canSplit()) {
                context.getAsteroidSpawner().spawnAsteroid(size.getNextSize().name(), x, y);
                context.getAsteroidSpawner().spawnAsteroid(size.getNextSize().name(), x, y);
                context.getGameFlowListener().onAsteroidsSpawned(2);
            }

            context.getWorld().removeEntity(this);
            context.getGameFlowListener().onAsteroidDestroyed(size.getPoints());
        }
    }

    @Override public CollisionType getCollisionType() { return CollisionType.ASTEROID; }
}
