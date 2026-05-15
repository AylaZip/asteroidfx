package dk.sdu.cbse.asteroidsfx.core;

import dk.sdu.cbse.asteroidsfx.common.IGameContext;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;

import java.util.Collection;
import java.util.ServiceLoader;

public class PluginLoader {

    public Collection<? extends IGamePlugin> loadPlugins() {
        return ServiceLoader.load(IGamePlugin.class).stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    public void startPlugins(Collection<? extends IGamePlugin> plugins, IGameContext context) {
        for (IGamePlugin plugin : plugins) {
            plugin.start(context);
        }
    }

    public void stopPlugins(Collection<? extends IGamePlugin> plugins, IGameContext context) {
        for (IGamePlugin plugin : plugins) {
            plugin.stop(context);
        }
    }
}
