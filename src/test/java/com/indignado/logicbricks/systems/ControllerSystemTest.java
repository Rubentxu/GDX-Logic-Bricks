package com.indignado.logicbricks.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.indignado.logicbricks.bricks.actuators.MessageActuator;
import com.indignado.logicbricks.bricks.controllers.AndController;
import com.indignado.logicbricks.bricks.controllers.OrController;
import com.indignado.logicbricks.bricks.controllers.Script;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.LogicBricksComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.actuators.MessageActuatorSystem;
import com.indignado.logicbricks.utils.box2d.BodyBuilder;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksComponentBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class ControllerSystemTest {
    private PooledEngine engine;
    private String state;
    private String name;
    private Entity entity;
    private ControllerSystem controllerSystem;
    private boolean checkScript;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.state = "StatePruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        controllerSystem = new ControllerSystem();
        engine.addSystem(controllerSystem);
        engine.addSystem(new StateSystem());

        StateComponent stateComponent =  new StateComponent();
        stateComponent.set(state);

        entity.add(stateComponent);
        engine.addEntity(entity);

    }


    @Test
    public void andControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor3 = new AlwaysSensor(new Entity());
        AndController andController = new AndController();
        andController.sensors.add(alwaysSensor);
        andController.sensors.add(alwaysSensor2);
        andController.sensors.add(alwaysSensor3);

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addController(andController)
                .build();

        entity.add(lbc);
        engine.update(1);

        assertTrue(andController.pulseSignal);

    }


    @Test
    public void andControllerFalseTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor3 = new AlwaysSensor(new Entity());
        alwaysSensor.tap = true;
        alwaysSensor.initialized = true;
        AndController andController = new AndController();
        andController.sensors.add(alwaysSensor);
        andController.sensors.add(alwaysSensor2);
        andController.sensors.add(alwaysSensor3);

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addController(andController)
                .build();

        entity.add(lbc);
        engine.update(1);

        assertFalse(andController.pulseSignal);

    }


    @Test(expected = LogicBricksException.class)
    public void andControllerExceptionTest() {
        AndController andController = new AndController();

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addController(andController)
                .build();

        entity.add(lbc);
        engine.update(1);

    }


    @Test
    public void orControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        alwaysSensor.tap = true;
        alwaysSensor.initialized = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        AlwaysSensor alwaysSensor3 = new AlwaysSensor(new Entity());
        alwaysSensor3.tap = true;
        alwaysSensor3.initialized = true;
        OrController orController = new OrController();
        orController.sensors.add(alwaysSensor);
        orController.sensors.add(alwaysSensor2);
        orController.sensors.add(alwaysSensor3);

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addSensor(alwaysSensor2)
                .addSensor(alwaysSensor3)
                .addController(orController)
                .build();

        entity.add(lbc);
        engine.update(1);

        assertTrue(orController.pulseSignal);

    }

    @Test
    public void orControllerFalseTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        alwaysSensor.tap = true;
        alwaysSensor.initialized = true;
        AlwaysSensor alwaysSensor2 = new AlwaysSensor(new Entity());
        alwaysSensor2.tap = true;
        alwaysSensor2.initialized = true;
        OrController orController = new OrController();
        orController.sensors.add(alwaysSensor);
        orController.sensors.add(alwaysSensor2);

        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addController(orController)
                .build();

        entity.add(lbc);
        engine.update(1);

        assertFalse(orController.pulseSignal);

    }


    @Test
    public void ScriptControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor(new Entity());
        checkScript = false;

        ScriptController scriptController = new ScriptController();
        scriptController.scripts.add(new Script() {
            @Override
            public void execute(Array<Sensor> sensors) {
                checkScript = true;

            }
        });
        scriptController.sensors.add(alwaysSensor);


        LogicBricksComponent lbc =  new LogicBricksComponentBuilder()
                .createLogicBricks(name, state)
                .addSensor(alwaysSensor)
                .addController(scriptController)
                .build();

        entity.add(lbc);
        engine.update(1);

        assertTrue(checkScript);

    }

}