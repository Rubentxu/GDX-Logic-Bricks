package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.sensors.base.ActuatorTest;
import com.indignado.logicbricks.utils.builders.EngineUtils;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.controllers.ScriptControllerBuilder;
import com.indignado.logicbricks.utils.builders.sensors.AlwaysSensorBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class ScriptControllerSystemTest {
    private LogicBricksEngine engine;
    private boolean checkScript;
    private EntityBuilder entityBuilder;
    private Entity player;


    public ScriptControllerSystemTest() {
        engine = new LogicBricksEngine();
        entityBuilder = new EntityBuilder(engine);
        ConditionalControllerSystem controllerSystem = new ConditionalControllerSystem();
        engine.addSystem(controllerSystem);

    }


    @Before
    public void setup() {
        checkScript = false;
        entityBuilder.initialize();
        IdentityComponent identityPlayer = entityBuilder.getComponent(IdentityComponent.class);
        identityPlayer.tag = "Player";

        AlwaysSensor sensor = EngineUtils.getBuilder(AlwaysSensorBuilder.class)
                .setName("SensorScript")
                .getBrick();

        Script script = new Script() {
            @Override
            public void execute(ScriptController controller, ObjectMap<String, Sensor> sensors, ObjectMap<String, Actuator> actuators) {
                checkScript = true;
            }
        };

        ScriptController controller = EngineUtils.getBuilder(ScriptControllerBuilder.class)
                .setScript(script)
                .setName("playerController")
                .getBrick();

        ActuatorTest actuatorTest = new ActuatorTest();

        player = entityBuilder
                .addController(controller, "Default")
                .connectToSensor(sensor)
                .connectToActuator(actuatorTest)
                .getEntity();

    }


    @Test
    public void ScriptControllerTest() {
        engine.addEntity(player);
        engine.update(1);
        assertTrue(checkScript);
    }

}