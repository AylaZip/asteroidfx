package dk.sdu.cbse.asteroidsfx.enemy;

import dk.sdu.cbse.asteroidsfx.common.*;

import java.util.Random;
import java.util.ServiceLoader;

public class EnemyEntity implements IEntity, ICollidable {
    private final IGameContext context;
    private static final Random RANDOM = new Random();
    
    private double x;
    private double y;
    private double velocityX = 0;
    private double velocityY = 0;
    private double angle = 0;
    private int lives = 3;
    private boolean destroyed = false;
    
    private static final double SIZE = 20;
    private static final double MAX_SPEED = 80;
    private static final double ACCELERATION = 150;
    private static final double SHOOT_COOLDOWN = 2.0;
    private static final double SHOOT_RANGE = 300;
    private static final double ASTEROID_AVOIDANCE_RANGE = 100;
    private static final double SHOT_ACCURACY = 0.85;
    
    private double shootTimer = RANDOM.nextDouble() * SHOOT_COOLDOWN;
    private double invulnerabilityTimer = 0;
    private static final double INVULNERABILITY_DURATION = 0.5;

    public EnemyEntity(IGameContext context) {
        this.context = context;

        int edge = RANDOM.nextInt(4);
        switch (edge) {
            case 0: x = RANDOM.nextDouble() * 800; y = -SIZE; break;
            case 1: x = RANDOM.nextDouble() * 800; y = 600 + SIZE; break;
            case 2: x = -SIZE; y = RANDOM.nextDouble() * 600; break;
            default: x = 800 + SIZE; y = RANDOM.nextDouble() * 600; break;
        }
    }

    @Override
    public void update(double dt) {
        if (destroyed || !context.getGameFlowListener().isWaveActive()) {
            return;
        }

        invulnerabilityTimer = Math.max(0, invulnerabilityTimer - dt);
        shootTimer -= dt;

        IEntity player = findPlayer();
        if (player == null) return;

        double playerX = ((ICollidable) player).getX();
        double playerY = ((ICollidable) player).getY();
        
        double toPlayerX = playerX - x;
        double toPlayerY = playerY - y;
        double distanceToPlayer = Math.sqrt(toPlayerX * toPlayerX + toPlayerY * toPlayerY);

        double avoidX = 0, avoidY = 0;
        for (IEntity entity : context.getWorld().getEntities()) {
            if (entity instanceof ICollidable) {
                ICollidable col = (ICollidable) entity;
                if (col.getCollisionType() == CollisionType.ASTEROID) {
                    double dx = x - col.getX();
                    double dy = y - col.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    
                    if (dist < ASTEROID_AVOIDANCE_RANGE && dist > 0) {
                        double strength = (ASTEROID_AVOIDANCE_RANGE - dist) / ASTEROID_AVOIDANCE_RANGE;
                        avoidX += (dx / dist) * strength;
                        avoidY += (dy / dist) * strength;
                    }
                }
            }
        }

        double desiredX = 0, desiredY = 0;
        if (distanceToPlayer > SHOOT_RANGE * 0.7) {
            desiredX = (toPlayerX / distanceToPlayer) * ACCELERATION;
            desiredY = (toPlayerY / distanceToPlayer) * ACCELERATION;
        } else if (distanceToPlayer < SHOOT_RANGE * 0.5) {
            desiredX = -(toPlayerX / distanceToPlayer) * ACCELERATION * 0.5;
            desiredY = -(toPlayerY / distanceToPlayer) * ACCELERATION * 0.5;
        }

        desiredX += avoidX * ACCELERATION * 2;
        desiredY += avoidY * ACCELERATION * 2;

        velocityX += desiredX * dt;
        velocityY += desiredY * dt;

        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (speed > MAX_SPEED) {
            velocityX = (velocityX / speed) * MAX_SPEED;
            velocityY = (velocityY / speed) * MAX_SPEED;
        }

        velocityX *= 0.98;
        velocityY *= 0.98;

        x += velocityX * dt;
        y += velocityY * dt;

        if (x < -SIZE) x = 800 + SIZE;
        else if (x > 800 + SIZE) x = -SIZE;
        if (y < -SIZE) y = 600 + SIZE;
        else if (y > 600 + SIZE) y = -SIZE;

        angle = Math.atan2(toPlayerY, toPlayerX) + Math.PI / 2;

        if (shootTimer <= 0 && distanceToPlayer < SHOOT_RANGE) {
            shoot(playerX, playerY, distanceToPlayer);
            shootTimer = SHOOT_COOLDOWN + RANDOM.nextDouble() * 0.5;
        }
    }

