package com.indignado.functional.test.levels.buoyancy;

import com.badlogic.ashley.core.Entity;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.buoyancy.entities.Box;
import com.indignado.functional.test.levels.buoyancy.entities.Pool;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.Game;

/**
 * @author Rubentxu.
 */
public class BuoyancyLevel extends LevelFactory {


    public BuoyancyLevel(Game game) {
        super(game);
        game.addEntityFactory(new Box(game));
        game.addEntityFactory(new Pool(game));
        game.addEntityFactory(new Ground(game));

    }


    @Override
    public void createLevel() {
        LogicBricksEngine engine = game.getEngine();
        game.getCamera().position.set(0, 7, 0);
        game.getCamera().viewportWidth = Settings.Width * 2;
        game.getCamera().viewportHeight = Settings.Height * 2;

        Entity pool = game.getEntityFactories().get(Pool.class).createEntity();
        game.positioningEntity(pool, -16, 4f, 0);
        engine.addEntity(pool);

        Entity pool2 = game.getEntityFactories().get(Pool.class).createEntity();
        pool2.getComponent(BuoyancyComponent.class).density = 1.5f;
        pool2.getComponent(BuoyancyComponent.class).offset = 7f;
        game.positioningEntity(pool2, 16, 4f, 0);
        engine.addEntity(pool2);

        Entity ground = game.getEntityFactories().get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = game.getEntityFactories().get(Box.class).createEntity();
        game.positioningEntity(box, 5, 12f, 0);
        engine.addEntity(box);

        Entity box2 = game.getEntityFactories().get(Box.class).createEntity();
        game.positioningEntity(box2, 12, 15f, 0);
        engine.addEntity(box2);

        Entity box3 = game.getEntityFactories().get(Box.class).createEntity();
        game.positioningEntity(box3, -15, 17f, 0);
        engine.addEntity(box3);

        Entity box4 = game.getEntityFactories().get(Box.class).createEntity();
        game.positioningEntity(box4, -21, 21f, 0);
        engine.addEntity(box4);

    }

}
