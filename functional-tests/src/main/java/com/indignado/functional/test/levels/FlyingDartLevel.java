package com.indignado.functional.test.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.entities.Dart;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel extends BaseLevel {


    public FlyingDartLevel() {
        addEntityFactory(new Dart());

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

        Body ground = world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(50, 0.6f))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();

        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "wall"));

        wall(world.getBodyBuilder(), 15, 7.5F, 1, 20);

    }

}
