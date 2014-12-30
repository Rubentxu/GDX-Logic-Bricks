package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.Logger;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.actuators.ActuatorComponent;
import com.indignado.logicbricks.core.CategoryBitsManager;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Settings;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.systems.actuators.ActuatorSystem;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystemTest {
    private World physic;
    private LogicBricksEngine engine;
    private CollisionSensorSystem collisionSensorSystem;
    private BodyBuilder bodyBuilder;
    private Entity player;
    private Entity ground;
    private Body bodyPlayer;
    private Body bodyGround;
    private RigidBodiesComponents rigidByPlayer;
    private RigidBodiesComponents rigidByGround;
    private EntityBuilder entityBuilder;
    private CategoryBitsManager categoryBitsManager;
    CollisionSensor collisionSensorPlayer;
    IdentityComponent identityPlayer;
    IdentityComponent identityGround;


    private class ActuatorTest extends Actuator {
    }

    private class ActuatorTestComponent extends ActuatorComponent<ActuatorTest> {
    }

    private class ActuatorTestSystem extends ActuatorSystem<ActuatorTest, ActuatorTestComponent> {

        public ActuatorTestSystem() {
            super(ActuatorTestComponent.class);
        }


        @Override
        public void processActuator(ActuatorTest actuator, float deltaTime) {

        }

    }


    @Before
    public void setup() {
        GdxNativesLoader.load();
        Settings.debugLevel = Logger.DEBUG;
        physic = new World(new Vector2(0, -9.81f), true);
        engine = new LogicBricksEngine();
        entityBuilder = new EntityBuilder(engine);
        collisionSensorSystem = new CollisionSensorSystem();
        engine.addSystem(collisionSensorSystem);
        engine.addEntityListener(collisionSensorSystem);
        physic.setContactListener(collisionSensorSystem);
        bodyBuilder = new BodyBuilder(physic);
        this.categoryBitsManager = new CategoryBitsManager();
        createContext();
    }


    @After
    public void tearDown() {
        player = null;
        ground = null;
        bodyPlayer = null;
        bodyGround = null;
        rigidByPlayer = null;
        rigidByGround = null;
        collisionSensorPlayer = null;
        identityPlayer = null;

    }


    private void createContext() {
        // Create Player Entity
        entityBuilder.initialize();
        identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";
        identityPlayer.category = categoryBitsManager.getCategoryBits(identityPlayer.tag);


        bodyPlayer = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(1)
                        .restitution(0f))
                .position(40, 23)
                .mass(1f)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        rigidByPlayer = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPlayer.rigidBodies.add(bodyPlayer);

        collisionSensorPlayer = BricksUtils.getBuilder(CollisionSensorBuilder.class)
                .setTargetName("Ground")
                .setName("collisionSensorPlayer")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();


        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(collisionSensorPlayer)
                .connectToActuator(actuatorTest)
                .getEntity();

        // Create Ground Entity
        entityBuilder.initialize();
        identityGround = entityBuilder.getComponent(IdentityComponent.class);
        identityGround.tag = "Ground";
        identityGround.category = categoryBitsManager.getCategoryBits(identityGround.tag);


        bodyGround = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(5, 1)
                        .restitution(0f))
                .position(40, 20)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        rigidByGround = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByGround.rigidBodies.add(bodyGround);

        ground = entityBuilder.getEntity();


        engine.addEntity(ground);
    }


    @Test
    public void bodyCollidesBodyTest() {
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        engine.update(1);

        assertTrue(collisionSensorPlayer.contact.isTouching());
        assertTrue(collisionSensorPlayer.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, collisionSensorPlayer.pulseState);

    }


    @Test
    public void filterTest() {
        identityPlayer.collisionMask = (short) ~identityGround.category;
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        engine.update(1);

        assertNull(collisionSensorPlayer.contact);
        assertFalse(collisionSensorPlayer.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, collisionSensorPlayer.pulseState);

    }


    @Test
    public void endBodyCollisionTest() {
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        engine.update(1);

        assertTrue(collisionSensorPlayer.contact.isTouching());
        assertTrue(collisionSensorPlayer.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, collisionSensorPlayer.pulseState);

        bodyPlayer.applyForce(0, 60, bodyPlayer.getWorldCenter().x, bodyPlayer.getWorldCenter().y, true);

        physic.step(1f, 8, 3);
        engine.update(1);
        physic.step(1f, 8, 3);
        engine.update(1);
        System.out.println("Body position3: " + bodyPlayer.getPosition());

        System.out.println("Body position4: " + bodyPlayer.getPosition());
        assertFalse(collisionSensorPlayer.contact.isTouching());
        assertFalse(collisionSensorPlayer.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, collisionSensorPlayer.pulseState);

    }


}
