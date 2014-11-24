package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Filter;

/**
 * @author Rubentxu.
 */
public class IdentityComponent extends Component {
    public String nameEntity;
    public long uuid;
    public Filter filter;

}
