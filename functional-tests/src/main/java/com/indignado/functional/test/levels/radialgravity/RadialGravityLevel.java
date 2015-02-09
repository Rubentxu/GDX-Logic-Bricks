package com.indignado.functional.test.levels.radialgravity;

import com.badlogic.ashley.core.Entity;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.radialgravity.entities.Box;
import com.indignado.functional.test.levels.radialgravity.entities.Planet;
import com.indignado.logicbricks.core.Game;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;

/**
 * @author Rubentxu.
 */
public class RadialGravityLevel extends LevelFactory {


    public RadialGravityLevel(Game game) {
        super(game);
        game.addEntityFactory(new Box(game));
        game.addEntityFactory(new Planet(game));
        game.addEntityFactory(new Ground(game));

    }


    @Override
    public void createLevel() {
        LogicBricksEngine engine = game.getEngine();
        game.getCamera().position.set(0, 7, 0);
        game.getCamera().viewportWidth = Settings.WIDTH * 3;
        game.getCamera().viewportHeight = Settings.HEIGHT * 3;

        Entity planet = game.getEntityFactories().get(Planet.class).createEntity();
        game.positioningEntity(planet, 0, 10f, 0);
        engine.addEntity(planet);

        Entity ground = game.getEntityFactories().get(Ground.class).createEntity();
        game.positioningEntity(ground, 0, -22f, 0);
        engine.addEntity(ground);

        Entity box = game.getEntityFactories().get(Box.class).createEntity();
        game.positioningEntity(box, 12f, 36f, 0);
        engine.addEntity(box);

        Entity box2 = game.getEntityFactories().get(Box.class).createEntity();
        game.positioningEntity(box2, -13, 33f, 0);
        engine.addEntity(box2);

    }

}
