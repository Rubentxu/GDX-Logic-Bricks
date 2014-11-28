package com.indignado.logicbricks.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;

/**
 * @author Rubentxu.
 */
public abstract class LevelFactory {
    protected ObjectMap<Class<? extends EntityFactory>, EntityFactory> entityFactories;
    protected static EntityBuilder entityBuilder;
    protected static BodyBuilder bodyBuilder;


    protected LevelFactory() {
        this.entityFactories = new ObjectMap<Class<? extends EntityFactory>, EntityFactory>();

    }


    public void loadAssets(AssetManager manager){
        for(EntityFactory factory: entityFactories.values()) {
            factory.loadAssets(manager);
        }
        manager.finishLoading();

    }


    public <T extends EntityFactory> LevelFactory addEntityFactory(T factory) {
        entityFactories.put(factory.getClass(),factory);
        return this;

    }


    public static void setEntityBuilder(EntityBuilder entityBuilder) {
        LevelFactory.entityBuilder = entityBuilder;

    }


    public static void setBodyBuilder(BodyBuilder bodyBuilder) {
        LevelFactory.bodyBuilder = bodyBuilder;

    }


    public abstract void createLevel(World world);


}
