package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.components.data.View;

/**
 * @author Rubentxu.
 */
public class ViewsComponent extends Component implements Poolable {
    public Array<View> views = new Array<View>();


    @Override
    public void reset() {
        views.clear();

    }

}
