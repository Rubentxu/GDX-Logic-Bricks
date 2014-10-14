package com.indignado.games.components;

import com.badlogic.ashley.core.Component;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class ColliderComponent extends Component {

    public BoundsComponent boundsComponent;	    // The world space bounding volume of the collider.
    public boolean  enabled;	// Enabled Colliders will collide with other colliders, disabled Colliders won't.
    public boolean isTrigger;	// Is the collider a trigger?

}
