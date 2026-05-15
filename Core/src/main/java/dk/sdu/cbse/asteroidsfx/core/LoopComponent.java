package dk.sdu.cbse.asteroidsfx.core;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IRenderer;
import dk.sdu.cbse.asteroidsfx.common.IEntity;
import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class LoopComponent {

    private volatile boolean running = false;

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
    }

    public void run(IGameContext context) {
        IRenderer renderer = context.getRenderer();
        long last = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            double dt = (now - last) / 1_000_000_000.0;
            last = now;

            context.getInput().beginFrame();

            // Make a copy of entities so we don't get errors if the list changes
            List<IEntity> entities = List.copyOf(context.getWorld().getEntities());

            for (IEntity e : entities) e.update(dt);

            // Wait for JavaFX to finish drawing before we continue
            CountDownLatch renderDone = new CountDownLatch(1);
            AtomicReference<Throwable> renderError = new AtomicReference<>();
            Platform.runLater(() -> {
                try {
                    renderer.beginFrame();
                    for (IEntity e : entities) e.draw(renderer);
                    renderer.endFrame();
                } catch (Throwable t) {
                    renderError.set(t);
                } finally {
                    renderDone.countDown();
                }
            });

            try {
                if (!renderDone.await(100, TimeUnit.MILLISECONDS)) {
                    System.err.println("Render frame timeout on JavaFX thread");
                }
                Throwable t = renderError.get();
                if (t != null) {
                    t.printStackTrace();
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                break;
            }

            try { 
                Thread.sleep(16); 
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
