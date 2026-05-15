package dk.sdu.cbse.asteroidsfx.collision;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.ICollidable;
import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;

import java.util.List;

public class CollisionSystem implements IEntity {
    private final IGameContext context;

    public CollisionSystem(IGameContext context) {
        this.context = context;
    }

    @Override
    public void update(double dt) {
        List<IEntity> entities = context.getWorld().getEntities();

        List<ICollidable> collidables = entities.stream()
            .filter(e -> e instanceof ICollidable)
            .map(e -> (ICollidable) e)
            .toList();

        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);

                if (checkCollision(a, b)) {
                    handleCollision(a, b);
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
    }

    private void handleCollision(ICollidable a, ICollidable b) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance == 0) return;

        double nx = dx / distance;
        double ny = dy / distance;

        double rvx = b.getVelocityX() - a.getVelocityX();
        double rvy = b.getVelocityY() - a.getVelocityY();

        double velAlongNormal = rvx * nx + rvy * ny;

        if (velAlongNormal > 0) return;

        double e = 0.8;

        double j = -(1 + e) * velAlongNormal;
        j /= (1 / a.getMass() + 1 / b.getMass());

        double impulseX = j * nx;
        double impulseY = j * ny;

        a.setVelocityX(a.getVelocityX() - (1 / a.getMass()) * impulseX);
        a.setVelocityY(a.getVelocityY() - (1 / a.getMass()) * impulseY);
        b.setVelocityX(b.getVelocityX() + (1 / b.getMass()) * impulseX);
        b.setVelocityY(b.getVelocityY() + (1 / b.getMass()) * impulseY);

        double percent = 0.2;
        double slop = 0.01;
        double penetration = a.getRadius() + b.getRadius() - distance;
        double correction = Math.max(penetration - slop, 0.0) / (1 / a.getMass() + 1 / b.getMass()) * percent;

    }

    private boolean checkCollision(ICollidable a, ICollidable b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double minDistance = a.getRadius() + b.getRadius();
        return distance < minDistance;
    }

    @Override
    public void draw(IRenderer renderer) {

    }
}
