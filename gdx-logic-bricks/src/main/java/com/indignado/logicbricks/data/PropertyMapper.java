package com.indignado.logicbricks.data;

import com.indignado.logicbricks.components.BlackBoardComponent;

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


    public Property get(BlackBoardComponent blackBoardComponent) {
        return blackBoardComponent.getProperty(propertyType);

    }


    public boolean has(BlackBoardComponent blackBoardComponent) {
        return blackBoardComponent.hasProperty(propertyType);

    }

}
