package com.indignado.logicbricks.utils.logicbricks;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

/**
 * @author Rubentxu.
 */
public class EntityBuilder {
    private final Engine engine;
    private LogicBricksBuilder logicBricksBuilder;


    public EntityBuilder(Engine engine) {
        this.engine = engine;

    }


    public <E extends Entity> E provides(Class<E> clazz) {
        try {
            E entity = clazz.newInstance();
            engine.addEntity(entity);
            return entity;
        } catch (InstantiationException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance controller " + clazz);
            return null;
        } catch (IllegalAccessException e) {
            Gdx.app.log("LogicBricksBuilder", "Error instance controller " + clazz);
            return null;
        }

    }

}
