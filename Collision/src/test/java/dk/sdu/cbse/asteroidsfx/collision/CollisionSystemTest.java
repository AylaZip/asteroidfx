package dk.sdu.cbse.asteroidsfx.collision;

import dk.sdu.cbse.asteroidsfx.common.ICollidable;
import dk.sdu.cbse.asteroidsfx.common.IEntity;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class CollisionSystemTest {

    private CollisionSystem collisionSystem;
    private IGameContext context;
    private IGameWorld world;

    @BeforeEach
    public void setUp() {
        context = mock(IGameContext.class);
        world = mock(IGameWorld.class);
        when(context.getWorld()).thenReturn(world);
        collisionSystem = new CollisionSystem(context);
    }

    @Test
    public void testCollisionDetection_WhenEntitiesOverlap() {
        ICollidable entityA = mock(ICollidable.class);
        when(entityA.getX()).thenReturn(100.0);
        when(entityA.getY()).thenReturn(100.0);
        when(entityA.getRadius()).thenReturn(20.0);

        ICollidable entityB = mock(ICollidable.class);
        when(entityB.getX()).thenReturn(110.0);
        when(entityB.getY()).thenReturn(110.0);
        when(entityB.getRadius()).thenReturn(20.0);

        when(world.getEntities()).thenReturn(Arrays.asList((IEntity) entityA, (IEntity) entityB));

        collisionSystem.update(0.016);

        verify(entityA, times(1)).onCollision(entityB);
        verify(entityB, times(1)).onCollision(entityA);
    }

    @Test
    public void testNoCollision_WhenEntitiesAreFarApart() {
        ICollidable entityA = mock(ICollidable.class);
        when(entityA.getX()).thenReturn(100.0);
        when(entityA.getY()).thenReturn(100.0);
        when(entityA.getRadius()).thenReturn(10.0);

        ICollidable entityB = mock(ICollidable.class);
        when(entityB.getX()).thenReturn(300.0);
        when(entityB.getY()).thenReturn(300.0);
        when(entityB.getRadius()).thenReturn(10.0);

        when(world.getEntities()).thenReturn(Arrays.asList((IEntity) entityA, (IEntity) entityB));

        collisionSystem.update(0.016);

        verify(entityA, never()).onCollision(any());
        verify(entityB, never()).onCollision(any());
    }
}
