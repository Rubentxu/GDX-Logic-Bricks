package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class AnimationComponent extends Component {
    public IntMap<Animation> animations = new IntMap<Animation>();

}
