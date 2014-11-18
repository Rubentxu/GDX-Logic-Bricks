package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.indignado.logicbricks.data.Script;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.sensors.AlwaysSensor;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.logicbricks.LogicBricksBuilder;
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
    private LogicBricksBuilder logicBricksBuilder;


    @Before
    public void setup() {
        this.name = "BricksPruebas";
        this.statePrueba = "StatePruebas";
        this.entity = new Entity();
        engine = new PooledEngine();
        scriptControllerSystem = new ScriptControllerSystem();
        engine.addSystem(scriptControllerSystem);
        engine.addSystem(new StateSystem());

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePrueba));

        entity.add(stateComponent);
        engine.addEntity(entity);
        logicBricksBuilder = new LogicBricksBuilder(engine, entity);

    }


    @Test
    public void ScriptControllerTest() {
        AlwaysSensor alwaysSensor = new AlwaysSensor();
        checkScript = false;

        ScriptController scriptController = new ScriptController();
        scriptController.scripts.add(new Script() {
            @Override
            public void execute(Array<Sensor> sensors) {
                checkScript = true;

            }
        });

        logicBricksBuilder.addController(scriptController, statePrueba)
                .connectToSensor(alwaysSensor);

        engine.update(1);
        assertTrue(checkScript);

    }

}