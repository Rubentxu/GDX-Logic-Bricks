package com.indignado.logicbricks.utils.builders;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.indignado.logicbricks.core.LogicBricksEngine;
import com.indignado.logicbricks.utils.Log;
import com.indignado.logicbricks.utils.builders.joints.JointBuilder;

/**
 * @author Rubentxu.
 */
public class LBBuilders {
    private String tag = LBBuilders.class.getSimpleName();
    private ObjectMap<Class<? extends BrickBuilder>, BrickBuilder> bricksBuilders;
    private EntityBuilder entityBuilder;
    private BodyBuilder bodyBuilder;
    private JointBuilder jointBuilder;

    public LBBuilders(LogicBricksEngine engine, World physics) {
        bricksBuilders = new ObjectMap<>();
        entityBuilder = new EntityBuilder(engine);
        bodyBuilder = new BodyBuilder(physics);
        jointBuilder = new JointBuilder(physics);

    }


    public <B extends BrickBuilder> B getBrickBuilder(Class<B> clazzBuilder) {
        B builder = (B) bricksBuilders.get(clazzBuilder);
        if (builder == null) {
            synchronized (clazzBuilder) {
                try {
                    Constructor constructor = findConstructor(clazzBuilder);
                    builder = (B) constructor.newInstance((Object[]) null);
                    bricksBuilders.put(clazzBuilder, builder);
                } catch (Exception e) {
                    Log.debug(tag, "Error instance actuatorBuilder %s" + clazzBuilder.getSimpleName());
                }
            }
        }
        return builder;

    }


    public EntityBuilder getEntityBuilder() {
        return entityBuilder;

    }


    public BodyBuilder getBodyBuilder() {
        return bodyBuilder;

    }


    public JointBuilder getJointBuilder() {
        return jointBuilder;

    }


    private <B extends BrickBuilder> Constructor findConstructor(Class<B> type) {
        try {
            return ClassReflection.getConstructor(type, (Class[]) null);
        } catch (Exception ex1) {
            try {
                Constructor constructor = ClassReflection.getDeclaredConstructor(type, (Class[]) null);
                constructor.setAccessible(true);
                return constructor;
            } catch (ReflectionException ex2) {
                return null;
            }
        }

    }

}
