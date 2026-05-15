package dk.sdu.cbse.asteroidsfx.main;

import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(GameConfig.class)
public class ModuleLayerIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Collection<? extends IGamePlugin> gamePlugins;

    @Test
    public void testSpringContextLoads() {
        assertNotNull(applicationContext, "Spring context should be initialized");
    }

    @Test
    public void testPluginsAreInjected() {
        assertNotNull(gamePlugins, "Game plugins collection should be injected");
        System.out.println("Integration Test - Plugins found: " + gamePlugins.size());
    }
}
