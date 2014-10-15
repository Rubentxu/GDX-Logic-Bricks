package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created on 14/10/14.
 *
 * @author Rubentxu
 */
public class BoundsComponent extends Component{

    public Vector2 center;  //	The center of the bounding box.
    public Vector2 extents; //	The extents of the box. This is always half of the size.
    public Vector2 max;     //	The maximal point of the box. This is always equal to center+extents.
    public Vector2 min;     //	The minimal point of the box. This is always equal to center-extents.
    public Vector2 size;    //  The total size of the box. This is always twice as large as the extents.

}
