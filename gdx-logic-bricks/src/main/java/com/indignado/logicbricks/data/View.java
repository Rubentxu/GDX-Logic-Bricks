package com.indignado.logicbricks.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.IntMap;

import java.util.Properties;

/**
 * @author Rubentxu.
 */
public class View extends Data{
    public String name;
    public Transform transform;
    public IntMap<Animation> animations;
    public TextureRegion textureRegion;
    public int layer = 0;


    public View() {
        properties.put("height",new Property<Float>("height",null));
        properties.put("width",new Property<Float>("width",null));
        properties.put("opacity",new Property<Integer>("opacity",null));
        properties.put("flipX",new Property<Boolean>("flipX",null));
        properties.put("flipY",new Property<Boolean>("flipY",null));
        properties.put("tint",new Property<Color>("tint",null));

    }

}
