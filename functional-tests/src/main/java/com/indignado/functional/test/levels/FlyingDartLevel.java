package com.indignado.functional.test.levels;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.FlyingDartCollisionRule;
import com.indignado.functional.test.entities.Dart;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.LevelCreator;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.systems.RenderingSystem;
import com.indignado.logicbricks.systems.sensors.CollisionSensorSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public class FlyingDartLevel extends BaseLevel {

    @Override
    public void createLevel(World world) {
        world.getCamera().position.set(0,7,0);
        world.getCamera().viewportWidth = Settings.Width;
        world.getCamera().viewportHeight = Settings.Height;
        Dart dart = world.getEngine().createEntity(Dart.class);
        dart.init(-13,6f,0);
        Dart dart2 = world.getEngine().createEntity(Dart.class);
        dart2.init(-12,3,0);

        Body ground = world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(50, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();

        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "wall"));

        wall(world.getBodyBuilder(),15, 7.5F, 1, 20);

    }

}
