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
import com.indignado.logicbricks.core.data.Axis2D;
import com.indignado.logicbricks.core.data.Property;
import com.indignado.logicbricks.core.sensors.RadarSensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.systems.sensors.base.BaseSensorSystemTest;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.RadarSensorBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rubentxu
 */
public class RadarSensorSystemTest extends BaseSensorSystemTest<RadarSensor, RadarSensorSystem> {
    RadarSensor sensor;
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


    public RadarSensorSystemTest() {
        super();
        sensorSystem = new RadarSensorSystem();
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
        identityPlayer.category = categoryBitsManager.getCategoryBits(identityPlayer.tag);


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

        sensor = BricksUtils.getBuilder(RadarSensorBuilder.class)
                .setAngle(90)
                .setAxis(Axis2D.Ynegative)
                .setDistance(4F)
                .setName("sensorPlayer")
                .getBrick();

        ConditionalController controllerGround = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
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
    public void radarAxisYnegativeTest() {
        identityPlayer.collisionMask = (short) identityGround.category;
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void radarAxisYnegativeTargetTagTest() {
        identityPlayer.collisionMask = (short) identityGround.category;
        sensor.targetTag = "Ground";
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void radarAxisYnegativeTargetTagInvalidTest() {
        identityPlayer.collisionMask = (short) identityGround.category;
        sensor.targetTag = "test";
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void radarAxisYnegativeTargetPropertyTest() {
        identityPlayer.collisionMask = (short) identityGround.category;
        sensor.propertyName = "GroundProperty";
        engine.addEntity(player);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "A) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, -1.5F));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "B) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 4));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "C) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertTrue(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        bodyPlayer.setLinearVelocity(new Vector2(0, 0));
        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "D) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_ON, sensor.pulseState);

        physic.step(1, 8, 3);
        engine.update(1);
        Log.debug("RadarSensorSystemTest", "E) Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }


    @Test
    public void radarAxisYnegativeTargetPropertyInvalidTest() {
        identityPlayer.collisionMask = (short) identityGround.category;
        sensor.propertyName = "test";
        engine.addEntity(player);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("RadarSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("RadarSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("RadarSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("RadarSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

        engine.update(1);
        physic.step(1, 8, 3);
        Log.debug("RadarSensorSystemTest", "Player position %s Ground position %s", bodyPlayer.getPosition(), bodyGround.getPosition());


        assertEquals(0, sensor.contactList.size);
        assertFalse(sensor.positive);
        assertEquals(LogicBrick.BrickMode.BM_OFF, sensor.pulseState);

    }
}
