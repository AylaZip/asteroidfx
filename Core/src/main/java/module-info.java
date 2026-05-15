module Core {
    requires transitive javafx.graphics;

    requires Common;
    requires spring.context;
    requires spring.beans;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    uses dk.sdu.cbse.asteroidsfx.common.IRendererProvider;
    uses dk.sdu.cbse.asteroidsfx.common.IInputServiceProvider;
    uses dk.sdu.cbse.asteroidsfx.common.IGamePlugin;
    uses dk.sdu.cbse.asteroidsfx.common.IBulletSpawner;
    uses dk.sdu.cbse.asteroidsfx.common.IAsteroidSpawnerProvider;

    exports dk.sdu.cbse.asteroidsfx.main;
    opens dk.sdu.cbse.asteroidsfx.main to javafx.graphics, spring.core, spring.beans, spring.context, spring.test;
}
