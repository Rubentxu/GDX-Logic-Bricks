package com.indignado.functional.test.levels.simpleplatform;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.functional.test.levels.base.entities.Crate;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Pulley;
import com.indignado.functional.test.levels.simpleplatform.entities.PlayerPlatform;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.Settings;

/**
 * @author Rubentxu.
 */
public class SimplePlatformLevel extends LevelFactory {


    public SimplePlatformLevel(Game game) {
        super(game);
        game.addEntityFactory(new PlayerPlatform(game));
        game.addEntityFactory(new Ground(game));
        game.addEntityFactory(new Crate(game));
        game.addEntityFactory(new Pulley(game));
    }


    @Override
    public void createLevel() {
        PooledEngine engine = game.getEngine();
        game.getCamera().position.set(0, 7, 0);
        game.getCamera().viewportWidth = Settings.WIDTH;
        game.getCamera().viewportHeight = Settings.HEIGHT;

        Entity player = game.getEntityFactories().get(PlayerPlatform.class).createEntity();
        engine.addEntity(player);

        Entity ground = game.getEntityFactories().get(Ground.class).createEntity();
        engine.addEntity(ground);

        Entity box = game.getEntityFactories().get(Crate.class).createEntity();
        game.positioningEntity(box, -3, 5f, 0);
        engine.addEntity(box);

        Entity box2 = game.getEntityFactories().get(Crate.class).createEntity();
        game.positioningEntity(box2, 9, 7f, 0);
        engine.addEntity(box2);


        Entity pulley = game.getEntityFactories().get(Pulley.class).createEntity();
        game.positioningEntity(pulley, 5, 11f, 0);
        engine.addEntity(pulley);

    }

}
