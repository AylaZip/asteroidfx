package dk.sdu.cbse.asteroidsfx.player;

import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.Key;
import dk.sdu.cbse.asteroidsfx.common.ICollidable;
import dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;
import dk.sdu.cbse.asteroidsfx.common.CollisionType;

public class PlayerEntity implements IEntity, ICollidable {
    private final IGameContext context;

    private double x = 400;
    private double y = 300;
    private double velocityX = 0;
    private double velocityY = 0;
    private double angle = 0;
    private final double acceleration = 900;
    private final double maxSpeed = 260;
    private final double dragPerSecond = 4.5;
    private final double turnSpeed = 4.0;
    private final double shipSize = 18;
    private boolean wasWaveActive = false;
    private double invulnerabilityTimer = 0;
    private double spawnProtectionTimer = 0;
    private boolean isBlinkingInvulnerability = false;
    private static final double LIFE_LOSS_INVULNERABILITY = 3.0;
    private static final double HITPOINT_PROTECTION = 1.0;
    private int health = 3;

    public PlayerEntity(IGameContext context) {
        this.context = context;
    }

    @Override
    public void update(double dt) {
        if (invulnerabilityTimer > 0) {
            invulnerabilityTimer -= dt;
            if (invulnerabilityTimer <= 0) {
                isBlinkingInvulnerability = false;
            }
        }
        if (spawnProtectionTimer > 0) {
            spawnProtectionTimer -= dt;
            if (spawnProtectionTimer <= 0) {
                isBlinkingInvulnerability = false;
            }
        }

        boolean isWaveActive = context.getGameFlowListener().isWaveActive();
        
        if (!isWaveActive && wasWaveActive) {
            resetPosition();
        }

        if (isWaveActive && !wasWaveActive) {
            spawnProtectionTimer = LIFE_LOSS_INVULNERABILITY;
            isBlinkingInvulnerability = false;
        }

        wasWaveActive = isWaveActive;
        
        if (!isWaveActive) {
            return;
        }

        if (context.getInput().isDown(Key.LEFT)) {
            angle -= turnSpeed * dt;
        }
        if (context.getInput().isDown(Key.RIGHT)) {
            angle += turnSpeed * dt;
        }

        if (context.getInput().isDown(Key.UP)) {
            double thrustX = Math.sin(angle);
            double thrustY = -Math.cos(angle);
            velocityX += thrustX * acceleration * dt;
            velocityY += thrustY * acceleration * dt;
        }

        if (!context.getInput().isDown(Key.UP)) {
            double dragFactor = Math.max(0, 1 - dragPerSecond * dt);
            velocityX *= dragFactor;
            velocityY *= dragFactor;
        }

        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (speed > maxSpeed) {
            double scale = maxSpeed / speed;
            velocityX *= scale;
            velocityY *= scale;
        }

        x += velocityX * dt;
        y += velocityY * dt;

        if (x < -shipSize) x = 800 + shipSize;
        else if (x > 800 + shipSize) x = -shipSize;

        if (y < -shipSize) y = 600 + shipSize;
        else if (y > 600 + shipSize) y = -shipSize;

        if (context.getInput().wasPressed(Key.SPACE)) {
            context.getBulletSpawners().stream().findFirst().ifPresent(spawner -> {
                double bulletX = x + Math.sin(angle) * shipSize;
                double bulletY = y - Math.cos(angle) * shipSize;
                spawner.spawnBullet(bulletX, bulletY, angle, velocityX, velocityY, this);
            });
        }
    }

    @Override
    public void draw(IRenderer renderer) {
        // Only blink if we are in the "blinking" invulnerability state (after life loss or spawn)
        if (isBlinkingInvulnerability && (invulnerabilityTimer > 0 || spawnProtectionTimer > 0) && (System.currentTimeMillis() % 200 < 100)) {
            return;
        }

        double noseLocalX = 0;
        double noseLocalY = -shipSize;
        double leftLocalX = -shipSize * 0.75;
        double leftLocalY = shipSize;
        double rightLocalX = shipSize * 0.75;
        double rightLocalY = shipSize;

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double noseX = x + (noseLocalX * cos - noseLocalY * sin);
        double noseY = y + (noseLocalX * sin + noseLocalY * cos);
        double leftX = x + (leftLocalX * cos - leftLocalY * sin);
        double leftY = y + (leftLocalX * sin + leftLocalY * cos);
        double rightX = x + (rightLocalX * cos - rightLocalY * sin);
        double rightY = y + (rightLocalX * sin + rightLocalY * cos);

        renderer.drawLine(noseX, noseY, leftX, leftY);
        renderer.drawLine(leftX, leftY, rightX, rightY);
        renderer.drawLine(rightX, rightY, noseX, noseY);
    }

    @Override public double getX() { return x; }
    @Override public double getY() { return y; }
    @Override public double getRadius() { return shipSize; }
    
    @Override public double getVelocityX() { return velocityX; }
    @Override public double getVelocityY() { return velocityY; }
    @Override public void setVelocityX(double vx) { this.velocityX = vx; }
    @Override public void setVelocityY(double vy) { this.velocityY = vy; }
    @Override public double getMass() { return 10.0; }

    @Override
    public void onCollision(ICollidable other) {
        if (other.getOwner() == this) return;
        
        if (invulnerabilityTimer <= 0 && spawnProtectionTimer <= 0) {
            boolean hit = false;
            
            if (other.getCollisionType() == CollisionType.ASTEROID) {
                hit = true;
            } else if (other.getCollisionType() == CollisionType.ENEMY) {
                hit = true;
            } else if (other.getCollisionType() == CollisionType.BULLET && 
                       other.getOwner() != null && 
                       ((ICollidable)other.getOwner()).getCollisionType() == CollisionType.ENEMY) {
                hit = true;
            }
            
            if (hit) {
                health--;
                
                if (health <= 0) {
                    context.getGameFlowListener().onPlayerDeath();
                    health = 3; 
                    invulnerabilityTimer = LIFE_LOSS_INVULNERABILITY;
                    isBlinkingInvulnerability = true;
                } else {
                    invulnerabilityTimer = HITPOINT_PROTECTION;
                    isBlinkingInvulnerability = false;
                    System.out.println("Player hit! Health left: " + health);
                }
            }
        }
    }

    private void resetPosition() {
        x = 400; y = 300; velocityX = 0; velocityY = 0; angle = 0; health = 3;
        invulnerabilityTimer = 0;
        spawnProtectionTimer = 0;
        isBlinkingInvulnerability = false;
    }

    @Override public CollisionType getCollisionType() { return CollisionType.PLAYER; }
}
