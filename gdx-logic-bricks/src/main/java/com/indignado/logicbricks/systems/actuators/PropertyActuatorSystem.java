package com.indignado.logicbricks.systems.actuators;

import com.badlogic.ashley.core.ComponentMapper;
import com.indignado.logicbricks.components.BlackBoardComponent;
import com.indignado.logicbricks.components.actuators.PropertyActuatorComponent;
import com.indignado.logicbricks.core.actuators.PropertyActuator;
import com.indignado.logicbricks.core.data.Property;
import com.indignado.logicbricks.utils.Log;

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
    public void processActuator(PropertyActuator actuator, float deltatime) {
        BlackBoardComponent blackBoardComponent = blackBoardMapper.get(actuator.owner);
        Property property = blackBoardComponent.getProperty(actuator.property);
        switch (actuator.mode) {
            case Assign:
                Log.debug(tag, "property %s value %s", actuator.property, actuator.value);
                if (property.getValue() != actuator.value) {
                    property.setValue(actuator.value);
                    Log.debug(tag, "tag %s value %s", property.getName(), property.getValue());
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
