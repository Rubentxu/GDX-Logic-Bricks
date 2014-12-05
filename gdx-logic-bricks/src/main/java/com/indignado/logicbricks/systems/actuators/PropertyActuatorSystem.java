package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.actuators.PropertyActuatorComponent;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.core.actuators.PropertyActuator;

import java.util.Set;

/**
 * @author Rubentxu
 */
public class PropertyActuatorSystem extends ActuatorSystem<PropertyActuator, PropertyActuatorComponent> {
    private final ComponentMapper<BlackBoardComponent> blackBoardMapper;


    public PropertyActuatorSystem() {
        super(PropertyActuatorComponent.class, BlackBoardComponent.class);
        this.blackBoardMapper = ComponentMapper.getFor(BlackBoardComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Integer state = stateMapper.get(entity).getCurrentState();
        Set<PropertyActuator> actuators = actuatorMapper.get(entity).actuators.get(state);
        if (actuators != null) {
            for (PropertyActuator actuator : actuators) {
                processActuator(actuator, blackBoardMapper.get(entity));

            }
        }

    }


    public void processActuator(PropertyActuator actuator, BlackBoardComponent blackBoardComponent) {
        if (evaluateController(actuator)) {
            Property property = blackBoardComponent.getProperty(actuator.property);
            switch (actuator.mode) {
                case Assign:
                    if (property.value != actuator.value) {
                        property.value = actuator.value;
                        Gdx.app.log("PropertyActuatorSystem", "tag " + property.name + " value " + property.value);
                    }
                    break;
                case Add:
                    break;
                case Toggle:
                    break;
                case Copy:
                    break;
            }
        }

    }


    public void processActuator(PropertyActuator actuator, float deltaTime) {
    }

}
