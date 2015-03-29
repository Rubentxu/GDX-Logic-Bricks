package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.indignado.logicbricks.core.data.Transform;

/**
 * @author Rubentxu.
 */
public class TransformsComponent extends Component implements Pool.Poolable {
    public Array<Transform> transforms = new Array<Transform>();


    @Override
    public void reset() {
        transforms.clear();

    }

}
