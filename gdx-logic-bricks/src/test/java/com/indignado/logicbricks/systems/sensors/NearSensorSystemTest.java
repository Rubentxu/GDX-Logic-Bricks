package com.indignado.logicbricks.systems.sensors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.core.CategoryBitsManager;
import com.indignado.logicbricks.core.LogicBrick;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.data.Property;
import com.indignado.logicbricks.core.sensors.NearSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EngineUtils;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.NearSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class NearSensorSystemTest extends BaseSensorSystemTest<NearSensor, NearSensorSystem> {
    NearSensor sensor;
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


    public NearSensorSystemTest() {
        super();
        sensorSystem = new NearSensorSystem();
        engine.addSystem(sensorSystem);
        GdxNativesLoader.load();
        physic = new World(new Vector2(0, 0f), true);
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

        bodyPlayer = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(1, 1)
                        .restitution(0f))
                .position(0, 6)
                .mass(1f)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        rigidByPlayer = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPlayer.rigidBodies.add(bodyPlayer);

        sensor = EngineUtils.getBuilder(NearSensorBuilder.class)
                .setDistance(4)
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

        BlackBoardComponent blackBoardGround = entityBuilder.getComponent(BlackBoardComponent.class);
        blackBoardGround.addProperty(new Property("GroundProperty", "Value"));

        bodyGround = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .boxShape(3, 1)
                        .restitution(0f))
                .position(0, 0)
                .mass(1f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        rigidByGround = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByGround.rigidBodies.add(bodyGround);

        ground = entityBuilder.getEntity();
        engine.addEntity(ground);

    }


    @Test
    public void defaultTest() {
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void targetTagTest() {
        sensor.targetTag = "Ground";
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void targetTagInvalidTest() {
        sensor.targetTag = "test";
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void targetPropertyTest() {
        sensor.targetPropertyName = "GroundProperty";
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void targetPropertyInvalidTest() {
        sensor.targetPropertyName = "test";
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("NearSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("NearSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("NearSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("NearSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("NearSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());

        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void defaultResetDistanceTest() {
        sensor.resetDistance = 6;
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 2));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


    @Test
    public void resetDistanceTargetTagTest() {
        sensor.targetTag = "Ground";
        sensor.resetDistance = 6;
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 2));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


    @Test
    public void resetDistanceTargetTagInvalidTest() {
        sensor.targetTag = "Test";
        sensor.resetDistance = 6;
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 2));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void resetDistanceTargetPropertyTest() {
        sensor.targetPropertyName = "GroundProperty";
        sensor.resetDistance = 6;
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 2));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

    }


    @Test
    public void resetDistanceTargetPropertyInvalidTest() {
        identityPlayer.collisionMask = (short) identityGround.category;
        sensor.targetPropertyName = "Test";
        sensor.resetDistance = 6;
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 2));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("NearSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


}
