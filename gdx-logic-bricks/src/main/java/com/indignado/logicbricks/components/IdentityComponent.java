package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;

/**
 * @author Rubentxu.
 */
public class IdentityComponent extends Component {
    public long uuid;
    public String tag = "Dummy";
    public short category = 0x0001;
    public short collisionMask = -1;
    public short group = 0;

}
