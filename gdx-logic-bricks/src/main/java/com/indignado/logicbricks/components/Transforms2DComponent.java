package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.indignado.logicbricks.core.data.Transform2D;

/**
 * @author Rubentxu.
 */
public class Transforms2DComponent extends Component implements Pool.Poolable {
    public Array<Transform2D> transforms = new Array<Transform2D>();


    @Override
    public void reset() {
        transforms.clear();

    }

}
