package com.indignado.functional.test.levels.simpleplatform;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.functional.test.levels.base.entities.Crate;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Wall;
import com.indignado.functional.test.levels.simpleplatform.entities.PlayerPlatform;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class SimplePlatformLevel extends LevelFactory {


    public SimplePlatformLevel() {
        addEntityFactory(new PlayerPlatform());
        addEntityFactory(new Ground());
        addEntityFactory(new Crate());
    }


    @Override
    public void createLevel(World world) {
        PooledEngine engine = world.getEngine();
        world.getCamera().position.set(0, 7, 0);
        world.getCamera().viewportWidth = Settings.Width;
        world.getCamera().viewportHeight = Settings.Height;

        Entity player = entityFactories.get(PlayerPlatform.class).createEntity(world);
        engine.addEntity(player);

        Entity ground =  entityFactories.get(Ground.class).createEntity(world);
        engine.addEntity(ground);

        Entity box = entityFactories.get(Crate.class).createEntity(world);
        positioningEntity(box, -3, 5f, 0);
        engine.addEntity(box);

        Entity box2 = entityFactories.get(Crate.class).createEntity(world);
        positioningEntity(box2, 9, 7f, 0);
        engine.addEntity(box2);

    }

}
