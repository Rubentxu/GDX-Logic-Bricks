package com.indignado.functional.test.levels.radialgravity;

import com.badlogic.ashley.core.Entity;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.radialgravity.entities.Box;
import com.indignado.functional.test.levels.radialgravity.entities.Planet;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class RadialGravityLevel extends LevelFactory {


    public RadialGravityLevel(World world) {
        super(world);
        world.addEntityFactory(new Box(world));
        world.addEntityFactory(new Planet(world));
        world.addEntityFactory(new Ground(world));

    }


    @Override
    public void createLevel() {
        LogicBricksEngine engine = world.getEngine();
        world.getCamera().position.set(0, 7, 0);
        world.getCamera().viewportWidth = Settings.Width * 3;
        world.getCamera().viewportHeight = Settings.Height * 3;

        Entity planet = world.getEntityFactories().get(Planet.class).createEntity();
        world.positioningEntity(planet, 0, 10f, 0);
        engine.addEntity(planet);

        Entity ground = world.getEntityFactories().get(Ground.class).createEntity();
        world.positioningEntity(ground, 0, -22f, 0);
        engine.addEntity(ground);

        Entity box = world.getEntityFactories().get(Box.class).createEntity();
        world.positioningEntity(box, 12f, 36f, 0);
        engine.addEntity(box);

        Entity box2 = world.getEntityFactories().get(Box.class).createEntity();
        world.positioningEntity(box2, -13, 33f, 0);
        engine.addEntity(box2);

    }

}
