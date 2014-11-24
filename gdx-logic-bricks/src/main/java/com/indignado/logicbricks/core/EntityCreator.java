package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Rubentxu.
 */
public interface EntityCreator {

    public Entity createEntity(World world, Vector2 position, float angle);

}
