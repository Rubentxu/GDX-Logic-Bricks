package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.CategoryBitsManager;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.CollisionSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.EngineUtils;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.CollisionSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class CollisionSensorSystemTest extends BaseSensorSystemTest<CollisionSensor, CollisionSensorSystem> {
    CollisionSensor sensor;
    IdentityComponent identityPlayer;
    IdentityComponent identityGround;
    private World physic;
    private Entity ground;
    private Body bodyPlayer;
    private Body bodyGround;
    private RigidBodiesComponents rigidByPlayer;
    private RigidBodiesComponents rigidByGround;
    private CategoryBitsManager categoryBitsManager;
    private BodyBuilder bodyBuilder;


    public CollisionSensorSystemTest() {
        super();
        sensorSystem = new CollisionSensorSystem();
        engine.addSystem(sensorSystem);
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, -9.81f), true);
        physic.setContactListener(sensorSystem);
        bodyBuilder = new BodyBuilder(physic);


    }


    @Override
    public void tearDown() {
        player = null;
        ground = null;
        bodyPlayer = null;
        bodyGround = null;
        rigidByPlayer = null;
        rigidByGround = null;
        sensor = null;
        identityPlayer = null;

    }

    @Override
    protected void createContext() {
        // Create Player Entity
        this.categoryBitsManager = new CategoryBitsManager();
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

        sensor = EngineUtils.getBuilder(CollisionSensorBuilder.class)
                .setTargetName("Ground")
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = EngineUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();


        player = entityBuilder
                .addController(controllerGround, "Default")
                .connectToSensor(sensor)
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
        identityPlayer.collisionMask = (short) identityGround.category;
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        engine.update(1);

        assertTrue(sensor.contactList.first().isTouching());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


    @Test
    public void filterTest() {
        identityPlayer.collisionMask = (short) ~identityGround.category;
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        engine.update(1);

        assertEquals(0, sensor.contactList.size);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void endBodyCollisionTest() {
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        engine.update(1);

        assertTrue(sensor.contactList.first().isTouching());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.applyForce(0, 60, bodyPlayer.getWorldCenter().x, bodyPlayer.getWorldCenter().y, true);

        physic.step(1f, 8, 3);
        engine.update(1);
        physic.step(1f, 8, 3);
        engine.update(1);
        System.out.println("Body position3: " + bodyPlayer.getPosition());

        System.out.println("Body position4: " + bodyPlayer.getPosition());
        assertEquals(0, sensor.contactList.size);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


}
