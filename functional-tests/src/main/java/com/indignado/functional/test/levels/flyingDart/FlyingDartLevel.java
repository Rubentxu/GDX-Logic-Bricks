package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Wall;
import com.indignado.functional.test.levels.flyingDart.entities.Dart;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel extends LevelFactory {


    public FlyingDartLevel() {
        addEntityFactory(new Dart());
        addEntityFactory(new Ground());
        addEntityFactory(new Wall());

    }


    @Override
    public void createLevel(World world) {
        PooledEngine engine = world.getEngine();
        world.getCamera().position.set(0, 9, 0);
        world.getCamera().viewportWidth = Settings.Width;
        world.getCamera().viewportHeight = Settings.Height;
        Entity dart = entityFactories.get(Dart.class).createEntity(world);
        positioningEntity(dart, -13, 6f, 0);
        engine.addEntity(dart);

        Entity dart2 = entityFactories.get(Dart.class).createEntity(world);
        positioningEntity(dart2,-6, 3, 0);
        engine.addEntity(dart2);

        Entity ground = entityFactories.get(Ground.class).createEntity(world);
        engine.addEntity(ground);
        Entity wall = entityFactories.get(Wall.class).createEntity(world);
        positioningEntity(wall, 14, 10, 0);
        engine.addEntity(wall);
    }

}
