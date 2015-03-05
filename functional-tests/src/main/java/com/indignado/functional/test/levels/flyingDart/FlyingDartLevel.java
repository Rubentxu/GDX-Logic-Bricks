package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
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
        addEntityFactory(new Dart(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));
        addEntityFactory(new Wall(builders, assetManager));
        addEntityFactory(new TriggerDart(builders, assetManager));

    }


    @Override
    public void createLevel() {
    /*
        game.getCamera().position.set(0, 9, 0);
        game.getCamera().viewportWidth = Settings.WIDTH;
        game.getCamera().viewportHeight = Settings.HEIGHT;*/
        Entity trigger = entitiesFactories.get(TriggerDart.class).createEntity();
        positioningEntity(trigger, -14, 2f, 0);
        engine.addEntity(trigger);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        engine.addEntity(ground);
        Entity wall = entitiesFactories.get(Wall.class).createEntity();
        positioningEntity(wall, 14, 10, 0);
        engine.addEntity(wall);

    }

}
