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
        if (Gdx.app == null) Settings.TESTING = true;
        if (Settings.DEBUG_ENTITY != null) {
            if (Settings.DEBUG_TAGS.size == 0 && tag.contains(Settings.DEBUG_ENTITY)) return true;
            for (String debugTag : Settings.DEBUG_TAGS) {
                if (tag.contains(debugTag) && tag.contains(Settings.DEBUG_ENTITY)) return true;
            }
        } else {
            for(String t : Settings.DEBUG_TAGS) {
                if(tag.contains(t))  return true;
            }

        }
        return false;

    }


    public static void debug(String tag, String message, Object... args) {
        if (isActive(tag) && !Settings.TESTING) Gdx.app.debug(tag, String.format(message, args));
        else if (isActive(tag) && Settings.TESTING) System.out.println(tag + "::" + String.format(message, args));

    }


    public static void debug(String tag, String message, Exception exception, Object... args) {
        if (isActive(tag) && !Settings.TESTING) Gdx.app.debug(tag, String.format(message, args), exception);
        else if (isActive(tag) && Settings.TESTING) System.out.println(tag + "::" + String.format(message, args));

    }


    public static void info(String tag, String message, Object... args) {
        if (isActive(tag) && !Settings.TESTING) Gdx.app.log(tag, String.format(message, args));
        else if (isActive(tag) && Settings.TESTING) System.out.println(tag + "::" + String.format(message, args));

    }


    public static void info(String tag, String message, Exception exception, Object... args) {
        if (isActive(tag) && !Settings.TESTING) Gdx.app.log(tag, String.format(message, args), exception);
        else if (isActive(tag) && Settings.TESTING) System.out.println(tag + "::" + String.format(message, args));

    }


    public static void error(String tag, String message, Object... args) {
        if (isActive(tag) && !Settings.TESTING) Gdx.app.error(tag, String.format(message, args));
        else if (isActive(tag) && Settings.TESTING) System.out.println(tag + "::" + String.format(message, args));

    }


    public static void error(String tag, String message, Throwable exception, Object... args) {
        if (isActive(tag) && !Settings.TESTING) Gdx.app.error(tag, String.format(message, args), exception);
        else if (isActive(tag) && Settings.TESTING) System.out.println(tag + "::" + String.format(message, args));

    }


    public static String tagEntity(String tag, Entity entity) {
        return String.format("%s::%s::%d:", tag, entity.getComponent(IdentityComponent.class).tag,
                entity.getComponent(IdentityComponent.class).uuid);

    }

}
