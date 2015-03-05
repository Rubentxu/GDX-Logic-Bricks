package com.indignado.functional.test.levels.simpleplatform;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.indignado.functional.test.levels.base.entities.Crate;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Pulley;
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

        addEntityFactory(new PlayerPlatform(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));
        addEntityFactory(new Crate(builders, assetManager));
        addEntityFactory(new Pulley(builders, assetManager));
    }


    @Override
    public void createLevel() {
      /*  game.getCamera().position.set(0, 7, 0);
        game.getCamera().viewportWidth = Settings.WIDTH;
        game.getCamera().viewportHeight = Settings.HEIGHT;*/

        Entity player = entitiesFactories.get(PlayerPlatform.class).createEntity();
        engine.addEntity(player);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = entitiesFactories.get(Crate.class).createEntity();
        positioningEntity(box, -3, 5f, 0);
        engine.addEntity(box);

        Entity box2 = entitiesFactories.get(Crate.class).createEntity();
        positioningEntity(box2, 9, 7f, 0);
        engine.addEntity(box2);


        Entity pulley = entitiesFactories.get(Pulley.class).createEntity();
        positioningEntity(pulley, 5, 11f, 0);
        engine.addEntity(pulley);

    }

}
