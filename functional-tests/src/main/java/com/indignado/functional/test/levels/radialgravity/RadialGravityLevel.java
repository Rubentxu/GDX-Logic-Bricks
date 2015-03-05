package com.indignado.functional.test.levels.radialgravity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
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
        addEntityFactory(new Cohete(builders, assetManager));
        addEntityFactory(new Planet(builders, assetManager));
        addEntityFactory(new Ground(builders, assetManager));

    }


    @Override
    public void createLevel() {

      /*  game.getCamera().position.set(0, 7, 0);
        game.getCamera().viewportWidth = Settings.WIDTH * 3;
        game.getCamera().viewportHeight = Settings.HEIGHT * 3;
*/
        Entity planet = entitiesFactories.get(Planet.class).createEntity();
        positioningEntity(planet, 0, 10f, 0);
        engine.addEntity(planet);

        Entity ground = entitiesFactories.get(Ground.class).createEntity();
        positioningEntity(ground, 0, -22f, 0);
        engine.addEntity(ground);

        Entity box = entitiesFactories.get(Cohete.class).createEntity();
        positioningEntity(box, 12f, 36f, 0);
        engine.addEntity(box);

        Entity box2 = entitiesFactories.get(Cohete.class).createEntity();
        positioningEntity(box2, -13, 33f, 0);
        engine.addEntity(box2);

    }

}
