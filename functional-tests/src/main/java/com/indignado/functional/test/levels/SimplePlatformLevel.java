package com.indignado.functional.test.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.functional.test.entities.Dart;
import com.indignado.functional.test.entities.PlayerPlatform;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.LevelCreator;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;

/**
 * @author Rubentxu.
 */
public class SimplePlatformLevel extends BaseLevel {

    private World world;
    private Engine engine;

    @Override
    public void createLevel(World world) {
        world.getCamera().position.set(0,7,0);
        world.getCamera().viewportWidth = Settings.Width;
        world.getCamera().viewportHeight = Settings.Height;
        PlayerPlatform player = world.getEngine().createEntity(PlayerPlatform.class);
        player.init(-0,3f,0);

        Body ground = world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(50, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(0, 0)
                .mass(1)
                .build();


        world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(1, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(3, 5)
                .mass(1)
                .build();


        world.getBodyBuilder().fixture(world.getBodyBuilder().fixtureDefBuilder()
                .boxShape(1, 1))
                .type(BodyDef.BodyType.StaticBody)
                .position(9, 7)
                .mass(1)
                .build();


        wall(world.getBodyBuilder(),15, 7.5F, 1, 20);

    }



}
