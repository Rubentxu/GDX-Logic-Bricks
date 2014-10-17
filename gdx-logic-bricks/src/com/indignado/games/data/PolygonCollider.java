package com.indignado.games.data;

import com.badlogic.gdx.math.Vector2;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class PolygonCollider extends Collider {
    public int pathCount;	// The number of paths in the polygon.
    public Vector2[] points;	// Corner points that define the collider's shape in local space.

}
