package dk.sdu.cbse.asteroidsfx.enemy;

import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;

import java.util.Random;

public class EnemyPlugin implements IGamePlugin, IEntity {
    private IGameContext context;
    private EnemyEntity enemyEntity;
    private boolean wasWaveActive = false;
    private static final Random RANDOM = new Random();

    public EnemyPlugin() {
    }

    @Override
    public void start(IGameContext context) {
        this.context = context;
        context.getWorld().addEntity(this);
        System.out.println("Enemy plugin started");
    }

    @Override
    public void stop(IGameContext context) {
        despawnEnemy();
        context.getWorld().removeEntity(this);
        System.out.println("Enemy plugin stopping!");
    }

    @Override
    public void update(double dt) {
        if (context == null) return;
        boolean isWaveActive = context.getGameFlowListener().isWaveActive();
        
        if (isWaveActive && !wasWaveActive) {
            spawnEnemy();
        }
        
        if (!isWaveActive && wasWaveActive) {
            despawnEnemy();
        }
        
        wasWaveActive = isWaveActive;
    }
    
    @Override
    public void draw(IRenderer renderer) {
    }

    private void spawnEnemy() {
        if (enemyEntity == null) {
            int level = context.getGameFlowListener().getLevel();
            int spawnChance = Math.min(level * 5, 100);
            if (RANDOM.nextInt(100) < spawnChance) {
                enemyEntity = new EnemyEntity(context);
                context.getWorld().addEntity(enemyEntity);
            }
        }
    }

    private void despawnEnemy() {
        if (enemyEntity != null) {
            context.getWorld().removeEntity(enemyEntity);
            enemyEntity = null;
        }
    }
}
