package com.indignado.logicbricks.utils;

import com.badlogic.gdx.Gdx;

/**
 * @author Rubentxu.
 */
public class Logger {
    private final String tag;

    public Logger(String tag) {
        this.tag = tag;

    }


    public void debug(String message, Object... args) {
        Gdx.app.debug(tag, String.format(message, args));

    }


    public void debug(String message, Exception exception, Object... args) {
        Gdx.app.debug(tag, String.format(message, args), exception);

    }


    public void info(String message, Object... args) {
        Gdx.app.log(tag, String.format(message, args));

    }


    public void info(String message, Exception exception, Object... args) {
        Gdx.app.log(tag, String.format(message, args), exception);

    }


    public void error(String message, Object... args) {
        Gdx.app.error(tag, String.format(message, args));

    }


    public void error(String message, Throwable exception, Object... args) {
        Gdx.app.error(tag, String.format(message, args), exception);

    }

}
