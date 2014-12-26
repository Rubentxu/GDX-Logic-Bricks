package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.Bag;
import com.badlogic.gdx.utils.Bits;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.components.data.Property;
import com.indignado.logicbricks.components.data.PropertyType;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class BlackBoardComponent extends Component implements Poolable {
    private String tag = this.getClass().getSimpleName();
    private Bag<Property> properties;
    private Bits propertyBits;


    public <V> BlackBoardComponent() {
        properties = new Bag<Property>();
        propertyBits = new Bits();

    }


    public BlackBoardComponent addProperty(Property property) {
        String propertyName = property.name;
        int propertyTypeIndex = PropertyType.getIndexFor(propertyName);
        properties.set(propertyTypeIndex, property);
        propertyBits.set(propertyTypeIndex);
        return this;

    }


    public BlackBoardComponent removeProperty(Property property) {
        Log.debug(tag, "Remove Property %s value %s", property.name);
        PropertyType propertyType = PropertyType.getFor(property.name);
        int propertyTypeIndex = propertyType.getIndex();
        Property removeProperty = properties.get(propertyTypeIndex);

        if (removeProperty != null) {
            properties.set(propertyTypeIndex, null);
            propertyBits.clear(propertyTypeIndex);

        }
        return this;

    }


    public <V> Property<V> getProperty(String name) {
        PropertyType propertyType = PropertyType.getFor(name);
        int propertyTypeIndex = propertyType.getIndex();
        if (propertyTypeIndex < properties.getCapacity()) {
            return properties.get(propertyTypeIndex);
        } else {
            return null;
        }

    }


    public <V> void setValueProperty(String name, V value) {
        Log.debug(tag, "Change Property %s value %s", name, value.toString());
        PropertyType propertyType = PropertyType.getFor(name);
        int propertyTypeIndex = propertyType.getIndex();
        if (propertyTypeIndex < properties.getCapacity()) {
            Property<V> property = properties.get(propertyTypeIndex);
            property.value = value;
        }

    }


    public boolean hasProperty(String name) {
        PropertyType propertyType = PropertyType.getFor(name);
        return propertyBits.get(propertyType.getIndex());

    }


    Bits getPropertyBits() {
        return propertyBits;

    }


    @Override
    public void reset() {
        properties.clear();
        propertyBits.clear();

    }

}
