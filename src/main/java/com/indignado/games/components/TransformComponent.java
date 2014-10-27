package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.indignado.games.data.Transform;

import java.util.Set;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class TransformComponent extends Component {
    public int layer = 0;
    public Set<Transform> transforms;

}