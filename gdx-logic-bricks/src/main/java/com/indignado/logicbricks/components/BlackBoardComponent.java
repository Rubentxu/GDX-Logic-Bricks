package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.Bag;
import com.badlogic.gdx.utils.Bits;
import com.indignado.logicbricks.data.Property;
import com.indignado.logicbricks.data.PropertyType;

/**
 * @author Rubentxu.
 */
public class BlackBoardComponent extends Component {
    private Bag<Property> properties;
    private Bits propertyBits;


    public BlackBoardComponent() {
        properties = new Bag<Property>();
        propertyBits = new Bits();

    }


    public <V extends Object> BlackBoardComponent add(Property<V> property) {
        String propertyName = property.name;
        int propertyTypeIndex = PropertyType.getIndexFor(propertyName);
        properties.set(propertyTypeIndex, property);
        propertyBits.set(propertyTypeIndex);
        return this;

    }


    public BlackBoardComponent remove(Property property) {
        PropertyType propertyType = PropertyType.getFor(property.name);
        int propertyTypeIndex = propertyType.getIndex();
        Property removeProperty = properties.get(propertyTypeIndex);

        if (removeProperty != null) {
            properties.set(propertyTypeIndex, null);
            propertyBits.clear(propertyTypeIndex);

        }
        return this;

    }


    public <V extends Object> Property<V> getProperty(PropertyType propertyType) {
        int propertyTypeIndex = propertyType.getIndex();
        if (propertyTypeIndex < properties.getCapacity()) {
            return properties.get(propertyTypeIndex);
        } else {
            return null;
        }

    }


    public boolean hasProperty(PropertyType propertyType) {
        return propertyBits.get(propertyType.getIndex());

    }


    Bits getPropertyBits() {
        return propertyBits;

    }


}
