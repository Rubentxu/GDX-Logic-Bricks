package com.indignado.functional.test.levels.flyingDart;

import com.badlogic.ashley.core.Entity;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Wall;
import com.indignado.functional.test.levels.flyingDart.entities.Dart;
import com.indignado.functional.test.levels.flyingDart.entities.TriggerDart;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel extends LevelFactory {


    public FlyingDartLevel(Game game) {
        super(game);
        game.addEntityFactory(new Dart(game));
        game.addEntityFactory(new Ground(game));
        game.addEntityFactory(new Wall(game));
        game.addEntityFactory(new TriggerDart(game));

    }


    @Override
    public void createLevel() {
        LogicBricksEngine engine = game.getEngine();
        game.getCamera().position.set(0, 9, 0);
        game.getCamera().viewportWidth = Settings.WIDTH;
        game.getCamera().viewportHeight = Settings.HEIGHT;
        Entity trigger = game.getEntityFactories().get(TriggerDart.class).createEntity();
        game.positioningEntity(trigger, -14, 2f, 0);
        engine.addEntity(trigger);

        Entity ground = game.getEntityFactories().get(Ground.class).createEntity();
        engine.addEntity(ground);
        Entity wall = game.getEntityFactories().get(Wall.class).createEntity();
        game.positioningEntity(wall, 14, 10, 0);
        engine.addEntity(wall);

    }

}
