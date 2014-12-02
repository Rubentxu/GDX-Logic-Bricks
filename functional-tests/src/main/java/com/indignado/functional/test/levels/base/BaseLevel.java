package com.indignado.functional.test.levels.base;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.levels.base.entities.Ground;
import com.indignado.functional.test.levels.base.entities.Wall;
import com.indignado.functional.test.levels.flyingDart.entities.Dart;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.LevelFactory;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public abstract class BaseLevel extends LevelFactory {

    protected Body wall(BodyBuilder builder, float x, float y, float width, float height) {
        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "wall"));
        return builder.fixture(new FixtureDefBuilder()
                .boxShape(width, height)
                .restitution(0.4f)
                .friction(0.5f))
                .position(x, y)
                .build();

    }


    private Body crate(BodyBuilder builder, float x, float y, float width, float height) {
        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "crate"));
        return builder.fixture(new FixtureDefBuilder()
                .boxShape(width, height)
                .restitution(0.4f)
                .friction(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .build();

    }

    /**
     * @author Rubentxu.
     */
    public static class FlyingDartLevel extends BaseLevel {


        public FlyingDartLevel() {
            addEntityFactory(new Dart());
            addEntityFactory(new Wall());
            addEntityFactory(new Ground());

        }


        @Override
        public void createLevel(World world) {
            PooledEngine engine = world.getEngine();
            world.getCamera().position.set(0, 9, 0);
            world.getCamera().viewportWidth = Settings.Width;
            world.getCamera().viewportHeight = Settings.Height;
            Entity dart = entityFactories.get(Dart.class).createEntity(world);
            positioningEntity(dart, -13, 6f, 0);
            engine.addEntity(dart);

            Entity dart2 = entityFactories.get(Dart.class).createEntity(world);
            positioningEntity(dart2,-6, 3, 0);
            engine.addEntity(dart2);

            Entity ground = entityFactories.get(Ground.class).createEntity(world);
            engine.addEntity(ground);
            Entity wall = entityFactories.get(Wall.class).createEntity(world);
            positioningEntity(wall,14,10,0);
            engine.addEntity(wall);

        }

    }
}
