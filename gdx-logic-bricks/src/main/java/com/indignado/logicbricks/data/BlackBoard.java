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
    /** A Bits describing all the Properties in this Component. For quick matching. */
    private Bits propertyBits;


    public BlackBoard() {
        properties = new Bag<Property>();
        propertiesArray = new Array<Property>(false,16);
        immutablePropertiesArray = new ImmutableArray<Property>(propertiesArray);
        propertyBits = new Bits();

    }


    public long getId(){
        return uuid;

    }




}
