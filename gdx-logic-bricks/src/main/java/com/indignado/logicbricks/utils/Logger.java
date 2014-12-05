package com.indignado.logicbricks.utils;

import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.core.Settings;

/**
 * @author Rubentxu.
 */
public class Logger {
    private final String tag;
    private boolean active;

    public Logger(String tag) {
        this.tag = tag;
        if(Settings.debugTags.size > 0 && !Settings.debugTags.contains(tag,false)) active = false;
        else active = true;

    }


    public void debug(String message, Object... args) {
        if(active) Gdx.app.debug(tag, String.format(message, args));

    }


    public void debug(String message, Exception exception, Object... args) {
        if(active) Gdx.app.debug(tag, String.format(message, args), exception);

    }


    public void info(String message, Object... args) {
        if(active) Gdx.app.log(tag, String.format(message, args));

    }


    public void info(String message, Exception exception, Object... args) {
        if(active) Gdx.app.log(tag, String.format(message, args), exception);

    }


    public void error(String message, Object... args) {
        if(active) Gdx.app.error(tag, String.format(message, args));

    }


    public void error(String message, Throwable exception, Object... args) {
        if(active) Gdx.app.error(tag, String.format(message, args), exception);

    }

}
