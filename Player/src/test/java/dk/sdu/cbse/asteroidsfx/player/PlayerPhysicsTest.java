package dk.sdu.cbse.asteroidsfx.player;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGameFlowListener;
import dk.sdu.cbse.asteroidsfx.common.IInputService;
import dk.sdu.cbse.asteroidsfx.common.Key;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerPhysicsTest {

    private final PlayerEntity player;
    private final IGameContext context;
    private final IInputService input;
    private final IGameFlowListener flowListener;

    public PlayerPhysicsTest() {
        context = mock(IGameContext.class);
        input = mock(IInputService.class);
        flowListener = mock(IGameFlowListener.class);
        
        when(context.getInput()).thenReturn(input);
        when(context.getGameFlowListener()).thenReturn(flowListener);
        when(context.getBulletSpawners()).thenReturn(Collections.emptyList());
        when(flowListener.isWaveActive()).thenReturn(true);
        
        player = new PlayerEntity(context);
    }

    @Test
    public void testPlayerAcceleration() {
        double initialY = player.getY();
        
        when(input.isDown(Key.UP)).thenReturn(true);
        
        player.update(0.1);
        
        assertTrue(player.getVelocityY() < 0, "Player should accelerate upwards");
        assertTrue(player.getY() < initialY, "Player position should move upwards");
    }

    @Test
    public void testPlayerFriction() {
        player.setVelocityX(100);
        player.setVelocityY(100);
        
        when(input.isDown(any())).thenReturn(false);
        
        player.update(0.1);
        
        assertTrue(Math.abs(player.getVelocityX()) < 100, "Velocity X should decrease due to friction");
        assertTrue(Math.abs(player.getVelocityY()) < 100, "Velocity Y should decrease due to friction");
    }

    @Test
    public void testPlayerRotation() {
        when(input.isDown(Key.LEFT)).thenReturn(true);
        player.update(0.1);
        
        when(input.isDown(Key.LEFT)).thenReturn(false);
        when(input.isDown(Key.UP)).thenReturn(true);
        
        player.update(0.1);
        
        assertNotEquals(0, player.getVelocityX(), "Player should have X velocity after rotating and thrusting");
    }
}
