package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class TransformComponent extends Component {
    public final Vector2 position  = new Vector2();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public int layer = 0;
    public TransformComponent parent;

}