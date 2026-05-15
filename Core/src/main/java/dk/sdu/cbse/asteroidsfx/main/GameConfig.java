package dk.sdu.cbse.asteroidsfx.main;

import dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawnerProvider;
import dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;
import dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
import dk.sdu.cbse.asteroidsfx.common.IInputServiceProvider;
import dk.sdu.cbse.asteroidsfx.common.IRendererProvider;
import dk.sdu.cbse.asteroidsfx.core.ScoringClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class GameConfig {

    private final ModuleLayer layer;

    public GameConfig() {
        this.layer = createLayer();
    }

    private ModuleLayer createLayer() {
        Path pluginsDir = Paths.get("plugins");
        ModuleFinder finder = ModuleFinder.of(pluginsDir);
        Set<String> roots = finder.findAll().stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .collect(Collectors.toSet());

        java.lang.module.Configuration configuration = ModuleLayer.boot()
                .configuration()
                .resolve(finder, ModuleFinder.of(), roots);

        return ModuleLayer.boot().defineModulesWithOneLoader(configuration, ClassLoader.getSystemClassLoader());
    }

    @Bean
    public Collection<? extends IGamePlugin> gamePlugins() {
        List<IGamePlugin> plugins = new ArrayList<>();
        ServiceLoader.load(IGamePlugin.class).forEach(plugins::add);

        if (layer != ModuleLayer.boot()) {
            ServiceLoader.load(layer, IGamePlugin.class).forEach(p -> {
                if (plugins.stream().noneMatch(existing -> existing.getClass().equals(p.getClass()))) {
                    plugins.add(p);
                }
            });
        }
        return plugins;
    }

    @Bean
    public Collection<? extends IBulletSpawner> bulletSpawners() {
        List<IBulletSpawner> spawners = new ArrayList<>();
        ServiceLoader.load(IBulletSpawner.class).forEach(spawners::add);

        if (layer != ModuleLayer.boot()) {
            ServiceLoader.load(layer, IBulletSpawner.class).forEach(s -> {
                if (spawners.stream().noneMatch(existing -> existing.getClass().equals(s.getClass()))) {
                    spawners.add(s);
                }
            });
        }
        return spawners;
    }

    @Bean
    public IInputServiceProvider inputServiceProvider() {
        return ServiceLoader.load(layer, IInputServiceProvider.class).findFirst()
                .orElseGet(() -> ServiceLoader.load(IInputServiceProvider.class).findFirst().orElse(null));
    }

    @Bean
    public IRendererProvider rendererProvider() {
        return ServiceLoader.load(layer, IRendererProvider.class).findFirst()
                .orElseGet(() -> ServiceLoader.load(IRendererProvider.class).findFirst().orElse(null));
    }

    @Bean
    public IAsteroidSpawnerProvider asteroidSpawnerProvider() {
        return ServiceLoader.load(layer, IAsteroidSpawnerProvider.class).findFirst()
                .orElseGet(() -> ServiceLoader.load(IAsteroidSpawnerProvider.class).findFirst().orElse(null));
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    @Bean
    public ScoringClient scoringClient(RestTemplate restTemplate) {
        return new ScoringClient(restTemplate);
    }
}
