package dk.sdu.cbse.asteroidsfx.core;
import dk.sdu.cbse.asteroidsfx.common.IGameWorld;
import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IUpdatable;

import dk.sdu.cbse.asteroidsfx.common.IInputService;
import dk.sdu.cbse.asteroidsfx.common.Key;

public class NullInputService implements IInputService {
    @Override public boolean isDown(Key key) { return false; }
    @Override public boolean wasPressed(Key key) { return false; }
    @Override public void clearPressed() { }
}
