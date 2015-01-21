package com.indignado.functional.test.levels.buoyancy;

import com.badlogic.ashley.core.Entity;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.buoyancy.entities.Box;
import com.indignado.functional.test.levels.buoyancy.entities.Pool;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class BuoyancyLevel extends LevelFactory {


    public BuoyancyLevel(World world) {
        super(world);
        world.addEntityFactory(new Box(world));
        world.addEntityFactory(new Pool(world));
        world.addEntityFactory(new Ground(world));

    }


    @Override
    public void createLevel() {
        LogicBricksEngine engine = world.getEngine();
        world.getCamera().position.set(0, 7, 0);
        world.getCamera().viewportWidth = Settings.Width * 2;
        world.getCamera().viewportHeight = Settings.Height * 2;

        Entity pool = world.getEntityFactories().get(Pool.class).createEntity();
        world.positioningEntity(pool, -16, 4f, 0);
        engine.addEntity(pool);

        Entity pool2 = world.getEntityFactories().get(Pool.class).createEntity();
        pool2.getComponent(BuoyancyComponent.class).density = 1.5f;
        pool2.getComponent(BuoyancyComponent.class).offset = 7f;
        world.positioningEntity(pool2, 16, 4f, 0);
        engine.addEntity(pool2);

        Entity ground = world.getEntityFactories().get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = world.getEntityFactories().get(Box.class).createEntity();
        world.positioningEntity(box, 5, 12f, 0);
        engine.addEntity(box);

        Entity box2 = world.getEntityFactories().get(Box.class).createEntity();
        world.positioningEntity(box2, 12, 15f, 0);
        engine.addEntity(box2);

        Entity box3 = world.getEntityFactories().get(Box.class).createEntity();
        world.positioningEntity(box3, -15, 17f, 0);
        engine.addEntity(box3);

        Entity box4 = world.getEntityFactories().get(Box.class).createEntity();
        world.positioningEntity(box4, -21, 21f, 0);
        engine.addEntity(box4);

    }

}
