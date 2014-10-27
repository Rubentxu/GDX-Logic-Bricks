package com.indignado.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.IntMap;
import com.indignado.games.data.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 16/10/14.
 *
 * @author Rubentxu
 */
public class AnimationComponent extends Component {
    public Map<String, Animation> animations = new HashMap<String, Animation>();

}
