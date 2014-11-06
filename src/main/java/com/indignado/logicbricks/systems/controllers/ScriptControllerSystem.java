package com.indignado.logicbricks.systems.controllers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.indignado.logicbricks.bricks.controllers.ConditionalController;
import com.indignado.logicbricks.bricks.controllers.Script;
import com.indignado.logicbricks.bricks.controllers.ScriptController;
import com.indignado.logicbricks.bricks.exceptions.LogicBricksException;
import com.indignado.logicbricks.bricks.sensors.Sensor;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.controllers.ConditionalControllerComponent;
import com.indignado.logicbricks.components.controllers.ScriptControllerComponent;

import java.util.Iterator;
import java.util.Set;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class ScriptControllerSystem extends IteratingSystem {
    private ComponentMapper<StateComponent> stateMapper;
    private ComponentMapper<ScriptControllerComponent> scriptControllerMapper;


    public ScriptControllerSystem() {
        super(Family.getFor(ScriptControllerComponent.class, StateComponent.class));
        scriptControllerMapper = ComponentMapper.getFor(ScriptControllerComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) throws LogicBricksException {
        Integer state = stateMapper.get(entity).get();
        Set<ScriptController> scriptControllers = scriptControllerMapper.get(entity).scriptControllers.get(state);
        if (scriptControllers != null) {
            for (ScriptController controller : scriptControllers) {
                Iterator<Script> it = controller.scripts.iterator();
                while (it.hasNext()) {
                    it.next().execute(controller.sensors);

                }
            }
        }

    }


}
