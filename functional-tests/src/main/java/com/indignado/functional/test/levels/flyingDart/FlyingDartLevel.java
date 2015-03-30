package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.functional.test.levels.base.entities.DefaultCamera;
import com.indignado.functional.test.levels.base.entities.DefaultLight;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Wall;
import com.indignado.functional.test.levels.flyingDart.entities.Dart;
import com.indignado.functional.test.levels.flyingDart.entities.TriggerDart;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel extends LevelFactory {


    public FlyingDartLevel(LogicBricksEngine engine, LBBuilders builders, AssetManager assetManager) {
        super(engine, assetManager);
        addEntityFactory(new DefaultLight(builders, assetManager));
        addEntityFactory(new DefaultCamera(builders, assetManager));
        addEntityFactory(new Dart(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));
        addEntityFactory(new Wall(builders, assetManager));
        addEntityFactory(new TriggerDart(builders, assetManager));

    }


    @Override
    public void createLevel() {
        Entity camera = entitiesFactories.get(DefaultCamera.class).createEntity(0,9,0);
        engine.addEntity(camera);

        Entity light = entitiesFactories.get(DefaultLight.class).createEntity(5,10,0);
        engine.addEntity(light);

        Entity trigger = entitiesFactories.get(TriggerDart.class).createEntity(-14, 2, 0);
        engine.addEntity(trigger);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity wall = entitiesFactories.get(Wall.class).createEntity(10, 10, 0);
        engine.addEntity(wall);

    }

}
