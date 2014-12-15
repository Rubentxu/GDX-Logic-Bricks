package com.indignado.logicbricks.components.data;

import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author Rubentxu.
 */
public final class PropertyType {
    private static ObjectMap<String, PropertyType> propertyTypes = new ObjectMap<String, PropertyType>();
    private static int typeIndex = 0;

    private final int index;


    private PropertyType() {
        this.index = typeIndex++;

    }


    public static PropertyType getFor(String nameProperty) {
        PropertyType type = propertyTypes.get(nameProperty);
        if (type == null) {
            type = new PropertyType();
            propertyTypes.put(nameProperty, type);

        }
        return type;

    }


    public static int getIndexFor(String nameProperty) {
        return getFor(nameProperty).getIndex();

    }


    public static Bits getBitsFor(String... namesProperties) {
        Bits bits = new Bits();

        int typesLength = namesProperties.length;
        for (int i = 0; i < typesLength; i++) {
            bits.set(PropertyType.getIndexFor(namesProperties[i]));
        }

        return bits;

    }


    public int getIndex() {
        return index;

    }


    @Override
    public int hashCode() {
        return index;

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PropertyType other = (PropertyType) obj;
        return index == other.index;
    }


}
