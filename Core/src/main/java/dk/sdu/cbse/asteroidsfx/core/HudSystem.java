package dk.sdu.cbse.asteroidsfx.core;

import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;

public class HudSystem implements IEntity {
    private final GameFlowSystem gameFlowSystem;
    private final IRenderer renderer;
    
    public HudSystem(GameFlowSystem gameFlowSystem, IRenderer renderer) {
        this.gameFlowSystem = gameFlowSystem;
        this.renderer = renderer;
    }
    
    @Override
    public void update(double dt) {

    }
    
    @Override
    public void draw(IRenderer renderer) {
        int score = gameFlowSystem.getScore();
        int level = gameFlowSystem.getLevel();
        int lives = gameFlowSystem.getLives();
        GameState state = gameFlowSystem.getCurrentState();
        
        renderer.drawText(10, 20, "Score: " + score, "white");
        renderer.drawText(10, 40, "Level: " + level, "white");
        renderer.drawText(10, 60, "Lives: " + lives, "white");
        
        if (state == GameState.MENU) {
            renderer.drawText(400, 300, "PRESS ENTER TO START", "white", true);
        } else if (state == GameState.READY) {
            renderer.drawText(400, 300, "LEVEL " + level, "white", true);
        } else if (state == GameState.GAME_OVER) {
            renderer.drawText(400, 300, "GAME OVER", "white", true);
            renderer.drawText(400, 350, "Final Score: " + score, "white", true);
            renderer.drawText(400, 400, "Press ENTER to return to menu", "white", true);
        }
    }
}
