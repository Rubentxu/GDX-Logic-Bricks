package com.indignado.logicbricks.core;

import com.badlogic.ashley.core.LogicEntity;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Rubentxu.
 */
public class EntityPool<T extends LogicEntity> extends Pool<T> {
    private Class<T> clazz;
    private World world;

    public EntityPool(Class<T> clazz, World world,int initialSize,int maxSize) {
        super(initialSize, maxSize);
        this.clazz = clazz;
        this.world = world;

    }


    protected T newObject() {
        try {
            T instance = (T) clazz.newInstance();
            instance.create(world);
            return instance;

        } catch (Exception ex) {
            throw new GdxRuntimeException("Unable to create new instance: " + clazz.getSimpleName(), ex);

        }
    }

}
