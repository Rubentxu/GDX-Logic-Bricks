package com.indignado.logicbricks.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.core.Settings;

/**
 * @author Rubentxu.
 */
public class Log {

    public static boolean isActive(String tag) {
        if (Settings.debugEntity != null) {
            if (Settings.debugTags.size == 0 && tag.contains(Settings.debugEntity)) return true;
            for (String debugTag : Settings.debugTags) {
                if (tag.contains(debugTag) && tag.contains(Settings.debugEntity)) return true;
            }
        } else if (Settings.debugTags.contains(tag, false)) {
            return true;
        }
        return false;

    }


    public static void debug(String tag, String message, Object... args) {
        if (isActive(tag)) Gdx.app.debug(tag, String.format(message, args));

    }


    public static void debug(String tag, String message, Exception exception, Object... args) {
        if (isActive(tag)) Gdx.app.debug(tag, String.format(message, args), exception);

    }


    public static void info(String tag, String message, Object... args) {
        if (isActive(tag)) Gdx.app.log(tag, String.format(message, args));

    }


    public static void info(String tag, String message, Exception exception, Object... args) {
        if (isActive(tag)) Gdx.app.log(tag, String.format(message, args), exception);

    }


    public static void error(String tag, String message, Object... args) {
        if (isActive(tag)) Gdx.app.error(tag, String.format(message, args));

    }


    public static void error(String tag, String message, Throwable exception, Object... args) {
        if (isActive(tag)) Gdx.app.error(tag, String.format(message, args), exception);

    }


    public static String tagEntity(String tag, Entity entity) {
        return String.format("%s::%s::%d:",tag,entity.getComponent(IdentityComponent.class).tag,
                entity.getComponent(IdentityComponent.class).uuid);

    }

}