    private IEntity findPlayer() {
        for (IEntity entity : context.getWorld().getEntities()) {
            if (entity instanceof ICollidable) {
                ICollidable col = (ICollidable) entity;
                if (col.getCollisionType() == CollisionType.PLAYER) {
                    return entity;
                }
            }
        }
        return null;
    }

    private void shoot(double targetX, double targetY, double distance) {
        context.getBulletSpawners().stream().findFirst().ifPresent(spawner -> {
            double inaccuracy = (1.0 - SHOT_ACCURACY) * (RANDOM.nextDouble() - 0.5) * 2;
            double aimAngle = Math.atan2(targetY - y, targetX - x);
            aimAngle += inaccuracy;
            
            double bulletX = x + Math.cos(aimAngle) * SIZE;
            double bulletY = y + Math.sin(aimAngle) * SIZE;
            double bulletAngle = aimAngle + Math.PI / 2;
            
            spawner.spawnBullet(bulletX, bulletY, bulletAngle, velocityX, velocityY, this);
        });
    }

    @Override
    public void draw(IRenderer renderer) {
        if (destroyed) return;

        double width = SIZE * 2;
        double height = SIZE;

        double[] topX = new double[8];
        double[] topY = new double[8];
        for (int i = 0; i < 8; i++) {
            double t = Math.PI * i / 7.0;
            topX[i] = x + Math.cos(t) * width * 0.5;
            topY[i] = y - height * 0.3 - Math.sin(t) * height * 0.3;
        }
        renderer.drawPolygon(topX, topY, 8);
        
        double[] bottomX = {
            x - width * 0.5, x - width * 0.7, x - width * 0.5,
            x + width * 0.5, x + width * 0.7, x + width * 0.5
        };
        double[] bottomY = {
            y - height * 0.3, y, y + height * 0.3,
            y + height * 0.3, y, y - height * 0.3
        };
        renderer.drawPolygon(bottomX, bottomY, 6);
        
        renderer.drawLine(x - width * 0.5, y - height * 0.3, x + width * 0.5, y - height * 0.3);
    }

    @Override public double getX() { return x; }

    @Override public double getY() { return y; }

    @Override public double getRadius() { return SIZE; }

    @Override public double getVelocityX() { return velocityX; }
    @Override public double getVelocityY() { return velocityY; }
    @Override public void setVelocityX(double vx) { this.velocityX = vx; }
    @Override public void setVelocityY(double vy) { this.velocityY = vy; }
    @Override public double getMass() { return 15.0; }

    @Override
    public void onCollision(ICollidable other) {
        if (destroyed || other.getOwner() == this) {
            return;
        }

        if (invulnerabilityTimer > 0) {
            return;
        }

        CollisionType type = other.getCollisionType();
        if (type == CollisionType.BULLET || type == CollisionType.ASTEROID || type == CollisionType.PLAYER) {
            lives--;
            invulnerabilityTimer = INVULNERABILITY_DURATION;
            
            if (lives <= 0) {
                destroyed = true;
                context.getWorld().removeEntity(this);
                context.getGameFlowListener().onEnemyDestroyed(200);
            }
        }
    }

    @Override public CollisionType getCollisionType() { return CollisionType.ENEMY; }
}
