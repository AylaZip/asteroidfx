package dk.sdu.cbse.asteroidsfx.core;

import java.util.*;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGameFlowListener;
import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.Key;
import dk.sdu.cbse.asteroidsfx.common.ICollidable;
import dk.sdu.cbse.asteroidsfx.common.CollisionType;

public class GameFlowSystem implements IGameFlowListener, IEntity {
    private GameState currentGameState;
    private int level;
    private int lives;
    private int score;
    private int asteroidsRemaining;
    private double readyTimer;
    private boolean shouldClearAsteroids = false;
    private static final int MAX_LIVES = 3;
    private static final double READY_DELAY = 3.0;
    private IGameContext context;
    private final ScoringClient scoringClient;
    
    public GameFlowSystem(IGameContext context, ScoringClient scoringClient) {
        this.context = context;
        this.scoringClient = scoringClient;
        this.currentGameState = GameState.MENU;
        this.level = 1;
        this.lives = MAX_LIVES;
        this.score = 0;
        this.asteroidsRemaining = 0;
        this.readyTimer = 0;
    }
    
    public void setContext(IGameContext context) {
        this.context = context;

        if (scoringClient != null) {
            new Thread(() -> {
                this.score = scoringClient.getScore();
            }).start();
        }
    }
    
    @Override
    public void update(double dt) {
        if (context == null) {
            return;
        }


        if (shouldClearAsteroids) {
            List<IEntity> toRemove = new ArrayList<>();
            for (IEntity entity : context.getWorld().getEntities()) {
                if (entity instanceof ICollidable && 
                    ((ICollidable) entity).getCollisionType() == CollisionType.ASTEROID) {
                    toRemove.add(entity);
                }
            }

            for (IEntity entity : toRemove) {
                context.getWorld().removeEntity(entity);
            }
            shouldClearAsteroids = false;
        }

        if (currentGameState == GameState.PLAYING) {
            return;
        }

        if (currentGameState == GameState.READY) {
            readyTimer += dt;
            if (readyTimer >= READY_DELAY) {
                beginWave();
            }
            return;
        }

        if (!context.getInput().wasPressed(Key.ENTER)) {
            return;
        }

        if (currentGameState == GameState.MENU) {
            startGame();
            return;
        }

        if (currentGameState == GameState.GAME_OVER) {
            resetToMenu();
        }
    }

    @Override
    public void draw(IRenderer renderer) {
    }
    
    public void startGame() {
        if (currentGameState == GameState.MENU) {
            level = 1;
            lives = MAX_LIVES;
            score = 0;
            if (scoringClient != null) {
                new Thread(scoringClient::resetScore).start();
            }
            startLevel();
        }
    }
    
    public void startLevel() {
        currentGameState = GameState.READY;
        readyTimer = 0;
    }
    
    public void beginWave() {
        if (currentGameState == GameState.READY) {
            currentGameState = GameState.PLAYING;
            context.getInput().clearPressed();
            context.getGameFlowListener().onWaveStart();
            spawnWave();
        }
    }
    
    public void spawnWave() {
        List<String> wave = generateWave(level);
        asteroidsRemaining = wave.size();
        
        for (String sizeString : wave) {
            context.getAsteroidSpawner().spawnAsteroid(sizeString);
        }
        System.out.println("Wave spawned: Level " + level + " with " + asteroidsRemaining + " asteroids");
    }
    
    public void asteroidDestroyed(int points) {
        this.score += points;
        if (scoringClient != null) {
            new Thread(() -> scoringClient.addScore(points)).start();
        }
        asteroidsRemaining--;
        checkWaveClear();
    }

    public void asteroidsSpawned(int count) {
        if (count <= 0) {
            return;
        }
        asteroidsRemaining += count;
    }
    
    private void checkWaveClear() {
        if (asteroidsRemaining <= 0 && currentGameState == GameState.PLAYING) {
            waveCleared();
        }
    }
    
    public void waveCleared() {
        currentGameState = GameState.READY;
        level++;
        readyTimer = 0;
    }
    
    public void playerDeath() {
        lives--;
        System.out.println("Player died! Lives remaining: " + lives);
        if (lives <= 0) {
            gameOver();
        }

    }
    
    public void gameOver() {
        currentGameState = GameState.GAME_OVER;

        shouldClearAsteroids = true;

        context.getInput().clearPressed();
    }
    
    public void resetToMenu() {
        currentGameState = GameState.MENU;
        level = 1;
        score = 0;
        if (scoringClient != null) {
            new Thread(scoringClient::resetScore).start();
        }
        lives = MAX_LIVES;
    }
    

    @Override
    public void onAsteroidDestroyed(int points) {
        asteroidDestroyed(points);
    }

    @Override
    public void onAsteroidsSpawned(int count) {
        asteroidsSpawned(count);
    }
    
    @Override
    public void onPlayerDeath() {
        playerDeath();
    }

    @Override
    public void onWaveStart() {

    }

    @Override
    public boolean isWaveActive() {
        return currentGameState == GameState.PLAYING;
    }

    @Override
    public void onEnemyDestroyed(int points) {
        this.score += points;
        if (scoringClient != null) {
            new Thread(() -> scoringClient.addScore(points)).start();
        }
        System.out.println("Enemy destroyed! +" + points + " points");
    }

    @Override
    public int getLevel() {
        return level;
    }

    private List<String> generateWave(int level) {
        List<String> wave = new ArrayList<>();

        if (level == 1) {
            wave.add("SMALL");
        } else if (level == 2) {
            wave.add("MEDIUM");
        } else if (level == 3) {
            wave.add("LARGE");
        } else {
            int levelsAfterFirstLarge = level - 3;
            int completedCycles = levelsAfterFirstLarge / 3;
            int phase = levelsAfterFirstLarge % 3;

            int largeCount = completedCycles + 1;
            for (int i = 0; i < largeCount; i++) {
                wave.add("LARGE");
            }

            if (phase == 1) {
                wave.add("SMALL");
            } else if (phase == 2) {
                wave.add("MEDIUM");
            }
        }

        return wave;
    }

    public GameState getCurrentState() { return currentGameState; }
    public int getScore() { 
        return score; 
    }
    public int getLives() { return lives; }
    public int getAsteroidsRemaining() { return asteroidsRemaining; }
}
