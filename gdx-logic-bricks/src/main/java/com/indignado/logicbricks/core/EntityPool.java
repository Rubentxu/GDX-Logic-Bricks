package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.LogicEntity;
import com.badlogic.gdx.utils.ReflectionPool;

/**
 * @author Rubentxu.
 */
public class EntityPool<T extends LogicEntity> extends ReflectionPool<T> {
    private World world;

    public EntityPool(Class<T> clazz, World world, int initialSize, int maxSize) {
        super(clazz, initialSize, maxSize);
        this.world = world;

    }


    protected T newObject() {
        T instance = (T) super.newObject();
        instance.create(world);
        return instance;

    }

}
