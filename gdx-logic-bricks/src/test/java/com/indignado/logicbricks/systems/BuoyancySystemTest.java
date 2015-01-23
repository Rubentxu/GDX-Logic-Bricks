package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.components.BuoyancyComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rubentxu.
 */
public class BuoyancySystemTest {
    private final BodyBuilder bodyBuilder;
    private LogicBricksEngine engine;
    private BuoyancyComponent buoyancyComponent;
    private World physic;
    private Body bodyPlayer;
    private EntityBuilder entityBuilder;


    public BuoyancySystemTest() {
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, -9.8f), true);
        bodyBuilder = new BodyBuilder(physic);
        engine = new LogicBricksEngine();
        BuoyancySystem buoyancySystem = new BuoyancySystem();
        engine.addSystem(buoyancySystem);
        entityBuilder = new EntityBuilder(engine);
        physic.setContactListener(buoyancySystem);

    }


    @Before
    public void setup() {
        // Player
        entityBuilder.initialize();

        bodyPlayer = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(1, 1)
                        .restitution(0f)
                        .density(1))
                .position(0, 8)
                .mass(1f)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        RigidBodiesComponents rigidByPlayer = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPlayer.rigidBodies.add(bodyPlayer);

        engine.addEntity(entityBuilder.getEntity());

        // Pool
        entityBuilder.initialize();
        Body bodyPool = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(3, 3)
                        .restitution(0f)
                        .density(1)
                        .sensor())
                .position(0, 0)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        RigidBodiesComponents rigidByPool = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPool.rigidBodies.add(bodyPool);

        buoyancyComponent = entityBuilder.getComponent(BuoyancyComponent.class);

        Entity pool = entityBuilder.getEntity();
        engine.addEntity(pool);
        Log.debug("BuoyancySystemTest", "Pool Entity Component size %s", pool.getComponents().size());

    }


    @Test
    public void defaultTest() {
        buoyancyComponent.offset = 6;
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "A) Player position %s", bodyPlayer.getPosition());

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "B) Player position %s", bodyPlayer.getPosition());

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "C) Player position %s", bodyPlayer.getPosition());

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "D) Player position %s", bodyPlayer.getPosition());

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "E) Player position %s", bodyPlayer.getPosition());

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "F) Player position %s", bodyPlayer.getPosition());

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("BuoyancySystemTest", "G) Player position %s", bodyPlayer.getPosition());
        //assertEquals(deltaTime, stateComponent.time, 0.1);

    }

}
