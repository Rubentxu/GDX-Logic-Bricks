package com.indignado.logicbricks.data;

import com.badlogic.ashley.core.Component;

/**
 * @author Rubentxu.
 */
public class PropertyMapper {
    private final PropertyType propertyType;


    private PropertyMapper(String propertyName) {
        propertyType = PropertyType.getFor(propertyName);

    }


    public static PropertyMapper getFor(String propertyName) {
        return new PropertyMapper(propertyName);

    }


    public T get(Component component){
        return entity.getComponent(componentType);
    }


    public boolean has(Entity entity) {
        return entity.hasComponent(componentType);
    }

}
