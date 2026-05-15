package dk.sdu.cbse.asteroidsfx.main;

import java.util.Collection;

import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IInputServiceProvider;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.IRendererProvider;
import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawnerProvider;
import dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;
import dk.sdu.cbse.asteroidsfx.core.GameContext;
import dk.sdu.cbse.asteroidsfx.core.GameWorld;
import dk.sdu.cbse.asteroidsfx.core.LoopComponent;
import dk.sdu.cbse.asteroidsfx.core.NullInputService;
import dk.sdu.cbse.asteroidsfx.core.NullRenderer;
import dk.sdu.cbse.asteroidsfx.core.NullAsteroidSpawner;
import dk.sdu.cbse.asteroidsfx.core.GameFlowSystem;
import dk.sdu.cbse.asteroidsfx.core.HudSystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {
    private LoopComponent loop;
    private AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) {
        // Start Spring to help manage our game parts
        context = new AnnotationConfigApplicationContext(GameConfig.class);

        var root = new Pane();
        var scene = new Scene(root, 800, 600);
        
        // Find the Input service. If not found, use a 'Null' version so it doesn't crash.
        var inputProvider = context.getBean(IInputServiceProvider.class);
        var input = (inputProvider != null) ? inputProvider.create(scene) : new NullInputService();
        if (input instanceof NullInputService) {
            System.out.println("No Input module found - using NullInputService");
        }
        
        stage.setTitle("AsteroidsFX");
        stage.setScene(scene);

        var canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        // Find the Renderer. If not found, use a 'Null' version.
        var rendererProvider = context.getBean(IRendererProvider.class);
        IRenderer renderer = (rendererProvider != null) ? rendererProvider.create(canvas.getGraphicsContext2D()) : new NullRenderer();
        if (renderer instanceof NullRenderer) {
            System.out.println("No Renderer module found - using NullRenderer");
        }

        var world = new GameWorld();
        var scoringClient = context.getBean(dk.sdu.cbse.asteroidsfx.core.ScoringClient.class);
        var gameFlowSystem = new GameFlowSystem(null, scoringClient);
        
        var asteroidSpawnerProvider = context.getBean(IAsteroidSpawnerProvider.class);
        var asteroidSpawner = (asteroidSpawnerProvider != null) ? asteroidSpawnerProvider.create(null) : new NullAsteroidSpawner();
        
        Collection<? extends dk.sdu.cbse.asteroidsfx.common.IBulletSpawner> bulletSpawners = context.getBean("bulletSpawners", Collection.class);

        var gameContext = new GameContext(world, renderer, input, gameFlowSystem, asteroidSpawner, bulletSpawners);
        gameFlowSystem.setContext(gameContext);

        world.addEntity(gameFlowSystem);
        
        var hudSystem = new HudSystem(gameFlowSystem, renderer);
        world.addEntity(hudSystem);

        // Start all the game plugins we found
        Collection<? extends IGamePlugin> plugins = context.getBean("gamePlugins", Collection.class);
        System.out.println("Plugins loaded via Spring: " + plugins.size());
        
        for (IGamePlugin plugin : plugins) {
            System.out.println("Starting plugin: " + plugin.getName());
            plugin.start(gameContext);
        }

        // Setup and start the game loop thread
        loop = new LoopComponent();
        loop.start();
        Thread gameThread = new Thread(() -> loop.run(gameContext), "game-loop");
        gameThread.setDaemon(true);
        gameThread.start();

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stopping application...");
        
        if (loop != null) {
            loop.stop();
        }

        if (context != null) {
            context.close();
        }
        
        System.out.println("Cleanup complete.");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
