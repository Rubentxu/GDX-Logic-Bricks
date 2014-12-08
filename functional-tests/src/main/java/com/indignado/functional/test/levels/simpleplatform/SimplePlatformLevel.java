package com.indignado.functional.test.levels.simpleplatform;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.functional.test.levels.base.entities.Crate;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.simpleplatform.entities.PlayerPlatform;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class SimplePlatformLevel extends LevelFactory {


    public SimplePlatformLevel(World world) {
        super(world);
        world.addEntityFactory(new PlayerPlatform(world));
        world.addEntityFactory(new Ground(world));
        world.addEntityFactory(new Crate(world));
    }


    @Override
    public void createLevel() {
        PooledEngine engine = world.getEngine();
        world.getCamera().position.set(0, 7, 0);
        world.getCamera().viewportWidth = Settings.Width;
        world.getCamera().viewportHeight = Settings.Height;

        Entity player = world.getEntityFactories().get(PlayerPlatform.class).createEntity();
        engine.addEntity(player);

        Entity ground = world.getEntityFactories().get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = world.getEntityFactories().get(Crate.class).createEntity();
        world.positioningEntity(box, -3, 5f, 0);
        engine.addEntity(box);

        Entity box2 = world.getEntityFactories().get(Crate.class).createEntity();
        world.positioningEntity(box2, 9, 7f, 0);
        engine.addEntity(box2);

    }

}
