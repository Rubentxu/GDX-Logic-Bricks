package com.indignado.logicbricks.data;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.Bag;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Bits;

/**
 * @author Rubentxu.
 */
public class BlackBoard extends Component {
    long uuid;
    private Bag<Property> properties;
    private Array<Property> propertiesArray;
    private ImmutableArray<Property> immutablePropertiesArray;
    private Bits propertyBits;


    public BlackBoard() {
        properties = new Bag<Property>();
        propertiesArray = new Array<Property>(false, 16);
        immutablePropertiesArray = new ImmutableArray<Property>(propertiesArray);
        propertyBits = new Bits();

    }


    public long getId() {
        return uuid;

    }


    public BlackBoard add(Property property) {
        String propertyName = property.name;

        for (int i = 0; i < propertiesArray.size; ++i) {
            if (propertiesArray.get(i).name == propertyName) {
                propertiesArray.removeIndex(i);
                break;
            }
        }

        int propertyTypeIndex = PropertyType.getIndexFor(propertyName);

        properties.set(propertyTypeIndex, property);
        propertiesArray.add(property);
        propertyBits.set(propertyTypeIndex);
        return this;

    }


    public void removeAll() {
        while (propertiesArray.size > 0) {
            remove(propertiesArray.get(0));
        }

    }


    public BlackBoard remove(Property property) {
        PropertyType propertyType = PropertyType.getFor(property.name);
        int propertyTypeIndex = propertyType.getIndex();
        Property removeProperty = properties.get(propertyTypeIndex);

        if (removeProperty != null) {
            properties.set(propertyTypeIndex, null);
            propertiesArray.removeValue(removeProperty, true);
            propertyBits.clear(propertyTypeIndex);

        }
        return this;

    }


    public ImmutableArray<Property> getProperties() {
        return immutablePropertiesArray;

    }


    public Property getProperty(PropertyType propertyType) {
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


    @Override
    public int hashCode() {
        return (int) (uuid ^ (uuid >>> 32));

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BlackBoard))
            return false;
        BlackBoard other = (BlackBoard) obj;
        return uuid == other.uuid;

    }

}
