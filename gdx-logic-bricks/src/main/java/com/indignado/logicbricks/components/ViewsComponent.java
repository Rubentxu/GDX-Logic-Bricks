package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.indignado.logicbricks.components.data.View;

/**
 * @author Rubentxu.
 */
public class ViewsComponent<V extends View> extends Component implements Poolable {
    public Array<V> views = new Array<V>();


    @Override
    public void reset() {
        for (V view : views) {
            view.reset();
        }
        views.clear();

    }

}
