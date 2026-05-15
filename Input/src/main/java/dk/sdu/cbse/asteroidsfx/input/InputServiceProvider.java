package dk.sdu.cbse.asteroidsfx.input;

import dk.sdu.cbse.asteroidsfx.common.IInputService;
import dk.sdu.cbse.asteroidsfx.common.IInputServiceProvider;
import javafx.scene.Scene;

public class InputServiceProvider implements IInputServiceProvider {
    
    @Override
    public IInputService create(Object context) {
        Scene scene = (Scene) context;
        return new InputService(scene);
    }
}
