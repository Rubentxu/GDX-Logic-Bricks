package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.Script;
import com.indignado.logicbricks.core.actuators.Actuator;
import com.indignado.logicbricks.core.controllers.ScriptController;
import com.indignado.logicbricks.core.sensors.AlwaysSensor;
import com.indignado.logicbricks.core.sensors.Sensor;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Rubentxu.
 */
public class ScriptControllerSystemTest {
    private PooledEngine engine;
    private String statePrueba;
    private String name;
    private Entity entity;
    private ScriptControllerSystem scriptControllerSystem;
    private boolean checkScript;
    private EntityBuilder entityBuilder;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.statePrueba = "StatePruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        scriptControllerSystem = new ScriptControllerSystem();
        engine.addSystem(scriptControllerSystem);
        engine.addSystem(new StateSystem(engine));

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePrueba));

        entity.add(stateComponent);
        engine.addEntity(entity);
        entityBuilder = new EntityBuilder(engine);

    }


    @Test
    public void ScriptControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        checkScript = false;

        ScriptController scriptController = new ScriptController();
        scriptController.script = new Script() {
            @Override
            public void execute(Array<Sensor> sensors, Array<Actuator> actuators) {

            }
        };

        entityBuilder.addController(scriptController, statePrueba)
                .connectToSensor(alwaysSensor);

        engine.update(1);
        assertTrue(checkScript);

    }

}