package com.indignado.logicbricks.data;

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


    public Property get(BlackBoard blackBoard) {
        return blackBoard.getProperty(propertyType);

    }


    public boolean has(BlackBoard blackBoard) {
        return blackBoard.hasProperty(propertyType);

    }

}
