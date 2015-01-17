package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Wall;
import com.indignado.functional.test.levels.flyingDart.entities.Dart;
import com.indignado.functional.test.levels.flyingDart.entities.TriggerDart;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel extends LevelFactory {


    public FlyingDartLevel(World world) {
        super(world);
        world.addEntityFactory(new Dart(world));
        world.addEntityFactory(new Ground(world));
        world.addEntityFactory(new Wall(world));
        world.addEntityFactory(new TriggerDart(world));

    }


    @Override
    public void createLevel() {
        LogicBricksEngine engine = world.getEngine();
        world.getCamera().position.set(0, 9, 0);
        world.getCamera().viewportWidth = Settings.Width;
        world.getCamera().viewportHeight = Settings.Height;
        Entity trigger = world.getEntityFactories().get(TriggerDart.class).createEntity();
        world.positioningEntity(trigger, -14, 2f, 0);
        engine.addEntity(trigger);

        Entity ground = world.getEntityFactories().get(Ground.class).createEntity();
        engine.addEntity(ground);
        Entity wall = world.getEntityFactories().get(Wall.class).createEntity();
        world.positioningEntity(wall, 14, 10, 0);
        engine.addEntity(wall);

    }

}
