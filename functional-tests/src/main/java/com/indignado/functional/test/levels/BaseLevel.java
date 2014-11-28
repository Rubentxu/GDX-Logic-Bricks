package com.indignado.functional.test.levels;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.LevelCreator;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public abstract class BaseLevel implements LevelCreator {

    protected Body wall(BodyBuilder builder,float x, float y, float width, float height) {
        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "wall"));
        return builder.fixture(new FixtureDefBuilder()
                .boxShape(width, height)
                .restitution(0.4f)
                .friction(0.5f))
                .position(x, y)
                .userData(context)
                .build();

    }


    private Body crate(BodyBuilder builder,float x, float y, float width, float height) {
        BlackBoardComponent context = new BlackBoardComponent();
        context.addProperty(new Property<String>("type", "crate"));
        return builder.fixture(new FixtureDefBuilder()
                .boxShape(width, height)
                .restitution(0.4f)
                .friction(0.5f))
                .userData(context)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

    }

}
