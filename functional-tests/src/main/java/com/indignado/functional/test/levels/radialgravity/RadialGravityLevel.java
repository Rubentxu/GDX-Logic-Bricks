package com.indignado.functional.test.levels.radialgravity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.indignado.functional.test.levels.base.entities.DefaultCamera;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.radialgravity.entities.Cohete;
import com.indignado.functional.test.levels.radialgravity.entities.Planet;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class RadialGravityLevel extends LevelFactory {


    public RadialGravityLevel(LogicBricksEngine engine, LBBuilders builders, AssetManager assetManager) {
        super(engine, assetManager);
        addEntityFactory(new DefaultCamera(builders, assetManager));
        addEntityFactory(new Cohete(builders, assetManager));
        addEntityFactory(new Planet(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));

    }


    @Override
    public void createLevel() {
        Entity camera = entitiesFactories.get(DefaultCamera.class).createEntity();
        positioningCamera(camera, 8, 7);
        engine.addEntity(camera);

        Entity planet = entitiesFactories.get(Planet.class).createEntity(new Vector3(0,10,0));

        engine.addEntity(planet);

        Entity ground = entitiesFactories.get(Ground.class).createEntity(new Vector3(0,-22,0));
        engine.addEntity(ground);

        Entity box = entitiesFactories.get(Cohete.class).createEntity(new Vector3(12f,36f,0));
        engine.addEntity(box);

        Entity box2 = entitiesFactories.get(Cohete.class).createEntity(new Vector3(-13,33,0));
        engine.addEntity(box2);

    }

}
