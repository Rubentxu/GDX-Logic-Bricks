package com.indignado.functional.test.levels.buoyancy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.buoyancy.entities.Box;
import com.indignado.functional.test.levels.buoyancy.entities.Pool;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class BuoyancyLevel extends LevelFactory {


    public BuoyancyLevel(LogicBricksEngine engine, LBBuilders builders, AssetManager assetManager) {
        super(engine, assetManager);
        addEntityFactory(new Box(builders, assetManager));
        addEntityFactory(new Pool(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));

    }


    @Override
    public void createLevel() {
       
       /* game.getCamera().position.set(0, 7, 0);
        game.getCamera().viewportWidth = Settings.WIDTH * 2;
        game.getCamera().viewportHeight = Settings.HEIGHT * 2;
*/
        Entity pool = entitiesFactories.get(Pool.class).createEntity();
        positioningEntity(pool, -16, 4f, 0);
        engine.addEntity(pool);

        Entity pool2 = entitiesFactories.get(Pool.class).createEntity();
        pool2.getComponent(BuoyancyComponent.class).density = 1.5f;
        pool2.getComponent(BuoyancyComponent.class).offset = 7f;
        positioningEntity(pool2, 16, 4f, 0);
        engine.addEntity(pool2);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = entitiesFactories.get(Box.class).createEntity();
        positioningEntity(box, 5, 12f, 0);
        engine.addEntity(box);

        Entity box2 = entitiesFactories.get(Box.class).createEntity();
        positioningEntity(box2, 12, 15f, 0);
        engine.addEntity(box2);

        Entity box3 = entitiesFactories.get(Box.class).createEntity();
        positioningEntity(box3, -15, 17f, 0);
        engine.addEntity(box3);

        Entity box4 = entitiesFactories.get(Box.class).createEntity();
        positioningEntity(box4, -21, 21f, 0);
        engine.addEntity(box4);

    }

}
