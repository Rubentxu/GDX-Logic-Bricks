package com.indignado.functional.test.levels.simpleplatform;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.functional.test.levels.base.entities.Crate;
import com.indignado.functional.test.levels.base.entities.DefaultLight;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Pulley;
import com.indignado.functional.test.levels.simpleplatform.entities.PlayerCamera;
import com.indignado.functional.test.levels.simpleplatform.entities.PlayerPlatform;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class SimplePlatformLevel extends LevelFactory {


    public SimplePlatformLevel(LogicBricksEngine engine, LBBuilders builders, AssetManager assetManager) {
        super(engine, assetManager);
        addEntityFactory(new DefaultLight(builders, assetManager));
        addEntityFactory(new PlayerCamera(builders, assetManager));
        addEntityFactory(new PlayerPlatform(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));
        addEntityFactory(new Crate(builders, assetManager));
        addEntityFactory(new Pulley(builders, assetManager));
    }


    @Override
    public void createLevel() {
        Entity light = entitiesFactories.get(DefaultLight.class).createEntity();
        positioningLight(light, 5, 5);
        engine.addEntity(light);

        Entity camera = entitiesFactories.get(PlayerCamera.class).createEntity();
        positioningCamera(camera, 0, 7);
        engine.addEntity(camera);

        Entity player = entitiesFactories.get(PlayerPlatform.class).createEntity();
        engine.addEntity(player);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = entitiesFactories.get(Crate.class).createEntity(-3,5,0);
        engine.addEntity(box);

        Entity box2 = entitiesFactories.get(Crate.class).createEntity(9,7,0);
        engine.addEntity(box2);

        Entity pulley = entitiesFactories.get(Pulley.class).createEntity(5,11,0);
        engine.addEntity(pulley);

    }

}
