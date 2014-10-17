package com.indignado.games.data;

import com.badlogic.gdx.math.Vector2;

/**
 * Created on 17/10/14.
 *
 * @author Rubentxu
 */
public class BoxCollider extends Collider {
    public Vector2 center;
    public Vector2 extents; //	The extents of the box. This is always half of the size.
    public Vector2 size;    //  The total size of the box. This is always twice as large as the extents.

}
