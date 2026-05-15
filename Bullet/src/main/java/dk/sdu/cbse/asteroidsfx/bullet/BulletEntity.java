package dk.sdu.cbse.asteroidsfx.bullet;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.ICollidable;
import dk.sdu.cbse.asteroidsfx.common.CollisionType;

public class BulletEntity implements IEntity, ICollidable {
    private final IGameContext context;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double bulletLength = 10;
    private double lifetime = 2.0;
    private double angle;
    private final double bulletSpeed = 600;
    private IEntity owner;
    
    public BulletEntity(IGameContext context, double x, double y, double angle, double velocityX, double velocityY, IEntity owner) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.owner = owner;

        double thrustX = Math.sin(angle);
        double thrustY = -Math.cos(angle);
        this.velocityX = thrustX * bulletSpeed + velocityX;
        this.velocityY = thrustY * bulletSpeed + velocityY;
    }

    @Override
    public void update(double dt) {
        x += velocityX * dt;
        y += velocityY * dt;

        lifetime -= dt;

        if (lifetime <= 0 || x < 0 || x > 800 || y < 0 || y > 600) {
            context.getWorld().removeEntity(this);
        }
    }

    @Override
    public void draw(IRenderer renderer) {
        double endX = x + bulletLength * Math.sin(angle);
        double endY = y + bulletLength * -Math.cos(angle);

        renderer.drawLine(x, y, endX, endY);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getRadius() {
        return 2;
    }

    @Override
    public void onCollision(ICollidable other) {
        if (other == owner || other.getOwner() == owner) {
            return;
        }

        context.getWorld().removeEntity(this);
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.BULLET;
    }

    @Override
    public IEntity getOwner() {
        return owner;
    }
}
