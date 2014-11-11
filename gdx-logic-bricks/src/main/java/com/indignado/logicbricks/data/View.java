package com.indignado.logicbricks.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.IntMap;

/**
 * @author Rubentxu.
 */
public class View extends Data {
    public String name;
    public Transform transform;
    public IntMap<Animation> animations;
    public TextureRegion textureRegion;
    public int layer = 0;
    public Property<Float> height = new Property<Float>("height", null);
    public Property<Float> width = new Property<Float>("width", null);
    public Property<Integer> opacity = new Property<Integer>("opacity", null);
    public Property<Boolean> flipX = new Property<Boolean>("flipX", null);
    public Property<Boolean> flipY = new Property<Boolean>("flipY", null);
    public Property<Color> tint = new Property<Color>("tint", null);


    public View(String name) {
        this.name = name;
        this.height.
    }
}
