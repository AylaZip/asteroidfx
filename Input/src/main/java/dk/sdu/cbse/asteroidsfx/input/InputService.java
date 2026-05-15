package dk.sdu.cbse.asteroidsfx.input;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IUpdatable;

import dk.sdu.cbse.asteroidsfx.common.IInputService;
import dk.sdu.cbse.asteroidsfx.common.Key;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class InputService implements IInputService {

    private final Set<Key> down = Collections.synchronizedSet(EnumSet.noneOf(Key.class));
    private final Set<Key> pressed = Collections.synchronizedSet(EnumSet.noneOf(Key.class));

    public InputService(Scene scene) {
        scene.setOnKeyPressed(e -> {
            Key k = map(e.getCode());
            if (k == null) return;

            if (down.add(k)) {
                pressed.add(k);
            }
        });

        scene.setOnKeyReleased(e -> {
            Key k = map(e.getCode());
            if (k == null) return;
            down.remove(k);
        });
    }

    @Override
    public boolean isDown(Key key) {
        return down.contains(key);
    }

    @Override
    public boolean wasPressed(Key key) {
        return pressed.remove(key);
    }

    @Override
    public void clearPressed() {
        pressed.clear();
    }

    private static Key map(KeyCode code) {
        return switch (code) {
            case LEFT -> Key.LEFT;
            case RIGHT -> Key.RIGHT;
            case UP -> Key.UP;
            case DOWN -> Key.DOWN;
            case SPACE -> Key.SPACE;
            case ENTER -> Key.ENTER;
            case ESCAPE -> Key.ESCAPE;
            default -> null;
        };
    }
}