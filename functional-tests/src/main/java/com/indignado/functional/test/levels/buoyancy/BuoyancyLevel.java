package com.indignado.functional.test.levels.buoyancy;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.functional.test.levels.base.entities.DefaultCamera;
import com.indignado.functional.test.levels.base.entities.DefaultLight;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.buoyancy.entities.Box;
import com.indignado.functional.test.levels.buoyancy.entities.Pool;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.config.Settings;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class BuoyancyLevel extends LevelFactory {


    public BuoyancyLevel(LogicBricksEngine engine, LBBuilders builders, AssetManager assetManager) {
        super(engine, assetManager);
        addEntityFactory(new DefaultLight(builders, assetManager));
        addEntityFactory(new DefaultCamera(builders, assetManager));
        addEntityFactory(new Box(builders, assetManager));
        addEntityFactory(new Pool(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));

    }


    @Override
    public void createLevel() {
        Settings.shadows2D = false;
        Entity camera = entitiesFactories.get(DefaultCamera.class).createEntity(0,7,0);
        engine.addEntity(camera);

        Entity light = entitiesFactories.get(DefaultLight.class).createEntity(5,10,0);
        engine.addEntity(light);

        Entity pool = entitiesFactories.get(Pool.class).createEntity(-8,3,0);
        engine.addEntity(pool);

        Entity pool2 = entitiesFactories.get(Pool.class).createEntity(7,3,0);
        pool2.getComponent(BuoyancyComponent.class).density = 1.5f;
        pool2.getComponent(BuoyancyComponent.class).offset = 5f;
        engine.addEntity(pool2);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = entitiesFactories.get(Box.class).createEntity(5,12,0);
        engine.addEntity(box);

        Entity box2 = entitiesFactories.get(Box.class).createEntity(12,15,0);
        engine.addEntity(box2);

        Entity box3 = entitiesFactories.get(Box.class).createEntity(-8,17,0);
        engine.addEntity(box3);

        Entity box4 = entitiesFactories.get(Box.class).createEntity(-3,21,0);
        engine.addEntity(box4);

    }

}
