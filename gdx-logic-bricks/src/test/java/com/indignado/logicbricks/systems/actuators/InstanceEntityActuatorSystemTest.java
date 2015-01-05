package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.components.*;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.TextureView;
import com.indignado.logicbricks.core.*;
import com.indignado.logicbricks.core.actuators.InstanceEntityActuator;
import com.indignado.logicbricks.core.controllers.ConditionalController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.controllers.ConditionalControllerSystem;
import com.indignado.logicbricks.systems.controllers.ControllerSystem;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.BricksUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.actuators.InstanceEntityActuatorBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ConditionalControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Rubentxu.
 */
public class InstanceEntityActuatorSystemTest {
    private final World world;
    private Entity player;
    private InstanceEntityActuator actuator;
    private EntityBuilder entityBuilder;
    private LogicBricksEngine engine;

    public InstanceEntityActuatorSystemTest() {
        Settings.debug = false;
        GdxNativesLoader.load();
        world = new World(new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 10), false), null, null,
                new OrthographicCamera());
        entityBuilder = world.getEntityBuilder();
        engine = world.getEngine();

    }


    @Before
    public void setup() {
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        AlwaysSensor sensor = BricksUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("playerSensor")
                .getBrick();


        ConditionalController controller = BricksUtils.getBuilder(ConditionalControllerBuilder.class)
                .setOp(ConditionalController.Op.OP_AND)
                .setName("playerController")
                .getBrick();

        actuator =  BricksUtils.getBuilder(InstanceEntityActuatorBuilder.class)
                .setType(InstanceEntityActuator.Type.AddEntity)
                .setEntityFactory(new EntityTest(null))
                .setLocalPosition(new Vector2(2, 1))
                .setDuration(1.5f)
                .setName("ActuatorInstanceDart")
                .getBrick();

        player = entityBuilder
                .addController(controller, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuator)
                .getEntity();

    }


    @Test
    public void instanceTest() {
        engine.addEntity(player);

        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, actuator.pulseState);
        engine.update(1);
        assertEquals(LogicBrick.BrickMode.BM_OFF, actuator.pulseState);



    }



    public class EntityTest extends EntityFactory {


        public EntityTest(World world) {
            super(world);

        }


        @Override
        public void loadAssets() { }


        @Override
        public Entity createEntity() {
            EntityBuilder entityBuilder = new EntityBuilder(engine);
            entityBuilder.initialize();

            IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
            identity.tag = "EntityTest";

            StateComponent state = entityBuilder.getComponent(StateComponent.class);
            state.createState("Default");

            Entity entity = entityBuilder.getEntity();
            return entity;

        }

    }

}