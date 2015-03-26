package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.indignado.logicbricks.core.data.Transform3D;

/**
 * @author Rubentxu.
 */
public class Transforms3DComponent extends Component implements Pool.Poolable {
    public Array<Transform3D> transforms = new Array<Transform3D>();


    @Override
    public void reset() {
        transforms.clear();

    }

}
