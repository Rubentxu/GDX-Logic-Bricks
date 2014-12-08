package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.core.World;
import com.indignado.logicbricks.systems.StateSystem;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import org.junit.Before;
import org.junit.Test;

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
        engine.addSystem(new StateSystem(new World(null, null, null, null)));

        StateComponent stateComponent = new StateComponent();
        stateComponent.changeCurrentState(stateComponent.getState(statePrueba));

        entity.add(stateComponent);
        engine.addEntity(entity);
        entityBuilder = new EntityBuilder(engine);

    }


    @Test
    public void ScriptControllerTest() {
       /* AlwaysSensor alwaysSensor = new AlwaysSensor();
        checkScript = false;

        ScriptController scriptController = new ScriptController();
        scriptController.script = new Script() {

            @Override
            public void execute(ObjectMap<String, Sensor> sensors, ObjectMap<String, Actuator> actuators) {

            }
        };

        entityBuilder.addController(scriptController, statePrueba)
                .connectToSensor(alwaysSensor);

        engine.update(1);
        assertTrue(checkScript);
*/
    }

}